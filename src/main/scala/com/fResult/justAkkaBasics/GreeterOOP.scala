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

  def apply(maxGreets: Int = 10): Behavior[GreetCommand] = Behaviors.setup(context => new GreeterBehavior(context, maxGreets))

  // The case class we define for our behaviors within our actor
  private class GreeterBehavior(context: ActorContext[GreetCommand],
                                private val maxGreets: Int) extends AbstractBehavior[GreetCommand](context) {

    // We add mutable state as a class field
    private var greetsCount = 0

    override def onMessage(message: GreetCommand): Behavior[GreetCommand] = {
      message match
        case Greet(whom) =>
          if (greetsCount < maxGreets) {
            greetsCount += 1
            context.log.info(s"Hello, $whom! Greet count [$greetsCount]")
          } else {
            // If we reached max greets count...
            context.log.info(s"Sorry $whom, I'm too tired to greet! Greet count [$greetsCount]")
          }

        case GoodBye(_) => context.log.error("No Goodbye")

      this
    }
  }
}