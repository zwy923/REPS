class HydroPlant(id: String, capacity: Double, val turbines: Int) extends EnergyPlant(id, capacity) {
  // We can assume that the output of a hydro plant depends on the number of turbines and the water flow
  // Here, we will use a simplified model where the water flow is represented by a factor between 0 and 1
  private var waterFlow: Double = 1.0

  // Update the water flow
  def updateWaterFlow(newFlow: Double): Unit = {
    if(newFlow >= 0 && newFlow <= 1)
      waterFlow = newFlow
    else
      throw new IllegalArgumentException("Water flow must be between 0 and 1.")
  }

  // Override the updateOutput method to take into account the water flow
  override def updateOutput(): Unit = {
    val newOutput = waterFlow * turbines
    if (newOutput >= 0 && newOutput <= capacity)
      currentOutput = newOutput
    else if (newOutput > capacity)
      currentOutput = capacity
  }
}