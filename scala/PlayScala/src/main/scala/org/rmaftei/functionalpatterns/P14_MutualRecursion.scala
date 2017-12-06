package org.rmaftei.functionalpatterns

import scala.util.control.TailCalls._

object P14_MutualRecursion extends App {
  def isOdd(n: Long): Boolean = if(n == 0) false else isEven(n - 1)

  def isEven(n: Long): Boolean = if(n == 0) true else isOdd(n - 1)

  println(isOdd(3))
  // println(isOdd(1000001)) // problem

  // With tails recursion

  def isOddTrampoline(n: Long): TailRec[Boolean] = if(n == 0) done(false) else tailcall(isEvenTrampoline(n - 1))

  def isEvenTrampoline(n: Long): TailRec[Boolean] = if(n == 0) done(true) else tailcall(isOddTrampoline(n - 1))

  println(isOddTrampoline(3).result)

  println(isOddTrampoline(1000001).result) // not a problem anymore

  //

  class Transition

  case object Ionization extends Transition
  case object Deionization extends Transition
  case object Vaporization extends Transition
  case object Condensation extends Transition
  case object Freezing extends Transition
  case object Melting extends Transition
  case object Sublimation extends Transition
  case object Deposition extends Transition

  def plasma(transition: List[Transition]): TailRec[Boolean] = transition match {
    case Nil => done(true)
    case Deionization :: tailTransation => tailcall(vapor(tailTransation))
    case _ => done(false)
  }

  def vapor(transition: List[Transition]): TailRec[Boolean] = transition match {
    case Nil => done(true)
    case Condensation :: tailTransation => tailcall(liquid(tailTransation))
    case Deionization :: tailTransation => tailcall(solid(tailTransation))
    case Ionization :: tailTransation => tailcall(plasma(tailTransation))
    case _ => done(false)
  }

  def liquid(transition: List[Transition]): TailRec[Boolean] = transition match {
    case Nil => done(true)
    case Vaporization :: tailTransation => tailcall(vapor(tailTransation))
    case Freezing :: tailTransation => tailcall(solid(tailTransation))
    case _ => done(false)
  }

  def solid(transition: List[Transition]): TailRec[Boolean] = transition match {
    case Nil => done(true)
    case Melting :: tailTransation => tailcall(liquid(tailTransation))
    case Sublimation :: tailTransation => tailcall(vapor(tailTransation))
    case _ => done(false)
  }

  println(solid(List(Melting,Vaporization, Ionization, Deionization)).result)

}
