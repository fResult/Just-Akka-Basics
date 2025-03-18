package com.fResult.justAkkaBasics

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object GreeterOOP {
  // <- Protocol definition, the type of message(s) the actor handles
  // Note: We support a single message type here.
  // We will see later how to support multiple message types.
  sealed trait GreetCommand

  final case class Greet(whom: String) extends GreetCommand

  final case class GoodBye(whom: String) extends GreetCommand
  // Protocol definition ->

  def apply(): Behavior[GreetCommand] = Behaviors.setup[GreetCommand](new GreeterBehavior(_))

  // The case class we define for our behaviors within our actor
  private class GreeterBehavior(context: ActorContext[GreetCommand])
      extends AbstractBehavior[GreetCommand](context) {

    // We add mutable state as a class field
    private var attendance = 0

    override def onMessage(message: GreetCommand): Behavior[GreetCommand] = {
      message match {
        case Greet(whom) =>
          // Welcome! attendance increases
          attendance += 1
          context.log.info(s"Hello, $whom! Attendance is [$attendance]")
        case GoodBye(whom) =>
          // Goodbye! attendance decreases
          attendance -= 1
          context.log.info(s"Goodbye, $whom! Attendance is [$attendance]")
      }

      this
    }
  }
}
