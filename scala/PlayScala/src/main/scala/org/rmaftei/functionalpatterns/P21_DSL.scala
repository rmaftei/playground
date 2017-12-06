package org.rmaftei.functionalpatterns

import java.util

import scala.io.Source

object P21_DSL extends App {
  case class CommandResult(status:Int, output:String, error: String)

  class Command(commandParts: List[String]) {
    def run() = {
      val asJavaList = new util.ArrayList[String]()
      commandParts foreach { el =>
        asJavaList.add(el)
      }

      val processBuilder = new ProcessBuilder(asJavaList)
      val process = processBuilder.start
      val status = process.waitFor()
      val outputAsString = Source.fromInputStream(process.getInputStream).mkString
      val errorAsString = Source.fromInputStream(process.getErrorStream).mkString

      CommandResult(status, outputAsString, errorAsString)
    }
  }

  object Command {
    def apply(command: String): Command = new Command(command.split("\\s").toList)
    def apply(commands: String*): Command = new Command(commands.toList)
  }

  // the magic
  implicit class CommandString(command:String) {
    def run = Command(command).run()
    def pipe(secondCommand: String) = {
      Vector(command, secondCommand)
    }
  }

  implicit class CommandVector(commands: Vector[String]) {
    def run = {
      val pipedCommands = commands.mkString(" | ")
      Command("/bin/sh", "-c", pipedCommands).run()
    }
9
    def pipe(nextCommand: String): Vector[String] = {
      commands :+ nextCommand
    }
  }

//  println("ls -l" run )
//  println("ls -l" pipe "grep src" run)
  println("ls -l" pipe "grep src" pipe "wc" run)

}
