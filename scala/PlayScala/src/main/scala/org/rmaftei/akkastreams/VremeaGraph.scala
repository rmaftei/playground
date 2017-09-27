package org.rmaftei.akkastreams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.{ActorMaterializer, ClosedShape}
import org.joda.time.DateTime
import play.api.libs.json._
import GraphDSL.Implicits._


object VremeaGraph extends App {

  println(DateTime.now())

  implicit val system = ActorSystem("weather-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val OPEN_WEATHER_API:String = "http://api.openweathermap.org/data/2.5/group?appid=3b7a45ea9025e6c5262d520faec00123&id="

  def getData(url: String, query:String):String = scala.io.Source.fromURL(url + query).mkString
  def getData(url: String, cityIds: Seq[String]):String = scala.io.Source.fromURL(url + cityIds.mkString(",")).mkString

  def getOras(data: JsValue): String = (data \ "name").as[String]
  def getTemperatura(data: JsValue):Double = (data \\ "temp").head.as[Double]
  def getCondition(data: JsValue):Seq[JsValue] = data \ "weather" \\ "main"
  def kelvinToCelsius(kelvin: Double):Double = kelvin - 273.15
  def kelvinToFahrenheit(kelvin: Double):Double = kelvin * 9/5 - 459.67


  var cityIds = Seq("665850","675810","684039","663118","685948")
  val response = getData(OPEN_WEATHER_API, cityIds)

  val data = Json.parse(response)

  val list = (data \ "list").get.asInstanceOf[JsArray].value.to[collection.immutable.Seq]


//  val graphWithBroadcast = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder:GraphDSL.Builder[NotUsed] =>
//    import GraphDSL.Implicits._
//
//    val in: Source[JsValue, NotUsed]  = Source(list)
//    val out = Sink.foreach(println)
//    val dev_null = Sink.foreach(println)
//
//    val bcast = builder.add(Broadcast[JsObject](2))
//
//    val extract = Flow[JsValue]
//      .map(data => {
//        val name = getOras(data)
//        val temp = getTemperatura(data)
//
//        (name, temp, kelvinToCelsius(temp), kelvinToFahrenheit(temp))
//      })
//
//    val toJsonObject = Flow[(String, Double, Double, Double)].map(data => {
//      Json.obj("nume" -> data._1, "kelvin" -> data._2, "celsius" -> data._3, "fahrenheit" -> data._4)
//    })
//
//    val reduce = Flow[JsObject].fold(Seq[JsObject]())((acc, value) => acc :+ value)
//    val map = Flow[Seq[JsObject]].map(listCities => Json.obj("cities" -> listCities))
//
//    in ~> extract ~> toJsonObject ~> bcast ~> reduce ~> map ~> out
//                                    bcast ~> dev_null
//    ClosedShape
//  })

//  graphWithBroadcast.run()

  val graphWithBalance = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder:GraphDSL.Builder[NotUsed] =>

    val in: Source[JsValue, NotUsed]  = Source(list)
    val merge = builder.add(Merge[JsObject](2))
    val out = Sink.foreach(println)

    val balance = builder.add(Balance[JsValue](2))

    val extract = Flow[JsValue]
      .map(data => {
        val name = getOras(data)
        val temp = getTemperatura(data)

        (name, temp, kelvinToCelsius(temp), kelvinToFahrenheit(temp))
      })

    val toJsonObject = Flow[(String, Double, Double, Double)].map(data => {
      Json.obj("nume" -> data._1, "kelvin" -> data._2, "celsius" -> data._3, "fahrenheit" -> data._4)
    })

    val reduce = Flow[JsObject].fold(Seq[JsObject]())((acc, value) => acc :+ value)
    val map = Flow[Seq[JsObject]].map(listCities => Json.obj("orașe" -> listCities))

    in ~> balance ~> extract ~> toJsonObject ~> merge ~> reduce ~> map ~> out
          balance ~> extract ~> toJsonObject ~> merge

    ClosedShape
  })

  graphWithBalance.run()

  system.terminate()

  println(DateTime.now())
}
