package org.rmaftei.functionalpatterns

object P18_LazySequences extends App {

  def pagedSequence(pageNume: Int): Stream[String] =
    getPage(pageNume) match {
      case Some(page: String) => page #:: pagedSequence(pageNume + 1)
      case None => Stream.empty
    }

  def getPage(page: Int) =
    page match {
      case 1 => Some("pg1")
      case 2 => Some("pg2")
      case 3 => Some("pg3")
      case 4 => Some("pg4")
      case 5 => Some("pg5")
      case _ => None
    }

  // 'force' makes them realize
  println(pagedSequence(1) take 2 force)

  val messageStringStream = "salut," #:: "ce mai faci?" #:: "Molto bene?" #:: Stream[String]()

  // lazy
  println(messageStringStream)

  println(messageStringStream take 2 reduceLeft((acc, current) => acc ++ current))

  // forced
  println(messageStringStream force)
}
