package week7.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import week7.model.FullName

object Reader {
  case object GetData
  case class BorrowBook(book: String)
  case class ReturnBook(bookId: String)
  case class Acknowledge(id: String)
  case class NoAcknowledge(id: String)

  def props(id: String, fullName: FullName) = Props(new Reader(id, fullName))
}

class Reader(id: String, fullName: FullName) extends Actor with ActorLogging {
  import Reader._

  override def receive: Receive = {
    case BorrowBook(bookId) =>
      sender() ! Acknowledge(id)
      log.info(s"I borrowed book with id: $id")
      context.become(bookBorrowed(bookId))

    case GetData => ???

  }

  def bookBorrowed(bookId: String): Receive = {
    case BorrowBook(_) =>
      sender() ! NoAcknowledge(id)

    case ReturnBook(borrowedId) =>
      if (borrowedId == bookId) {
        sender() ! Acknowledge(id)
        context.become(receive)
      } else {
        sender() ! NoAcknowledge(id)
      }
  }

}
