package week7
import week5.Boot.readInt

object Test extends App {

  def divide(num1: Int, num2: Int): Option[Int] = {
    if (num2 == 0) None else Some(num1 / num2)
  }

  def calculator(operand1: Option[Int], operator: String, operand2: Option[Int]): Option[Int] = {
    for {
      value1 <- operand1
      value2 <- operand2
      result <- operator match {
        case "+" => Some(value1 + value2)
        case "-" => Some(value1 - value2)
        case "*" => Some(value1 * value2)
        case "/" => divide(value1, value2)
        case _ => None
      }
    } yield result
  }


  def printResult(opt: Option[Int]): String = opt match {
    case Some(res) => res.toString
    case None => "Generic Error"
  }

  println(printResult(calculator(readInt("8"), "/", readInt("2"))))
  println(printResult(calculator(readInt("8"), "+", readInt("2"))))
  println(printResult(calculator(readInt("8"), "*", readInt("2"))))
  println(printResult(calculator(readInt("8"), "/", readInt("0"))))
  println(printResult(calculator(readInt("8"), "/", readInt("2o"))))
  println(printResult(calculator(readInt("8o"), "/", readInt("2"))))
  println(printResult(calculator(readInt("8o"), "/", readInt("2p"))))
  println(printResult(calculator(readInt("8o"), "/", readInt("0"))))

}
