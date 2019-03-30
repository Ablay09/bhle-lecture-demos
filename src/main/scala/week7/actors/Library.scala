package week7.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, ReceiveTimeout}
import week7.model.{ErrorInfo, FullName, Book => BookModel}

import scala.concurrent.duration._

object Library {

  case class CreateBook(id: String, name: String, author: String, year: Int)

  case class CreateReader(id: String, name: FullName)

  case class BorrowBook(bookId: String, readerId: String)

  case class ReturnBook(bookId: String, readerId: String)

  case class Response(message: String)

  case object GetBooks

  case class GetBook(id: String)

  case class Books(books: Seq[BookModel])

  def props() = Props(new Library())

//  type BookId = String

}

class Library extends Actor with ActorLogging {

  // set timeout
  context.setReceiveTimeout(3.seconds)

//  import Library._

  var books = Map.empty[String, ActorRef] //scala.collection.mutable.Map.empty[String, ActorRef] //
  var readers = Map.empty[String, ActorRef]

  // (readerId -> bookId)
  var borrowedBooks = Map.empty[String, String]

  private val funBorrowBook = (bookId: String, readerId: String, map: Map[String, String]) => {
    map + (readerId -> bookId)
  }

  private val funReturnBook = (bookId: String, readerId: String, map: Map[String, String]) => {
    map - readerId
  }

  override def receive: Receive = {
    case Library.CreateBook(id, name, author, year) =>
      log.info(s"CreateBook with name $name received.")

      if (books.contains(id)) {
        log.warning(s"Id: $id already exists.")
        sender() ! Left(ErrorInfo(409, s"Book with id: $id already exists"))
      } else {
        val book: ActorRef = context.actorOf(Book.props(id, name, author, year), id)
        books = books + (id -> book)
        sender() ! Right(Library.Response("OK"))
      }

    case Library.CreateReader(id, fullName) =>
      val reader = context.actorOf(Reader.props(id, fullName), id)
      readers = readers + (id -> reader)

    case Library.BorrowBook(bookId, readerId) =>
      log.info(s"Received BorrowBook with bookId: $bookId and readerId: $readerId")
      // FIXME: use options
      if (books.keySet.contains(bookId) && readers.keySet.contains(readerId)) {
        readers(readerId) ! Reader.BorrowBook(bookId)
        context.become(waitingAck(sender(), bookId, funBorrowBook))
      } else {
        // TODO: reply with error
      }

    case Library.ReturnBook(bookId, readerId) =>
      if (books.keySet.contains(bookId) && readers.keySet.contains(readerId)) {
        readers(readerId) ! Reader.ReturnBook(bookId)
        context.become(waitingAck(sender(), bookId, funReturnBook))
      } else {
        // TODO: reply with error
      }

    case Library.GetBook(id) =>
      log.info(s"Received GetBook with id: $id")
      books.get(id) match {
        case Some(bookActor) =>
          bookActor ! Book.GetData
          context.become(waitingBookResponse(sender()))
        case None => sender() ! Left(ErrorInfo(404, s"Book with id: $id not found."))
      }

    case Library.GetBooks =>
      log.info("Received GetBooks")
      // send to all books GetData
      books.values.foreach(bookActor => bookActor ! Book.GetData)
      context.become(waitingResponses(books.size, sender(), Seq.empty[BookModel]))

  }

  def waitingBookResponse(replyTo: ActorRef): Receive = {
    case book: BookModel =>
      replyTo ! Right(book)
      context.become(receive)

    case ReceiveTimeout =>
      replyTo ! Left(ErrorInfo(504, "Timeout when looking for book."))
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

  def waitingAck(replyTo: ActorRef, bookId: String, fun: (String, String, Map[String, String]) => Map[String, String]): Receive = {
    case Reader.Acknowledge(id) =>
//      borrowedBooks = //function or method (return Map[String, String]      borrowedBooks + (id -> bookId)

      // return
//      borrowedBooks = borrowedBooks - id

      borrowedBooks = fun(bookId, id, borrowedBooks)
      replyTo ! Library.Response("OK")

    case Reader.NoAcknowledge(id) =>
      replyTo ! Library.Response("Not OK")

    case ReceiveTimeout =>
      log.error("Received timeout while waiting for Ack(s)")
  }

}
