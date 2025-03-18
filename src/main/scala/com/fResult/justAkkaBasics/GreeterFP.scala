package com.fResult.justAkkaBasics

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

object GreeterFP {
  // <- Protocol definition, the type of message(s) the actor handles
  sealed trait GreetCommand

  final case class Greet(whom: String) extends GreetCommand

  final case class GoodBye(whom: String) extends GreetCommand
  // Protocol definition ->

  def apply(): Behavior[GreetCommand] =
    Behaviors.receive[GreetCommand] { (context, message) =>
      message match {
        case Greet(whom) => context.log.info(s"Hello, $whom!")
        case GoodBye(whom) => context.log.info(s"Goodbye, $whom!")
      }

      Behaviors.same
    }
}
