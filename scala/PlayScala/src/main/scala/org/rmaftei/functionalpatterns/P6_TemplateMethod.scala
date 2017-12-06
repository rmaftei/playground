package org.rmaftei.functionalpatterns

object P6_TemplateMethod extends App {

  def makeGradeReporter(numToLetter: (Double) => String,
                        printGradeReport: (Seq[String] => Unit)) = (grades: Seq[Double]) => {

      printGradeReport(grades map numToLetter)
    }

  def fullGradeConverter(grade: Double) =
    if(grade <= 5.0 && grade > 4.0) "A"
    else if(grade <= 4.0 && grade > 3.0) "B"
    else if(grade <= 3.0 && grade > 2.0) "C"
    else if(grade <= 2.0 && grade > 0.0) "D"
    else if(grade <= 0.0) "F"
    else "N/A"

  def printHistogram(grades: Seq[String]): Unit = {
    val grouped = grades.groupBy(identity)
    val counts = (grouped map ((elem) => (elem._1, elem._2.size)) toSeq) sorted

    counts.foreach(count => {
     val stars = "*" * count._2
      println("%s: %s".format(count._1, stars))
    })
  }

  val sampleGrades = Vector(5.0, 4.0, 4.4, 2.2, 3.3, 3.5)

  val fullGradeReporter = makeGradeReporter(fullGradeConverter, printHistogram)

  fullGradeReporter(sampleGrades)
}
