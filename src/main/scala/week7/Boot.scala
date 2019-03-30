package week7

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import model.{Book => BookModel}
import week7.actors.Library
import akka.pattern.ask
import akka.util.Timeout
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.concurrent.duration._


object Boot extends App with LibraryRoutes {
//  val system = ActorSystem()
//  val bot = system.actorOf(Bot.props(), "bot")
//
//  bot ! Bot.StartTest

  val log = LoggerFactory.getLogger("Boot")

  // needed to run the route
  implicit val system = ActorSystem()

  implicit val materializer = ActorMaterializer()
  // needed for the future map/flatmap in the end and future in fetchItem and saveOrder
  implicit val executionContext = system.dispatcher


  val library = system.actorOf(Library.props(), "library")

  val route =
    pathPrefix("library") {
      concat(
        bookRoutes,
        booksRoutes
      )
    }


  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  log.info("Listening on port 8080...")

}
