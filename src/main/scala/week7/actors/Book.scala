package week7.actors

import akka.actor.{Actor, ActorLogging, Props}
import week7.model.{Book => BookModel}

object Book {
  // actor
  def props(id: String, name: String, author: String, year: Int) = Props(new Book(id, name, author, year))

  case object GetData
}

class Book(id: String, name: String, author: String, year: Int) extends Actor with ActorLogging {
  import Book._

  override def receive: Receive = {
    case GetData =>
      log.info("Received GetData request")
      sender() ! BookModel(id, name, author, year)
  }

}
