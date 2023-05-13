class HydropowerPlant(id: String, maxOutput: Double, weatherData: WeatherData) extends EnergyPlant(id, maxOutput) {

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

}