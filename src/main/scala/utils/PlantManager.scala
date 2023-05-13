import scala.collection.mutable

class PlantManager {
  private val plants: mutable.Map[String, EnergyPlant] = mutable.Map()

  def addPlant(plant: EnergyPlant): Unit = {
    if(plants.contains(plant.id))
      throw new IllegalArgumentException("A plant with the same ID already exists.")
    else
      plants += (plant.id -> plant)
  }

  def removePlant(id: String): Unit = {
    if(!plants.contains(id))
      throw new IllegalArgumentException("The plant does not exist.")
    else
      plants -= id
  }

  def getPlant(id: String): Option[EnergyPlant] = {
    plants.get(id)
  }

  def getAllPlants: Map[String, EnergyPlant] = {
    plants.toMap
  }

  def shutdownPlant(id: String): Unit = {
    getPlant(id) match {
      case Some(plant) => plant.shutdown()
      case None => throw new IllegalArgumentException("The plant does not exist.")
    }
  }

  def startPlant(id: String): Unit = {
    getPlant(id) match {
      case Some(plant) => plant.start()
      case None => throw new IllegalArgumentException("The plant does not exist.")
    }
  }

  def getTotalOutput: Double = {
    plants.values.map(_.getCurrentOutput).sum
  }
}