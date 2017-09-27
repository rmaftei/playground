package org.rmaftei.neophytes

/**
  * http://danielwestheide.com/blog/2012/11/28/the-neophytes-guide-to-scala-part-2-extracting-sequences.html
  */
object P2_ExtractingSequences extends App {

  val xs = 3 :: 6 :: 12 :: Nil

  println(xs)

  val res = xs match {
    case List(a,b) => a + b
    case List(a,b,c) => a + b + c
    case _ => 0
  }

  println(res)

  // without carring about the size of the list

  val xs2 = 3 :: 6 :: 12 :: 24 :: Nil
  val res2 = xs match {
    case List(a, b, _*) => a * b
    case _ => 0
  }

  println(res2)

  // sequence extractor
  // def unapplySeq(object: S): Option[Seq[T]]

  object GivenNames {
    def unapplySeq(name: String): Option[Seq[String]] = {
      val names = name.trim.split(" ")

      if(names.forall(_.isEmpty)) None else Some(names)
    }
  }

  val name = "Gigel Guma Ion"

  def greetWithFirstName(name: String) = name match {
    case GivenNames(firstName, _*) => "Good morning, " + firstName + "!"
    case _ => "Welcome! Please make sure to fill in your name!"
  }

  println(greetWithFirstName(name))
}
