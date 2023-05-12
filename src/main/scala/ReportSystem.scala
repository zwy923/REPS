class ReportSystem(plantManager: PlantManager) {
  // Generate a report of the status of all plants
  def generateStatusReport(): Unit = {
    val allPlants = plantManager.getAllPlants

    allPlants.foreach { case (id, plant) =>
      println(s"Plant ID: $id")
      println(s"Type: ${plant.getClass.getSimpleName}")
      println(s"Status: ${plant.getStatus}")
      println(s"Current Output: ${plant.getCurrentOutput}")
      println("-----------------------------")
    }
  }

  // Generate a report of the total energy output
  def generateOutputReport(): Unit = {
    val totalOutput = plantManager.getTotalOutput
    println(s"Total Energy Output: $totalOutput")
  }
}
