package org.rmaftei.neophytes

import java.io.FileNotFoundException
import java.net.{MalformedURLException, URL}

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * http://danielwestheide.com/blog/2012/12/26/the-neophytes-guide-to-scala-part-6-error-handling-with-try.html
  */
object P6_Try extends App {

  def parseURL(url: String): Try[URL] = Try(new URL(url))

  val url = parseURL("not good url") getOrElse new URL("http://www.google.com")

  println(url)

  // mapping and flatmapping

  val protocol = parseURL("http://www.google.com").map { _.getProtocol }

  println(protocol)

  // for comprehensions
  def getURLContent(url: String): Try[Iterator[String]] =
    for {
      url <- parseURL(url)
      connection <- Try(url.openConnection())
      is <- Try(connection.getInputStream)
      source = Source.fromInputStream(is)
    } yield source.getLines()

  // Pattern Matching

  getURLContent("http://danielwestheide.com/foobar") match {
    case Success(lines) => lines.foreach(println)
    case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
  }

  // Recovering from a Failure
  val content = getURLContent("garbage") recover {
    case e: FileNotFoundException => Iterator("Requested page does not exist")
    case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
    case _ => Iterator("An unexpected error has occurred. We are so sorry!")
  }

  content match {
    case Success(it) => println(it.toList)
    case Failure(ex) => println(ex)
  }



}
