package week6

import akka.actor.{Actor, ActorLogging}

object GreetPrinter {
  case class PrintGreeting(message: String)
}

class GreetPrinter extends Actor with ActorLogging {
  import GreetPrinter._

  override def receive: Receive = {

    case PrintGreeting(msg) =>
      log.info("Received a message to print")
      println(msg)
  }
}
