package org.rmaftei.functionalpatterns

object P7_Strategy extends App {

  case class Person(firstName: Option[String],
                    middleName: Option[String],
                    lastName: Option[String])

  def isFirstNameValid(person: Person) = person.firstName.isDefined

  def isFullNameValid(person: Person) = person match {
    case Person(firstName, middleName, lastName) =>
      firstName.isDefined && middleName.isDefined && lastName.isDefined
  }

  def personCollect(isValid: (Person) => Boolean) = {
    var validPeople = Vector[Person]()

    (person: Person) => {
      if(isValid(person)) {
        validPeople = validPeople :+ person
      }

      validPeople
    }
  }

  val singleNameValidCollector = personCollect(isFirstNameValid)
  val fullNameValidCollector = personCollect(isFullNameValid)

  val p1 = Person(Some("Gică1"), Some("Ionel1"), Some("Ion1"))
  val p2 = Person(Some("Gică2"), None, Some("Ion2"))
  val p3 = Person(None, None, None)

  println(singleNameValidCollector(p1))
  println(singleNameValidCollector(p2))
  println(singleNameValidCollector(p3))


}
