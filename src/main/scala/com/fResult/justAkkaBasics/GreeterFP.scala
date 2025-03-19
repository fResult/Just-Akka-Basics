package com.fResult.justAkkaBasics

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{Behaviors, StashBuffer}

object GreeterFP {
  // <- Protocol definition, the type of message(s) the actor handles
  sealed trait GreetCommand

  final case class Greet(whom: String) extends GreetCommand

  final case class GoodBye(whom: String) extends GreetCommand
  // Protocol definition ->

  final case class State(maxGreets: Int = 10
                         , minimumAttendance: Int = 5
                         , waitingRoomCount: Int = 0
                         , greetsCount: Int = 0)

  def apply(state: State): Behavior[GreetCommand] =
    Behaviors.withStash[GreetCommand](100) { stashBuffer =>
      waitingRoom(state, stashBuffer)
    }

  private def waitingRoom(state: State, stashBuffer: StashBuffer[GreetCommand]): Behavior[GreetCommand] = {
    Behaviors.receive { (context, message) =>
      message match {
        case Greet(whom) =>
          val newWaitingRoomCount = state.waitingRoomCount + 1
          context.log.info(s"Hi $whom, please go to the waiting room, Waiting count is [$newWaitingRoomCount/${state.minimumAttendance}]")

          val updatedState = state.copy(waitingRoomCount = newWaitingRoomCount)
          stashBuffer.stash(message)
          if (newWaitingRoomCount < state.minimumAttendance)
            waitingRoom(updatedState, stashBuffer)
          else
            stashBuffer.unstashAll(doGreeter(updatedState))
        case other =>
          context.log.error(s"[waitingRoom]: Only for Greet, $other")
          stashBuffer.stash(other)
          Behaviors.same
      }
    }
  }

  private def doGreeter(state: State): Behavior[GreetCommand] =
    Behaviors.receive { (context, message) =>
      message match {
        case Greet(whom) =>
          if (state.greetsCount < state.maxGreets) {
            val newGreetsCount = state.greetsCount + 1
            context.log.info(s"Welcome, $whom! Greet count is [$newGreetsCount/${state.maxGreets}]")
            doGreeter(state.copy(greetsCount = newGreetsCount))
          } else {
            context.log.warn(s"Sorry $whom, I'm too tired to greet! Greet count is already [${state.greetsCount}/${state.maxGreets}]")
            tiredGreeter()
          }
        case other =>
          context.log.error(s"[doGreeter]: Only for Greet, $other")
          doGreeter(state)
      }
    }

  private def tiredGreeter(): Behavior[GreetCommand] =
    Behaviors.receive { (context, message) =>
      message match
        case Greet(_) =>
          context.log.info("zZzZzZz")
        // Note: Here, once the greeter is tired, there is no return back!
        case other => context.log.error(s"[tiredGreeter]: Only for Greet, $other")

      tiredGreeter()
    }
}
