package com.fResult.justAkkaBasics

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object GreeterFP {
  // <- Protocol definition, the type of message(s) the actor handles
  sealed trait GreetCommand

  final case class Greet(whom: String) extends GreetCommand

  final case class GoodBye(whom: String) extends GreetCommand
  // Protocol definition ->

  final case class State(greetsCount: Int = 0, maxGreets: Int = 10)

  def apply(state: State): Behavior[GreetCommand] = doGreeter(state)

  private def doGreeter(state: State): Behavior[GreetCommand] =
    Behaviors.receive { (context, message) =>
      message match {
        case Greet(whom) =>
          if (state.greetsCount < state.maxGreets) {
            val newGreetsCount = state.greetsCount + 1
            context.log.info(s"Hello, $whom! Greet count is [$newGreetsCount]")
            doGreeter(state.copy(greetsCount = newGreetsCount))
          } else {
            context.log.warn(s"Sorry $whom, I'm too tired to greet! Greet count [${state.greetsCount}]")
            tiredGreeter()
          }
        case _ =>
          context.log.error("Only for Greet")
          doGreeter(state)
      }
    }

  private def tiredGreeter(): Behavior[GreetCommand] =
    Behaviors.receive { (context, message) =>
      message match
        case Greet(_) =>
          context.log.info("zZzZzZz")
        // Note: Here, once the greeter is tired, there is no return back!
        case _ => context.log.error("Only for Greet")

      tiredGreeter()
    }
}
