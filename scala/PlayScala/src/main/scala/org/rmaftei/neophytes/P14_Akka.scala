package org.rmaftei.neophytes

import akka.actor.{ActorRef, ActorSystem, Props}

/**
  * http://danielwestheide.com/blog/2013/02/27/the-neophytes-guide-to-scala-part-14-the-actor-approach-to-concurrency.html
  */
object P14_Akka extends App {

  val system = ActorSystem("Barista")

  sealed trait CoffeeRequest
  case object CappuccinoRequest extends CoffeeRequest
  case object EspressoRequest extends CoffeeRequest

  import akka.actor.Actor

  class Barista extends Actor {
    override def receive: Receive = {
      case CappuccinoRequest => println("I have a cappuccino")
      case EspressoRequest => println("Espressoo !!!")
    }
  }

  val barista = system.actorOf(Props[Barista], "Barista")

  barista ! CappuccinoRequest
  barista ! EspressoRequest

  println("I ordered a cappuccino and an espresso")


  // responding
  case class Bill(cents: Int)
  case object ClosingTime
  class SecondBarista extends Actor {
    def receive = {
      case CappuccinoRequest =>
        sender ! Bill(250)
        println("I have to prepare a cappuccino!")
      case EspressoRequest =>
        sender ! Bill(200)
        println("Let's prepare an espresso.")
      case ClosingTime => context.system.terminate
    }
  }

  case object CaffeineWithdrawalWarning
  class Customer(caffeineSource: ActorRef) extends Actor {
    def receive = {
      case CaffeineWithdrawalWarning => caffeineSource ! EspressoRequest
      case Bill(cents) => println(s"I have to pay $cents cents, or else!")
    }
  }


  val secondBarista = system.actorOf(Props[SecondBarista], "SecondBarista")
  val customer = system.actorOf(Props(classOf[Customer], secondBarista), "Customer")
  customer ! CaffeineWithdrawalWarning
  secondBarista ! ClosingTime

}
