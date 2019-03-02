package week6

import akka.actor.{Actor, ActorLogging, ActorRef}

class GreeterManager(greeter: ActorRef, printer: ActorRef) extends Actor with ActorLogging {


  greeter ! Greeter.Greeting("Hi KBTU")

  greeter ! Greeter.Greet

  override def receive: Receive = {
    case Greeter.MyGreeting(msg) =>
      log.info("Received MyGreeting message. Sending message to printer")
      printer ! GreetPrinter.PrintGreeting(msg)
  }
}
