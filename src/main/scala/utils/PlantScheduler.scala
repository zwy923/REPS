class PlantScheduler(plantManager: PlantManager) {
  private var energyDemand: Double = 0.0

  // Update the energy demand
  def updateEnergyDemand(newDemand: Double): Unit = {
    if(newDemand >= 0)
      energyDemand = newDemand
    else
      throw new IllegalArgumentException("Energy demand must be non-negative.")
  }

  // Check if the current energy output meets the demand
  def checkEnergyOutput(): Boolean = {
    val totalOutput = plantManager.getTotalOutput
    totalOutput >= energyDemand
  }

  // Try to balance the energy output and the demand
  def balanceEnergyOutput(): Unit = {
    val totalOutput = plantManager.getTotalOutput

    // If the output is less than the demand, try to start some plants
    if(totalOutput < energyDemand) {
      for((id, plant) <- plantManager.getAllPlants if totalOutput < energyDemand) {
        if(plant.getStatus == "Shutdown") {
          plant.start()
          plant.updateOutput()
        }
      }
    }
    // If the output is greater than the demand, try to shutdown some plants
    else if(totalOutput > energyDemand) {
      for((id, plant) <- plantManager.getAllPlants if totalOutput > energyDemand) {
        if(plant.getStatus == "Running") {
          plant.shutdown()
          plant.updateOutput()
        }
      }
    }
  }
}