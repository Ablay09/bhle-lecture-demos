package week2.model

case class Student(name: String, age: Int) extends Human

class SuperStudent(override val name: String, override val age: Int, school: String) extends Student(name, age)

// cannot inherit from SuperStudent
//case class UltraSuperStudent(override val name: String, override val age: Int, school: String) extends SuperStudent(name, age, school)