package org.rmaftei.neophytes

import scala.concurrent.Future
import scala.util.Random

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * http://danielwestheide.com/blog/2013/01/09/the-neophytes-guide-to-scala-part-8-welcome-to-the-future.html
  */
object P8_Future extends App {

  type CoffeeBeans = String
  type GroundCoffee = String
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  case class GrindingException(msg: String) extends Exception(msg)
  case class FrothingException(msg: String) extends Exception(msg)
  case class WaterBoilingException(msg: String) extends Exception(msg)
  case class BrewingException(msg: String) extends Exception(msg)
  case class Water(temperature: Int)

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println("hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("milk frothing system engaged!")
    Thread.sleep(Random.nextInt(2000))
    println("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    println("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    println("it's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, foam: FrothedMilk): Future[String] = Future {
    "this is your coffee"
  }

  val result = for {
    ground <- grind("arabica beans")
    water <- heatWater(Water(20))
    foam <- frothMilk("milk")
    espresso <- brew(ground, water)
  } yield combine(espresso, foam)


  result.onComplete(res => println(res))

}
