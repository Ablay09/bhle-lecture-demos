package week7.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import week7.model.{Book => BookModel}

object Library {

  case class CreateBook(id: String, name: String, author: String, year: Int)

  case object GetBooks

  case class Books(books: Seq[BookModel])

  def props() = Props(new Library())

}

class Library extends Actor with ActorLogging {

//  import Library._

  var books = Map.empty[String, ActorRef] //scala.collection.mutable.Map.empty[String, ActorRef] //

  override def receive: Receive = {
    case Library.CreateBook(id, name, author, year) =>
      log.info(s"CreateBook with name $name received.")
      val book: ActorRef = context.actorOf(Book.props(id, name, author, year), id)

      books = books + (id -> book)

    case Library.GetBooks =>
      log.info("Received GetBooks")
      // send to all books GetData
      books.values.foreach(bookActor => bookActor ! Book.GetData)
      context.become(waitingResponses(books.size, sender(), Seq.empty[BookModel]))

  }

  def waitingResponses(responsesLeft: Int, replyTo: ActorRef, books: Seq[BookModel]): Receive = {
    case book: BookModel =>
      log.info(s"Received BookModel with name: ${book.name}. Responses left: $responsesLeft")
      if (responsesLeft - 1 == 0) {
        log.info("All responses received, replying to initial request.")
        replyTo ! Library.Books(books :+ book)
        context.become(receive)
      }
      else context.become(waitingResponses(responsesLeft - 1, replyTo, books = books :+ book))
  }

}
