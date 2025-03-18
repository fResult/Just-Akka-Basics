ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "3.6.4"

lazy val root = (project in file("."))
  .settings(
    name := "Just Akka Basics (Scala)"
  )

//val AkkaVersion = "2.10.2"
val AkkaVersion = "2.8.8"
//val AkkaHttpVersion = "10.7.0"
val AkkaHttpVersion = "10.5.3"

libraryDependencies ++= List(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe" % "config" % "1.4.3", // Used by `akka-actor-typed`'s ActorSystem
  "ch.qos.logback" % "logback-classic" % "1.5.17"
)
