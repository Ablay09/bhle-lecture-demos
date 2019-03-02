package week6

import akka.actor.{ActorRef, ActorSystem, Props}

object ActorBoot extends App {

  val system = ActorSystem("my-first-system")
  // Props simplified: constructor for actors
  val greeter: ActorRef = system.actorOf(Props(new Greeter()), "greeter-actor")
  val printer: ActorRef = system.actorOf(Props(new GreetPrinter))
  val manager = system.actorOf(Props(new GreeterManager(greeter, printer)), "greeter-manager")

}
