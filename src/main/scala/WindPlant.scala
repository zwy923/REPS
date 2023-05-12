class WindPlant(id: String, capacity: Double, val turbines: Int) extends EnergyPlant(id, capacity) {
  // We can assume that the output of a wind plant depends on the number of turbines and the wind speed
  // Here, we will use a simplified model where the wind speed is represented by a factor between 0 and 1
  private var windSpeed: Double = 1.0

  // Update the wind speed
  def updateWindSpeed(newSpeed: Double): Unit = {
    if(newSpeed >= 0 && newSpeed <= 1)
      windSpeed = newSpeed
    else
      throw new IllegalArgumentException("Wind speed must be between 0 and 1.")
  }

  // Override the updateOutput method to take into account the wind speed
  override def updateOutput(): Unit = {
    val newOutput = windSpeed * turbines
    if (newOutput >= 0 && newOutput <= capacity)
      currentOutput = newOutput
    else if(newOutput > capacity)
      currentOutput = capacity
  }
}