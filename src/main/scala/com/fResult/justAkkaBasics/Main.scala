package com.fResult.justAkkaBasics

import akka.actor.typed.ActorSystem

object Main {
  def main(args: Array[String]): Unit = {
    val greeterSystem = ActorSystem(GreeterFP(), "greeter-fp")
  }
}
