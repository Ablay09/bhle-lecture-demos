package week3

import week2.model.{Human, Student, Teacher}
import week3.companion.Train
import week3.model.{Animal, Cat, Dog, Parrot}

object Boot extends App {

  val humans: List[Human] =
    List(Student("Sedi", 21), Teacher("t-1", "Ivan"), Student("Abilay", 20), Teacher("123", "Abilhozhyn"))

  humans.foreach { human =>
    human match {
      case Student(name, age) => println(s"Student with name: $name and age: $age")
      case Teacher(_, name) => println(s"Teacher with name: $name")
    }
  }

  humans.foreach { human =>
    human match {
      case student: Student => println(s"Student with name: ${student.name} and age: ${student.age}")
      case Teacher(_, name) if name == "Ivan" => println(s"Teacher Ivan with name: $name")
      case Teacher(_, name) => println(s"Teacher with name: $name")
    }
  }

  humans.foreach { human =>
    human match {
      case any: Any => println(s"This is any: $any")
      case student: Student => println(s"Student with name: ${student.name} and age: ${student.age}")
      case Teacher(_, name) if name == "Ivan" => println(s"Teacher Ivan with name: $name")
      case Teacher(_, name) => println(s"Teacher with name: $name")
    }
  }

  val animals: List[Animal] = List(Cat("Ceasar"))

  animals.foreach { animal => animal match {
    case cat: Cat => println("cat")
    case dog: Dog => println("dog")
    case any: Any => println("this is any")
  }
  }


  println(Utils.generateRandomNumber())


  /*================ Companion Objects =============*/

  val train: Train = Train.trainFactory("Talgo")
  println(s"Area of wheels: ${Train.areaOfWheels(train)}")

  val train2 = Train("Paravoz", 32)
//  val train3 = Train.apply("Paravoz", 32)


  /*============= For Comprehensions ============*/
  def foo(n: Int, v: Int) =
    for (i <- 0 until n;
         j <- 0 until n if i < j;
         k <- 0 until n if i + j + k == v)
      yield (i, j, k)

  foo(10, 10) foreach {
    case (i, j, k) =>
      println(s"($i, $j, $k) ")
  }


  val addOne: Int => Int = (x: Int) => x + 1

  def walk[T <: Animal](obj: T): Unit = {

    // inner method
    def myMethod = "my method"

    println(obj.walk)
  }


  /******** self type ****************/

  val parrot = Parrot(8)
  println(parrot.walkingAnimal)


  /********** Implicits ***************/

  implicit val dbConnection: String = "Connected to DB"

  // 2nd implicit gives error
//  implicit val dbConnection2 = "Connected to DB"


  def createUser(userName: String)(implicit dbCon: String) = {
    s"Created a user using $dbCon"
  }

  def deleteUser(userName: String)(implicit dbCon: String) = {
    s"Deleted a user using $dbCon"
  }

  def modifyUser(userName: String)(implicit dbCon: String) = {
    "Modifyed a user"
  }

  val explicitCon = "another connection"
  println(createUser("Ivan")) // implicitly
  println(deleteUser("Yuriy")(explicitCon)) // explicit


  // implicit Dog => Int
  // implicit conversion (of types)
  implicit def dogInt(dog: Dog): Int = 4

  // uses dogInt to convert Dog to Int
  println(5 + Dog("Goofy"))

}
