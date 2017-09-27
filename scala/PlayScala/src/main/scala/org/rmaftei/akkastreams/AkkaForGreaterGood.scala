package org.rmaftei.akkastreams

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ThrottleMode}
import akka.stream.scaladsl.{Keep, Sink, Source}

import scala.concurrent.Await
import scala.concurrent.duration._

object AkkaForGreaterGood extends App {

  implicit val system = ActorSystem("the-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val run = Source
    .repeat("un text mișto")
    .zip(Source.fromIterator(() => Iterator.from(0)))
    .take(7)
      .mapConcat{ case (s, n) =>
          val i = " " * n

          f"$i$s%n"

      }
    .throttle(1, 100 millis, 1, ThrottleMode.Shaping)
    .toMat(Sink.foreach(print))(Keep.right)
    .run()


  run.onComplete(_ => system.terminate())
  Await.ready(system.whenTerminated, Duration.Inf)


}
