package com.fResult.justAkkaBasics

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object GreeterFP {
  // <- Protocol definition, the type of message(s) the actor handles
  sealed trait GreetCommand

  final case class Greet(whom: String) extends GreetCommand

  final case class GoodBye(whom: String) extends GreetCommand
  // Protocol definition ->

  final case class State(attendance: Int = 0)

  def apply(state: State = State(0)): Behavior[GreetCommand] =
    Behaviors.receive[GreetCommand] { (context, message) =>
      message match {
        case Greet(whom) =>
          context.log.info(s"Hello, $whom! Attendance is [${state.attendance + 1}]")
          // We update the behavior to handle the next message
          // with new immutable state
          apply(state.copy(state.attendance + 1))
        case GoodBye(whom) =>
          context.log.info(s"Goodbye, $whom! Attendance is [${state.attendance - 1}]")
          // Updating state for Goodbye as we did with Greet
          apply(state.copy(state.attendance - 1))
      }
    }
}
