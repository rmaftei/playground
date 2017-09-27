package org.rmaftei.akkastreams

import akka.NotUsed
import akka.stream.scaladsl.Flow
import org.rmaftei.akkastreams.PancakesMachine._

object PancakePipeline extends App {


  val fryingPan1: Flow[ScoopOfBatter, HalfCookedPancake, NotUsed] =
    Flow[ScoopOfBatter].map { batter => HalfCookedPancake() }

  val fryingPan2: Flow[HalfCookedPancake, Pancake, NotUsed] =
    Flow[HalfCookedPancake].map { halfCookedPancake => Pancake() }

  // using #async, means that fryingPan1 does not wait for fryingPan2 to finish
  // thus this process is concurrent
  val pancakeChef: Flow[ScoopOfBatter, Pancake, NotUsed] =
    Flow[ScoopOfBatter].via(fryingPan1.async).via(fryingPan2.async)


}
