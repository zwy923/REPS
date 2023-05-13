class HydropowerPlant(id: String, val maxOutput: Double, weatherData: WeatherData) extends EnergyPlant(id) {
  override def updateOutput(): Unit = {
    if (getStatus == "Running") {
      // Assume the output is proportional to the water flow
      val potentialOutput = weatherData.getWaterFlow * 100

      // Make sure the output does not exceed the max output
      currentOutput = Math.min(potentialOutput, maxOutput)
    } else {
      currentOutput = 0.0
    }
  }

  // You can add more specific methods for HydropowerPlant here
  // ...
}