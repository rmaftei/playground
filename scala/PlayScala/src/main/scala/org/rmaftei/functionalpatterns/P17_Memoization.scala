package org.rmaftei.functionalpatterns

object P17_Memoization extends App {

  def expensiveLookup(id: Int) = {
    println(s"Doing expensive lookup for $id")
    Thread.sleep(1000L)

    Map(42 -> "foo", 12 -> "bar", 1 -> "baz").get(id)
  }

  println(expensiveLookup(42))

  def memoizedExpensiveLookup() = {
    var cache = Map[Int, Option[String]]()

    (id: Int) =>
      cache.get(id) match {
        case Some(result:Option[String]) => result
        case None =>
          val result = expensiveLookup(id)
          cache += id -> result
          result
      }
  }

  val cachedExpensiveLookup = memoizedExpensiveLookup
  println(cachedExpensiveLookup(42))
  println(cachedExpensiveLookup(12))
  println(cachedExpensiveLookup(42))
}
