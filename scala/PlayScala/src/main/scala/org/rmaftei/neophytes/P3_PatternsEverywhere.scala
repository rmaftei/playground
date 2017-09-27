package org.rmaftei.neophytes

/**
  * http://danielwestheide.com/blog/2012/12/05/the-neophytes-guide-to-scala-part-3-patterns-everywhere.html
  */
object P3_PatternsEverywhere extends App {

  case class Player(name: String, score: Int)

  def message(player: Player) = player match {
    case Player(_, score) if score > 100000 => println("Get a job, dude!")
    case Player(name, _) => println("Hey " + name + ", nice to see you again!")
  }

  def printMessage(player: Player) = println(message(player))

  printMessage(Player("Gigel", 1000))
  printMessage(Player("Ionel", 1000000))

  def printName(name: String) = println(name)

  def currentPlayer(): Player = Player("Ionel", 9000)

  val Player(name, _) = currentPlayer()

  printName(name)

  def gameResult(): (String, Int) = ("Danile", 70)

  val (namePlayer, score) = gameResult()

  println("Good job " + namePlayer + " " + score)

  // Patterns in for comprehensions

  def gameResults(): Seq[(String, Int)] =
    ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil

  def hallOfFame = for {
    (name, score) <- gameResults()
    if score > 5000
  } yield name

  println(hallOfFame)
}
