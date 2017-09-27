package org.rmaftei.neophytes

import java.net.URL

import scala.io.Source

/**
  * http://danielwestheide.com/blog/2013/01/02/the-neophytes-guide-to-scala-part-7-the-either-type.html
  */
object P7_Either extends App {

  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked for the good of the people!")
    else
      Right(Source.fromURL(url))

  // pattern matching

  getContent(new URL("http://google.com")) match {
    case Left(msg) => println(msg)
    case Right(source) => source.getLines.foreach(println)
  }

  // mapping

  val content: Either[String, Iterator[String]] =
    getContent(new URL("http://danielwestheide.com")).right.map(_.getLines())

  println(content)

  // content is a Right containing the lines from the Source returned by getContent
  val moreContent: Either[String, Iterator[String]] =
    getContent(new URL("http://google.com")).right.map(_.getLines)

  println(moreContent)

  val contentLeft: Either[Iterator[String], Source] =
    getContent(new URL("http://danielwestheide.com")).left.map(Iterator(_))

  println(contentLeft)

  val moreContentLeft: Either[Iterator[String], Source] =
    getContent(new URL("http://google.com")).left.map(Iterator(_))

  println(moreContentLeft)

}
