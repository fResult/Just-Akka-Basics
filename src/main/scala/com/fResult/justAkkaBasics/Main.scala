package com.fResult.justAkkaBasics

import akka.actor.typed.ActorSystem

import scala.io.StdIn.readLine
import scala.util.control.Exception

object Main {
  def main(args: Array[String]): Unit = {
    handleGreeterFp()
    handleGreeterOop()
  }

  private def handleGreeterFp(): Unit = {
    val greeterSystem = ActorSystem(GreeterFP(GreeterFP.State(maxGreets = 3, minimumAttendance = 2)), "greeter-fp")

    // ! Send a message to `greeterSystem` actor
    greeterSystem ! GreeterFP.Greet("Akka w/ FP")
    greeterSystem ! GreeterFP.GoodBye("Akka w/ FP")
    greeterSystem ! GreeterFP.Greet("Korn w/ FP")
    greeterSystem ! GreeterFP.GoodBye("Korn w/ FP")
    greeterSystem ! GreeterFP.Greet("John w/ FP")
    greeterSystem ! GreeterFP.Greet("Alex w/ FP")
    greeterSystem ! GreeterFP.Greet("Sarah w/ FP")
    greeterSystem ! GreeterFP.GoodBye("Sarah w/ FP")
    greeterSystem ! GreeterFP.Greet("George w/ FP")
    println(">>> Press ENTER to exit <<<")
    readLine()
    Exception.ignoring(classOf[Exception])

    // When ActorSystem.terminate is invoked, the CoordinatedShutdown process
    // will stop actors and services in specific order.
    greeterSystem.terminate()
  }

  private def handleGreeterOop(): Unit = {
    val greeterSystem = ActorSystem(GreeterOOP(2), "greeter-oop")

    // ! Send a message to `greeterSystem` actor
    greeterSystem ! GreeterOOP.Greet("Akka w/ OOP")
    greeterSystem ! GreeterOOP.Greet("Korn w/ OOP")
    greeterSystem ! GreeterOOP.GoodBye("Akka w/ OOP")
    greeterSystem ! GreeterOOP.Greet("John w/ OOP")
    greeterSystem ! GreeterOOP.Greet("Alex w/ OOP")
    println(">>> Press ENTER to exit <<<")
    readLine()
    Exception.ignoring(classOf[Exception])

    // When ActorSystem.terminate is invoked, the CoordinatedShutdown process
    // will stop actors and services in specific order.
    greeterSystem.terminate()
  }
}
