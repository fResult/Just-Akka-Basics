package com.fResult.justAkkaBasics


object Greeter {
  def apply(): Behavior[Greet] =
    Behaviors.setup { context =>
      val childActorRef = context.spawn(ChildActor(), "childActorName")
      welcoming(childActorRef)
    }

  def welcoming(childActorRef: ActorRef[ChildCommand]) =
Behaviors.receive { (context, message) =>
  message match {
    case Greet(whom) =>
      val childActor = context.spawn(ChildActor(), "childActorName")
      // interact with the child to process the message,
      // or save the ActorRef in state to interact with it later
      Behaviors.same
  }
}

