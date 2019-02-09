package week3.model


// goal: this trait needs Animal
// but we don't want to extend Animal
trait WalkingAnimal {
  this: Animal =>

  // override
  override def walk = "my walk"

  def walkingAnimal = s"animal $walk"

  // I want to use Aninal's walk
}
