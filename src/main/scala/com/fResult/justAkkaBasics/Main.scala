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
    val greeterSystem = ActorSystem(GreeterFP(), "greeter-fp")

    // ! Send a message to `greeterSystem` actor
    greeterSystem ! GreeterFP.Greet("Akka w/ FP")
    greeterSystem ! GreeterFP.Greet("Korn w/ FP")
    greeterSystem ! GreeterFP.GoodBye("Akka w/ FP")
    println(">>> Press ENTER to exit <<<")
    readLine()
    Exception.ignoring(classOf[Exception])

    // When ActorSystem.terminate is invoked, the CoordinatedShutdown process
    // will stop actors and services in specific order.
    greeterSystem.terminate()
  }

  private def handleGreeterOop(): Unit = {
    val greeterSystem = ActorSystem(GreeterOOP(), "greeter-oop")

    // ! Send a message to `greeterSystem` actor
    greeterSystem ! GreeterOOP.Greet("Akka w/ OOP")
    greeterSystem ! GreeterOOP.Greet("Korn w/ OOP")
    greeterSystem ! GreeterOOP.GoodBye("Akka w/ OOP")
    println(">>> Press ENTER to exit <<<")
    readLine()
    Exception.ignoring(classOf[Exception])

    // When ActorSystem.terminate is invoked, the CoordinatedShutdown process
    // will stop actors and services in specific order.
    greeterSystem.terminate()
  }
}
