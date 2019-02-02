package week1

object Boot extends App {
  println("Hello World")

  val x: Int = 3

  // immutable
  val str: String = "abc"

  // mutable variable
  var var1 = "abc"
  var1 = var1 + "def"



  // `if` conditional is expression in Scala
  val biggerThanThree: Any = if (x > 3) "is bigger" else true

  println(biggerThanThree)


  /*============ Functions =================*/
  val add: (Int, Int) => Int = (x: Int, y: Int) => x + y
  val sub: (Int, Int) => Int = (x: Int, y: Int) => x - y

  // type of this function is (Int, Int, (Int, Int) => Int) => Int
  // it takes 3 arguments
  // argument-1: (Int, Int) => Int
  // argument-2, argument-2: Int
  // returns Int
  val applyOperation: (Int, Int, (Int, Int) => Int) => Int = (x: Int, y: Int, fun: (Int, Int) => Int) => fun(x, y)

  def aOperation(f: (Int, Int) => Int, x: Int, y: Int): Int = {
    val result = f(x, y)
    result
  }

  println(aOperation(add, 5, 6)) // 11
  println(aOperation(sub, 5, 6)) // -1


  class Student(var x: Int, y: Int = 6) {

    def getX = x
  }

  val student = new Student(3, 4)
  val student2 = new Student(3)
  student.x
  student.getX


  // interface + concrete methods/parameters
  trait Being

  case class Human(name: String, age: Int) extends Being

  case class Car(model: String) extends Being

  val human1 = Human("Ivan", 23)

  val toyota = Car("Toyota")

  def printInfo(being: Being): Unit = being match {
    case Human(name, age) => println("he is " + age + " old")
    case Car(model) => println("This is " + model)
  }

//  val xyz = being match {
//    case Human(name, age) => name
//    case Car(model) => model
//  }

  printInfo(human1)
  printInfo(toyota)
}
