package org.rmaftei.functionalpatterns

// Pattern 3 - Replacing Command pattern
object P3_Command extends App {

  class CashRegister(var total: Int) {
    def addCash(toAdd: Int) {
      total += toAdd
    }
  }

  def makePurchase(register: CashRegister, amount: Int):() => Unit = {
    () => {
      println("Purchase amount: " + amount)
      register.addCash(amount)
    }

  }

  var purchases: Vector[() => Unit] = Vector()

  def executePurchase(purchase: () => Unit) = {
    purchases = purchases :+ purchase
    purchase()
  }

  val cashRegister = new CashRegister(0)
  val purchaseOne = makePurchase(cashRegister,100)
  val purchaseTwo = makePurchase(cashRegister,50)

  executePurchase(purchaseOne)
  executePurchase(purchaseTwo)

  println(cashRegister.total)

  println("Settings zero")
  cashRegister.total = 0
  println(cashRegister.total)

  // ok, but what if the call is expensive(third party api, long computation)
  purchases.foreach(purchase => purchase.apply())

  println("After replay")
  println(cashRegister.total)

}


