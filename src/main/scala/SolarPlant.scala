class SolarPlant(id: String, capacity: Double, val panels: Int) extends EnergyPlant(id, capacity) {
  // We can assume that the output of a solar plant depends on the number of panels and the sunlight intensity
  // Here, we will use a simplified model where the sunlight intensity is represented by a factor between 0 and 1
  private var sunlightIntensity: Double = 1.0

  // Update the sunlight intensity
  def updateSunlightIntensity(newIntensity: Double): Unit = {
    if(newIntensity >= 0 && newIntensity <= 1)
      sunlightIntensity = newIntensity
    else
      throw new IllegalArgumentException("Sunlight intensity must be between 0 and 1.")
  }

  // Override the updateOutput method to take into account the sunlight intensity
  override def updateOutput(): Unit = {
    val newOutput = sunlightIntensity * panels
    if (newOutput >= 0 && newOutput <= capacity)
      currentOutput = newOutput
    else if (newOutput > capacity)
      currentOutput = capacity
  }
}
