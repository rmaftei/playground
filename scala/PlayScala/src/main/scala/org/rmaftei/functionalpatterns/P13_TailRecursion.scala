package org.rmaftei.functionalpatterns

import scala.annotation.tailrec

object P13_TailRecursion extends App {
  case class Person(firstName: String, lastName: String)

  def makePeople(firstNames: Seq[String],lastNames: Seq[String]) = {

    @tailrec
    def helper(firstNames: Seq[String],lastNames: Seq[String], people:Vector[Person]): Seq[Person] = {
      if(firstNames.isEmpty) {
        return people
      } else {
        val newPerson = Person(firstNames.head, lastNames.head)
        helper(firstNames.tail, lastNames.tail, people :+ newPerson)
      }
    }

    helper(firstNames, lastNames, Vector[Person]())
  }

  val firstNames = Seq("Ionel", "Gigel", "Gică")
  val lastNames = Seq("Ionel", "Gigel", "Gică").reverse

  print(makePeople(firstNames, lastNames))

}
