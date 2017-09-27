name := "PlayScala"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value

libraryDependencies += "com.typesafe.akka" % "akka-stream_2.11" % "2.5.3"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.5.3"
libraryDependencies += "com.typesafe.play" % "play-json_2.11" % "2.6.2"
libraryDependencies += "com.typesafe.akka" % "akka-http-core_2.11" % "10.0.9"

libraryDependencies += "org.scalacheck" % "scalacheck_2.11" % "1.13.5" % "test"
