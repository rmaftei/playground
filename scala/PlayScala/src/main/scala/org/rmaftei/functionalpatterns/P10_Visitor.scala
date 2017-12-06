package org.rmaftei.functionalpatterns

object P10_Visitor extends App {

  implicit class ExtendsPerson(person: Person) {
    def fullAddress = person.street + " " + person.houseNum
  }

  trait Person {
    def fullname: String
    def firstname: String
    def lastname: String
    def houseNum: String
    def street: String
  }

  class SimplePerson(val firstname:String, val lastname: String, val houseNum:String, val street: String) extends Person {
    override def fullname: String = lastname + " " + firstname
  }

  val simplePerson = new SimplePerson("Mike", "Line", "123", "A street")
  println (simplePerson.fullname)

  println (simplePerson.fullAddress)


}
