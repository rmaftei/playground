package org.rmaftei.functionalpatterns

object P9_Decorator extends App {

  def add(a: Int, b:Int) = a + b
  def substract(a: Int, b:Int) = a - b
  def multiply(a: Int, b:Int) = a * b
  def divide(a: Int, b:Int) = a / b

  def makeLogger(calcFn: (Int, Int) => Int) =
    (a: Int, b: Int) => {
      val result = calcFn(a,b)
      println("Result is: " + result)
      result
    }

  val loggingAdd = makeLogger(add)

  add(1,3)
  loggingAdd(2, 3)
}
