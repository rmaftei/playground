package org.rmaftei.neophytes

/**
  * http://danielwestheide.com/blog/2012/12/12/the-neophytes-guide-to-scala-part-4-pattern-matching-anonymous-functions.html
  */
object P4_PatternMatchingAnonymousFunctions extends App {

  val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
    ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

  def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)

  println(wordsWithoutOutliers(wordFrequencies))

  // better with patterns

  def wordsWithoutOutliersPattern(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.filter { case (_, f) => f > 3 && f < 25 }.map { case (name, _) => name }

  println(wordsWithoutOutliersPattern(wordFrequencies))

  // assign anonymous function to variable

  val predicate: ((String, Int)) => Boolean = { case (_, f) => f > 3 && f < 25 }
  val transformFn: ((String, Int)) => String = { case (w, _) => w }

  def wordsWithoutOutliersVariables(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.filter(predicate).map(transformFn)

  println(wordsWithoutOutliersVariables(wordFrequencies))

  // Partial functions

  val pf: PartialFunction[(String, Int), String] = {
    case (word, freq) if freq > 3 && freq < 25 => word
  }

  def wordsWithoutOutliersPartial(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.collect(pf)

  println(wordsWithoutOutliersPartial(wordFrequencies))


  def wordsWithoutOutliersPartialAnonim(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.collect { case (word, freq) if freq > 3 && freq < 25 => word }

  println(wordsWithoutOutliersPartialAnonim(wordFrequencies))
}
