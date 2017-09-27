package org.rmaftei.neophytes

/**
  * http://danielwestheide.com/blog/2012/11/21/the-neophytes-guide-to-scala-part-1-extractors.html
  */
object P1_Extractors extends App {

  //extractors
  trait User {
    def name: String
    def score: Int
  }
  class FreeUser(val name: String, val score: Int, val upgradeProbability: Double)
    extends User
  class PremiumUser(val name: String, val score: Int) extends User

  object FreeUser {
    def unapply(user: FreeUser): Option[(String, Int, Double)] =
      Some((user.name, user.score, user.upgradeProbability))
  }
  object PremiumUser {
    def unapply(user: PremiumUser): Option[(String, Int)] = Some((user.name, user.score))
  }

  val user: User = new FreeUser("Daniel", 3000, 0.7d)

  val welcomeMessage = user match {
    case FreeUser(name, _, p) =>
      if (p > 0.75) name + ", what can we do for you today?" else "Hello " + name
    case PremiumUser(name, _) => "Welcome back, dear " + name
  }

  println(welcomeMessage)

  //boolean extractor

  object premiumCandidate {
    def unapply(user: FreeUser): Boolean = user.upgradeProbability > 0.75
  }

  val userEx2: User = new FreeUser("Daniel", 2500, 0.8d)
  val welcomeMessageEx2 = userEx2 match {
    case freeUser @ premiumCandidate() => " SEND SPAM to " + freeUser.name
    case _ => "already premium"
  }

  println(welcomeMessageEx2)

  // infix operation patterns

  val xs = 58 #:: 43 #:: 93 #:: Stream.empty //lazy ?

  println(xs.head)
  println(xs(1))

  println(xs match {
    case first #:: second #:: _ => first - second
    case _ => -1
  })


}
