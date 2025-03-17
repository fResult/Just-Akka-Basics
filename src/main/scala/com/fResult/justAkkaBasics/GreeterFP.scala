package com.fResult.justAkkaBasics

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object GreeterFP {
  // <- Protocol definition, the type of message(s) the actor handles
  sealed trait GreetCommand
  private final case class Greet(whom: String) extends GreetCommand
  private final case class GoodBye(whom: String) extends GreetCommand
  // Protocol definition ->

  def apply(): Behavior[GreetCommand] =
    Behaviors.receive { (context, message) =>
      message match {
        case Greet(whom) =>
          context.log.info(s"Hello, $whom!")
          Behaviors.same
        case GoodBye(whom) =>
          context.log.info(s"Goodbye, $whom!")
          Behaviors.same
      }
    }
}
