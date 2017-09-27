package org.rmaftei.akkastreams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import org.joda.time.DateTime
import play.api.libs.json._

object Vremea extends App {

  println(DateTime.now())

  implicit val system = ActorSystem("the-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val KELVIN = 273.15

  case class Temperature(city: String, temperature: Double)

  val OPEN_WEATHER_API:String = "http://api.openweathermap.org/data/2.5/group?appid=3b7a45ea9025e6c5262d520faec00123&id="

  def getData(url: String, query:String):String = scala.io.Source.fromURL(url + query).mkString
  def getData(url: String, cityIds: Seq[String]):String = scala.io.Source.fromURL(url + cityIds.mkString(",")).mkString

  def getOras(data: JsValue): String = (data \ "name").as[String]
  def getTemperatura(data: JsValue):Double = (data \ "main" \ "temp").as[Double]
  def getCondition(data: JsValue):Seq[JsValue] = data \ "weather" \\ "main"


  var cityIds = Seq("665850","675810","684039","663118","685948")
  val response = getData(OPEN_WEATHER_API, cityIds)

  val data = Json.parse(response)

  val list = (data \ "list").get.asInstanceOf[JsArray].value.to[collection.immutable.Seq]

  val weatherSource:Source[JsValue, NotUsed] = Source(list)

  val transform = Flow[JsValue]
    .map(jsValue => {
      val name = (jsValue \ "name").as[String]
      val temp = (jsValue \\ "temp").head.as[Double] - KELVIN

      Json.obj("nume" -> name, "temperatura" -> temp)

    })
    .fold(Seq[JsObject]())((acc, value) => acc :+ value)
    .map(listCities => Json.obj("cities" -> listCities))

  val dst = Sink.foreach(println)

  val graphWeather = weatherSource via transform to dst
  graphWeather.run()

  system.terminate()

  println(DateTime.now())
}
