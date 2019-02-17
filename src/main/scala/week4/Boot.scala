package week4

object Boot extends App {
  println(5 + 5)
  println(5.+(5)) // equivalent




  // exercise: A traffic light is red, green, or yellow.
  // Translate this description into Scala code.

  // Traffic light: Red or Green or Yellow

  sealed trait TrafficLight

  // class -- object
  // case class -- case object
  case object Red extends TrafficLight
  case object Green extends TrafficLight
  case object Yellow extends TrafficLight



  // Recursive contruction

  case class RecursiveClass(recursiveClass: RecursiveClass)

  val rc = RecursiveClass(RecursiveClass(RecursiveClass(RecursiveClass(null))))

  // null Pointer
  // rc.recursiveClass.recursiveClass.recursiveClass.recursiveClass.recursiveClass.recursiveClass.recursiveClass


  // Linked List
  sealed trait IntList {

    // sum of members of IntList
    def sum(acc: Int = 0): Int = this match {
      case End => acc
      case Node(head, tail) => tail.sum(acc + head)
    }
  }
  case object End extends IntList
  case class Node(head: Int, tail: IntList) extends IntList


  val intList = Node(1, Node(2, Node(3, Node(4, End))))

  // print list contents
  // () means Unit
  def printList(intList: IntList): Unit = intList match {
    case End => println()
    case Node(head, tail) =>
      print(s"$head ")
      printList(tail)
  }

  printList(intList)

  // reverse print of linked list
  def reversePrintList(intList: IntList): Unit = intList match {
    case End => ()
    case Node(head, tail) =>
      reversePrintList(tail)
      print(s"$head ")
  }

  reversePrintList(intList)
  println()


  // Tail recursion
  // Sum all numbers until n

  // Recursion (normal)
  def sumRecursive(n: Int): Int = n match {
    case 0 => 0
    case k: Int => k + sumRecursive(k - 1)
  }
  println(s"Sum until 10: ${sumRecursive(10)}")


  // Tail recursion
  def sumTailRecursive(n: Int, acc: Int = 0): Int = n match {
    case 0 => acc
    case k: Int => sumTailRecursive(k - 1, acc + k)
  }
  println(s"Sum (tail recursive) until 10: ${sumTailRecursive(10)}")

  // implement sum for linked list using tail recursion
  println(s"sum of list: ${intList.sum()}")





  /* =============== Collections =================== */
  val intSeq: Seq[Int] = Seq(102, 23, 32, 4, 0, 928, 8)


  // number (Alt + Equal(=))
  intSeq.foreach(number => println(number))
  intSeq.foreach(println(_))

  // println: Any => Unit
  // Int IS Any
  intSeq.foreach(println)

  // MAP
//  val squares = intSeq.map(number => number * number)
//  squares.foreach(println)
  intSeq.map(number => number * number).foreach(println)

  val stringSeq: Seq[String] = intSeq.map(number => number.toString)


  // FLATMAP

  // permutations
  // "abc" => List("abc", "acb", "bac", "bca", "cab", "cba")
  def permutation(n: String) = n.permutations
//
  val permutations: Seq[Seq[String]] = intSeq.map(number => number.toString.permutations).map(iter => iter.toSeq)
  permutations.foreach(sequence => println(sequence.toList))

  val flattened: Seq[String] = permutations.flatten
  println(flattened.toList)

  // flatten + map
  val permutations2: Seq[String] = intSeq.map(number => number.toString.permutations).flatMap(iter => iter.toSeq)
  val permutations3: Seq[Char] = intSeq.flatMap(number => number.toString.permutations).flatMap(iter => iter.toSeq)
//  println(permutations3)















}
