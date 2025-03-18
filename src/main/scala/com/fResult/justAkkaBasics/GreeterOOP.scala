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

    override def onMessage(message: GreetCommand): Behavior[GreetCommand] = {
      message match {
        case Greet(whom) => context.log.info(s"Hello, $whom!")
        case GoodBye(whom) => context.log.info(s"Goodbye, $whom!")
      }
      // returning `this` allows the next message to be processed
      // by the same behavior
      this
    }
  }
}
