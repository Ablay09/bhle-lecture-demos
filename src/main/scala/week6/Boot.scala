package week6

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object Boot extends App {

  def divide(x: Int, y: Int): Either[String, Int] = {
    if (y == 0) Left("Error when diving")
    else Right(x / y)
  }

  def sumHatesFive(x: Int, y: Int) = {
    if (x == 5 || y == 5) Left("I hate FIVE")
    else Right(x + y)
  }

  def subtractHatesSix(x: Int, y: Int) = {
    if (x == 6 || y == 6) Left("I hate SIX")
    else Right(x - y)
  }


  val result: Either[String, Int] = sumHatesFive(6, 0).flatMap { sumResult =>
    subtractHatesSix(10, sumResult).flatMap { subResult =>
      divide(9, subResult)
    }
  }

  result match {
    case Right(res) => println(s"result is $res")
    case Left(err) => println(s"error: $err")
  }

//  Try(5 / 0) match {
//    case Success(result) =>
//    case Failure(exception) =>
//  }



  // Either -> Option
  val opt: Option[Int] = result.toOption

  // Option -> Either
  val either: Either[String, Int] = opt.toRight("Some Error")

  // TODO: research
//  val a: String <:< Throwable = new Throwable("bla-bla")
//  result.toTry

  val ourTry = Try(5 / 0)

  // try -> option
  ourTry.toOption

  // try -> either
  val throwableTry: Either[Throwable, Int] = ourTry.toEither

  val future1 = Future {
    5 + 6
  }


}
