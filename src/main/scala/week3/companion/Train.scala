package week3.companion

import week2.model.Human


object Train extends Human {
  def areaOfWheels(train: Train): Int = train.numberOfWheels * 2 * 1 * 3

  override def name: String = ???

  def trainFactory(model: String): Train = {
    // retrieves data about model
    val nWheels = model match {
      case "Talgo" => 100
      case "Paravoz" => 60
      case "Metro" => 78
    }

    Train(model, nWheels)
  }

  // apply

//  def apply(model: String, numberOfWheels: Int): Train = new Train(model, numberOfWheels)
}

case class Train(model: String, numberOfWheels: Int)
