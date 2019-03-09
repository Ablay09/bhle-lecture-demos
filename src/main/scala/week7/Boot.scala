package week7

import akka.actor.ActorSystem

object Boot extends App {
  val system = ActorSystem()
  val bot = system.actorOf(Bot.props(), "bot")

  bot ! Bot.StartTest
}
