package week7

import akka.actor.{Actor, ActorLogging, Props}
import week7.actors.Library

object Bot {
  def props() = Props(new Bot())
  case object StartTest
}

class Bot extends Actor with ActorLogging {
  import Bot._

  val library = context.actorOf(Library.props(), "library")

  override def receive: Receive = {
    case StartTest =>
      library ! Library.CreateBook("1", "50 shades of grey", "Kuat", 2019)
      library ! Library.CreateBook("2", "Algorithms and DS", "Corman", 2000)
      library ! Library.CreateBook("3", "3 pals", "Remark", 1863)
      library ! Library.CreateBook("4", "OOP", "Pakita", 2013)
      library ! Library.CreateBook("5", "Martin Eden", "London", 1910)


      library ! Library.GetBooks
      library ! Library.CreateBook("6", "Test book", "Adam", 1900)


    case Library.Books(books) =>
      books.foreach(b => log.info(s"${b.name}"))
  }
}
