class HydropowerPlant(id: String, maxOutput: Double, weatherData: WeatherData) extends EnergyPlant(id, maxOutput) {

  override def updateOutput(): Unit = {
    if (getStatus == "Running") {

      val potentialOutput = weatherData.getWaterFlow * 100

      currentOutput = Math.min(potentialOutput, maxOutput)
    } else {
      currentOutput = 0.0
    }
  }

}