package org.rmaftei.neophytes

/**
  * http://danielwestheide.com/blog/2012/12/19/the-neophytes-guide-to-scala-part-5-the-option-type.html
  */
object P5_Option extends App{

  val greeting: Option[String] = Some("Hello world")
  val noGreeting: Option[String] = None

  println(greeting.get)
  println(noGreeting.getOrElse("No greeting, sorry"))

  // pattern matching
  case class User(id: Int, firstname: String, lastname: String, age: Int, gender: Option[String])

  val user = User(2, "Johanna", "Doe", 30, None)

  user.gender match {
    case Some(gender) => println("Gender: " + gender)
    case None => println("Gender: not specified")
  }

  //Options can be viewed as collections

  val optionUser = Some(User(1,"Gigel", "Bunescu", 45,None))

  val optionUser2 = Some(User(2,"Vasile", "Doe", 25,Some("M")))

  val firstname = optionUser.map { user => user.firstname }

  println(firstname.getOrElse("No name"))

  val found = optionUser.filter { _.age > 30 }
  println(found.getOrElse("No user"))

  val notFound = optionUser.filter { _.age > 50 }
  println(notFound.getOrElse("No user"))

  // For comprehensions

  println(for {
    user <- optionUser
    gender <- user.gender

  } yield gender)

  println(for {
    User(_,_,_,_,Some(gender)) <- optionUser2
  } yield gender)

  // Chaining options

  case class Resource(content: String)
  val resourceFromConfigDir: Option[Resource] = None
  val resourceFromClasspath: Option[Resource] = Some(Resource("I was found on the classpath"))
  val resource = resourceFromConfigDir orElse resourceFromClasspath

  println(resource)
}
