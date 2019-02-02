package week2

import week2.model.{Human, Student, SuperStudent, Teacher}

object Boot extends App {

  var aida = Student("Aida", 20)

  aida = aida.copy(age = 21)

  val abilay = new SuperStudent("Abilay", 22, "KBTU")

//  val adilhan = UltraSuperStudent("Adilhan", 23, "KBTU")

  println(abilay.age)
//  println(adilhan.age)

  val humans: List[Human] = List(aida, Teacher("123", "Ivan"), Student("Baha", 25), Teacher("321", "Roman"))

  humans.foreach { human =>
    human match {
      case student: Student => println("This is a student: " + student.name)
      case Teacher(_, name) => println("This is a teacher: " + name)
    }
  }

  // long tuple

  val longTuple = (1, 2, 3, 4, "5", 6, 7, 8)

  // print 5th member
  println(longTuple._5)





  // High-order function

  def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
    val schema = if (ssl) "https://" else "http://"
    (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
  }

  val domainName = "www.example.com"

  // function for https://www.example.com
  def getURL: (String, String) => String = urlBuilder(ssl=true, domainName)

  def getUrlKbtu = urlBuilder(ssl = true, "www.kbtu.kz")

  val endpoint = "users"
  val query = "id=1"

  val url = getURL(endpoint, query)

  val kbtuUrl = getUrlKbtu("fit", "id=0")

  println(s"url: $url")
  println(s"KBTU url: $kbtuUrl")





  // Multiple parameters
//  def applyFunction(x: Int, y: Int)(func: (Int, Int)=> Int, func2: (Int, Int, Int) => Int)

  val students = List(aida, Student("Baha", 21), Student("Kairosh", 29))

  val totalAgeLeft = students.foldLeft(0)((a, b) => a + b.age)

  val notAnon: (Student, Int) => Int = (student: Student, agg: Int) => student.age + agg

  val totalAgeRight = students.foldRight(0)(notAnon)

  println(totalAgeLeft)
  println(totalAgeRight)

  // case class comparison

  val aida2 = Student("Aida", 21)

  println(s"Comparison of 2 Aidas: ${aida == aida2}")
  println(s"Aida: $aida")


}
