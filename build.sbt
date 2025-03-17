ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "just-akka-basics"
  )

val AkkaVersion = "2.10.2"
//val AkkaHttpVersion = "10.7.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
// libraryDependencies += "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion
// libraryDependencies += "com.typesafe.akka" %% "akka-http-typed" % AkkaHttpVersion

