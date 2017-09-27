package org.rmaftei.akkastreams

import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl._
import org.rmaftei.akkastreams.PancakesMachine.ScoopOfBatter
import org.rmaftei.akkastreams.PancakesMachine.Pancake
import GraphDSL.Implicits._
import akka.actor.ActorSystem

object PancakeParallel extends App {

  implicit val system = ActorSystem("panckage-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val s = System.nanoTime

  val batter = Source(collection.immutable.Seq(ScoopOfBatter,ScoopOfBatter,ScoopOfBatter,ScoopOfBatter,ScoopOfBatter))

  val out = Sink.foreach(println)

  val fryingPan1: Flow[ScoopOfBatter.type, Pancake, NotUsed] =
    Flow[ScoopOfBatter.type].map { batter => {
        Pancake()
      }
    }

  val fryingPan2: Flow[ScoopOfBatter.type, Pancake, NotUsed] =
    Flow[ScoopOfBatter.type].map { batter => {
      Pancake()
    }
    }

  val panckageChef: Graph[FlowShape[ScoopOfBatter.type, Pancake], NotUsed] = GraphDSL.create() { implicit builder =>
    val dispatchBatter = builder.add(Balance[ScoopOfBatter.type](2))
    val mergePancakes = builder.add(Merge[Pancake](2))

    dispatchBatter.out(0) ~> fryingPan1 ~> mergePancakes.in(0)
    dispatchBatter.out(1) ~> fryingPan2 ~> mergePancakes.in(1)

    FlowShape(dispatchBatter.in, mergePancakes.out)
  }

  val panckageChefRun: Flow[ScoopOfBatter.type , Pancake, NotUsed] = Flow.fromGraph(panckageChef)

  Source(collection.immutable.Seq.fill(10000000) {
    ScoopOfBatter
  }).via(panckageChefRun).fold(0)((acc, value) => acc + 1).runForeach(sum => println(sum)).onComplete(_ => {
    println("time: "+(System.nanoTime-s)/1e6+"ms")
    system.terminate()
  })

}
