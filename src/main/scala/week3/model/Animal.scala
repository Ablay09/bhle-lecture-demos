package week3.model

trait LiveBeing
sealed trait Animal extends LiveBeing {
  def walk = "walking"
}

case class Cat(name: String) extends Animal
case class Dog(name: String) extends Animal {
  def add(otherDog: Dog) = ???
}
case class Parrot(legs: Int) extends Animal with WalkingAnimal
