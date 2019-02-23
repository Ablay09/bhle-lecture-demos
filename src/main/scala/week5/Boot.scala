package week5

import scala.concurrent.{Await, ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object Boot extends App {

  val intSeq: Seq[Int] = Seq(102, 23, 32, 4, 0, 928, 8)


  // appending
  val newSeq = intSeq :+ 3
//  newSeq.foreach(println)


  // immutable variable, mutable collection
  val mutableArray = collection.mutable.ArrayBuffer(1, 2, 3)

  mutableArray.+=(5)
//  mutableArray foreach println

  var l = List(1, 2, 3)

  l = l :+ 5

//  l.foreach(println)

  // prepend
  val l3 = 8 +: List(9, 10)

  // List(1, 2, 3, 4, 5, 6)
  val twoList = (List(1, 2, 3) ++ List(4, 5, 6)).++(List(0))


  // filter
//  intSeq.filter(number => number % 2 == 0).foreach(println)


  // sort with
//  println(intSeq.sortWith((x, y) => x < y))
//  println(intSeq.sortWith((x, y) => x > y))

  // find left for later


  // contains
//  println(intSeq.contains(4))
//  println(intSeq.contains(1000))



  // =============== Options ==================
  def readInt(str: String): Option[Int] =
    if(str matches "\\d+") Some(str.toInt) else None


  println(readInt("123"))
  println(readInt("12314fasd"))
  // println("12u3p4oi".toInt) // throws exception
  println(readInt("0"))
  println(readInt("poqw"))

  // Get of else

  val opt1: Option[Int] = readInt("123")
  val optionValue = opt1.getOrElse(0)
  println(optionValue)
  println(readInt("asb").getOrElse(0))

  // pattern match

  opt1 match {
    case Some(value) => println(s"Option has value: $value")
    case None => println("Option has none value")
  }

  println(opt1.filter(number => number  != 123))
  println(readInt("567").filter(number => number  != 123))


  // find in collections

  println(intSeq.find(x => x == 32))
  println(intSeq.find(x => x == 532))

  // sum of 2, 3 options

  def sumOfOptionsUsingBoilerplate(opt1: Option[Int], opt2: Option[Int], opt3: Option[Int]): Option[Int] = {
    opt1 match {
      case Some(value1) => opt2 match {
        case Some(value2) => opt3 match {
          case Some(value3) => Some(value1 + value2 + value3)
          case None => Some(value1 + value2)
        }
        case None => Some(value1)
      }
      case None => None
    }
  }

  def sumOfOptionsUsingMaps(opt1: Option[Int], opt2: Option[Int], opt3: Option[Int]): Option[Int] = {
    opt1.flatMap { value1 =>
      opt2.flatMap { value2 =>
        opt3.map { value3 =>
          value1 + value2 + value3
        }
      }
    }
  }

  println(sumOfOptionsUsingMaps(Some(3), Some(6), Some(10)))
  // returns None
  println(sumOfOptionsUsingMaps(Some(3), None, Some(10)))

  // for comprehension
  def sumOfOptionsUsingFor(opt1: Option[Int], opt2: Option[Int], opt3: Option[Int]): Option[Int] = {
    for {
      value1 <- opt1
      value2 <- opt2
      value3 <- opt3
    } yield value1 + value2 + value3
  }

  // for
  // x <- collection
  // y <- monad

  println(sumOfOptionsUsingFor(Some(3), Some(6), Some(10)))
  // returns None
  println(sumOfOptionsUsingFor(Some(3), None, Some(10)))



  /* ================ Future ================ */
//  implicit val ex: ExecutionContext
  def add(x: Int, y: Int): Future[Int] = Future { x + y }


  val future1 = add(2, 3)

  future1.onComplete {
    case Success(value) => println(s"Future result: $value")
    case Failure(exception) => println(s"Error: ${exception.getMessage}")
  }

  def subtract(x: Int, y: Int) = Future { x - y }

  def multiply(x: Int, y: Int) = Future {
    println(s"Multiplying $x and $y")
    x * y
  }

  def divide(x: Int, y: Int) = Future { x / y }


  val result: Future[Int] = add(2, 3).flatMap { addResult =>
    subtract(addResult, 2).flatMap { subtractResult =>
      multiply(subtractResult, 10).flatMap { multiplyResult =>
        divide(multiplyResult, 6)
      }
    }
  }

  // non-blocking execution
  result.onComplete {
    case Success(value) => println(s"Complex future result: $value")
    case Failure(exception) => println(s"Complex future error: ${exception.getMessage}")
  }

  // UNRESPONSIVE
  // Await.result(result)


  val result2: Future[Int] = add(20, 30).flatMap { addResult =>
    subtract(addResult, 10).flatMap { subtractResult =>
      divide(subtractResult, 0).flatMap { divisionResult =>
        multiply(divisionResult, 8)
      }
    }
  }

  result2.onComplete {
    case Success(value) => println(s"Complex future result: $value")
    case Failure(exception) => println(s"Complex future error: ${exception.getMessage}")
  }
  // Kostyl
  Thread.sleep(2000)

  // Try

//  try {
//
//  } catch {
//
//  }

  def divideTry(x: Int, y: Int): Try[Int] = Try(x / y)

  divideTry(4, 2) match {
    case Success(tryResult) => println(s"Try result: $tryResult")
    case Failure(ex) => println(s"Error: ${ex.getMessage}")
  }

  divideTry(4, 0) match {
    case Success(tryResult) => println(s"Try result: $tryResult")
    case Failure(ex) => println(s"Error: ${ex.getMessage}")
  }

}
