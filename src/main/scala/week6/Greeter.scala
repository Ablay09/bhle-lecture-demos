package week6

import akka.actor.{Actor, ActorLogging}


// companion object
object Greeter {

  case class Greeting(message: String)
  case object Greet

  case class MyGreeting(message: String)

}

class Greeter extends Actor with ActorLogging {
  import Greeter._

  var greeting: String = ""

  // receives Any => Unit
  override def receive: PartialFunction[Any, Unit] = {
    // handle messages here
    // use pattern matching

    case Greeting(message) =>
      log.debug(s"Received Greeting with message: $message")
      greeting = message

    case Greet =>

      // actor who sent current message
      // ! -- send
      sender() ! MyGreeting(greeting)


  }
}
