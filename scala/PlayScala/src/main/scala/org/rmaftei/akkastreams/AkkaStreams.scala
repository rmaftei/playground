package org.rmaftei.akkastreams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._


object AkkaStreams extends App {

  implicit val system = ActorSystem("the-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val source: Source[Int, NotUsed] = Source(1 to 9)
  val sum = source.filter(elm => elm % 2 == 0).reduce((acc, elem) => acc + elem)
  val sumResult = sum.map(_ + " sum").runWith(Sink.foreach(println))

  // flows
  val transformation = Flow[Int].reduce((acc, elem) => acc * elem)
  val dst = Sink.foreach(println)

  val graphFactorial = source via transformation to dst
  graphFactorial.run()

  system.terminate()
}
