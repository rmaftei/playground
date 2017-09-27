package org.rmaftei.neophytes

/**
  * http://danielwestheide.com/blog/2013/01/23/the-neophytes-guide-to-scala-part-10-staying-dry-with-higher-order-functions.html
  */
object P10_DRY_WithHigherOrderFunctions extends App {

  case class Email(subject: String, text:String, sender:String, recipient: String)

  type EmailFilter = Email => Boolean

  def newMailsForUser(mails:Seq[Email], f:EmailFilter): Seq[Email] = mails.filter(f)

  // factory functions

  val sentBeOneOf:Set[String] => EmailFilter = senders => email => senders.contains(email.sender)

  val notSendBeOneOf:Set[String] => EmailFilter = senders => email => !senders.contains(email.sender)

  val minimumSize:Int => EmailFilter = n => email => email.text.length >= n

  val maximumSize:Int => EmailFilter = n => email => email.text.length <= n


  val badguyFilder:EmailFilter = notSendBeOneOf(Set("johnnybegood@mail.ro"))

  val goodGuyFilter:EmailFilter = sentBeOneOf(Set("goodguy@mail.ro"))

  val mails = Email(
    subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you?",
    sender = "goodguy@mail.ro",
    recipient = "me@example.com") :: Email(
    subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you?",
    sender = "johnnybegood@mail.ro",
    recipient = "me@example.com") :: Nil

  val emailThatCanBySend = newMailsForUser(mails, badguyFilder) :: newMailsForUser(mails, goodGuyFilter) :: Nil

  val result = emailThatCanBySend.map(email => email.head)

  println("Result1: " + result)


  // Composing predicates

  def any[A](predicates: (A => Boolean)*): A => Boolean = a => predicates.exists(p => p(a))

  def every[A](predicates: (A => Boolean)*): A => Boolean = a => !predicates.exists(p => p(a))

  val anyFilter: EmailFilter = any(
    notSendBeOneOf(Set("badguy@mail.ro")),
    minimumSize(200),
    maximumSize(2000)
  )

  val everyFilter: EmailFilter = every(
    notSendBeOneOf(Set("badguy@mail.ro")),
    minimumSize(200),
    maximumSize(2000)
  )


  val mails2 = Email(
    subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you?",
    sender = "badguy@mail.ro",
    recipient = "me@example.com") :: Email(
    subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you?",
    sender = "johnnybegood@mail.ro",
    recipient = "me@example.com") :: Nil

  val result2 = newMailsForUser(mails2, anyFilter).map(email => email.sender)

  val result3 = newMailsForUser(mails2, everyFilter).map(email => email.sender)

  println("Result2: " + result2)

  println("Result3: " + result3)
}
