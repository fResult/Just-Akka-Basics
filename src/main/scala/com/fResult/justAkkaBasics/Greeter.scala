package com.fResult.justAkkaBasics

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext}

object Greeter {
  // <- Protocol definition, the type of message(s) the actor handles
  // Note: We support a single message type here.
  // We will see later how to support multiple message types.
  final case class Greet(whom: String)
  // Protocol definition ->

  // The case class we define for our behaviors within our actor
  class GreeterBehavior(context: ActorContext[Greet]) extends AbstractBehavior[Greet](context) {
    override def onMessage(message: Greet): Behavior[Greet] = ???
  }
}
