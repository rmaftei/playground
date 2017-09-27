package org.rmaftei.akkastreams

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Flow, Framing, Sink, Source}
import akka.util.ByteString

import scala.concurrent.Future

object RugbyCSV extends App {

  case class Data(
                   id: String,
                   matchId: String,
                   stadium: String,
                   city: String,
                   country: String,
                   data: String,
                   team1: String,
                   team2: String,
                   score1: Int,
                   score2: Int,
                   championship: String)

  implicit val system = ActorSystem("the-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val startTime = System.currentTimeMillis()

  val file = Paths.get("src/main/resources/matches_2016.csv")

  val lines = scala.io.Source.fromFile("src/main/resources/matches_2016_half.csv").getLines()

//  val source:Source[String, NotUsed] = Source.fromIterator(() => lines)

  val source: Source[String, Future[IOResult]] = FileIO.fromPath(file)
    .via(Framing.delimiter(ByteString("\n"),maximumFrameLength = 2048,allowTruncation = true))
    .map(_.utf8String)

  val toDataType = Flow[String].map(line => {

    val splits = line.split(",")

    try {
      Data(
        splits(0),
        splits(1),
        splits(3),
        splits(4),
        splits(5),
        splits(8) + splits(9),
        splits(12),
        splits(15),
        splits(17).toInt,
        splits(18).toInt,
        splits(22))

    } catch {
      case _: RuntimeException =>
        Data("","","","","","","","",0,0,"")
    }
  })

  val computeTotalPoints = Flow[Data].map(data => {
    data.score1 + data.score2
  }).reduce(_ + _)

//  val computeTotalPointsByStadium = Flow[Data].groupBy(_.).map(data => {
//    data.score1 + data.score2
//  }).reduce(_ + _)

  val out = Sink.foreach(println)

//   val flow = source via toDataType to out
   val flow = source via toDataType via computeTotalPoints to out

   flow.run().onComplete(_ => system.terminate())

}
