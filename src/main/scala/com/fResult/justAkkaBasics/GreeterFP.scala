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
        case Greet(whom) => greet(whom, context)
        case GoodBye(whom) => goodBye(whom, context)
      }
    }

  private def greet(whom: String, context: ActorContext[GreetCommand]): Behavior[GreetCommand] = {
    context.log.info(s"Hello, $whom!")
    Behaviors.same
  }

  private def goodBye(whom: String, context: ActorContext[GreetCommand]): Behavior[GreetCommand] = {
    context.log.info(s"Goodbye, $whom!")
    Behaviors.same
  }
}

