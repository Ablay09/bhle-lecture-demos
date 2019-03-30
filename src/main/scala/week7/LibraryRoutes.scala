package week7

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives._
import week7.Boot.library
import week7.actors.Library
import model.{Book => BookModel}
import akka.pattern.ask
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.util.Timeout

import scala.concurrent.duration._

trait LibraryRoutes extends JsonSupport {

  def library: ActorRef

  implicit val timeout = Timeout(30.seconds)

  val bookRoutes = path("book") {
    // HTTP POST
    concat(
      post {
        entity(as[BookModel]) { bookModel =>
          complete {
            // type cast to Library.Response
            //              "OK"
            // return type: String, Int, Standard library types, Future, Either, Option
            (library ? Library.CreateBook(bookModel.id, bookModel.name, bookModel.author, bookModel.year))
              .mapTo[Either[model.ErrorInfo, Library.Response]]
          }
        }
      },
      get {
        parameters("id".as[String]) { id =>
          complete {
            (library ? Library.GetBook(id)).mapTo[Either[model.ErrorInfo, BookModel]]
          }
        }
      }
    )
  }

  val booksRoutes = path("books") {
    get {
      complete {
        (library ? Library.GetBooks).mapTo[Library.Books]
      }
    }
  }

}
