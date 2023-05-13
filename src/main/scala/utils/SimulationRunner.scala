import Main.dataCollector

import java.io.{BufferedWriter, FileWriter}
import java.time.LocalDate
import scala.io.Source

class SimulationRunner(initialDate: LocalDate, plantManager: PlantManager, alertSystem: AlertSystem, weatherData: WeatherData, fileName: String = "historydata.csv") {
  private var currentDate: LocalDate = initialDate

  def runSimulation(days: Int): Unit = {
    val writer = new BufferedWriter(new FileWriter(fileName, true))

    // Add header if the file is empty
    val source = Source.fromFile(fileName)
    if (source.getLines().isEmpty) {
      writer.write("Period, Total Output, Efficiency\n")
    }
    source.close()

    (1 to days).foreach { _ =>
      alertSystem.checkAlerts()
      alertSystem.simulateDeviceDamage()
      weatherData.updateData()

      plantManager.getAllPlants.values.foreach { plant =>
        if (plant.getDeviceStatus == DeviceStatus.Damaged) {
          plant.shutdown()
          plant.updateOutput()
          dataCollector.collectData()
        }
        plant.updateOutput()
        dataCollector.collectData()
      }

      val operatingPlants = plantManager.getAllPlants.values.filter(plant => plant.getStatus == "Running" && plant.getDeviceStatus == DeviceStatus.Normal)

      val totalOutput = operatingPlants.map(_.getCurrentOutput).sum
      val maxOutput = operatingPlants.map(_.getMaxOutput).sum
      val efficiency = totalOutput / maxOutput

      writer.write(s"$currentDate, $totalOutput, $efficiency\n")

      currentDate = currentDate.plusDays(1)
    }

    writer.close()
  }
}


