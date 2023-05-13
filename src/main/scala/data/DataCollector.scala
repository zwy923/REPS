import java.io.{File, PrintWriter}
import scala.io.Source
import java.io.{File, FileWriter, BufferedWriter}

class DataCollector(plantManager: PlantManager, fileName: String, weatherData: WeatherData) {

  // Load data from the data.csv file
  private def loadDataFile(): Unit = {
    val source = Source.fromFile(fileName)
    val lines = source.getLines().drop(1) // Skip the header line
    lines.foreach { line =>
      val fields = line.split(",").map(_.trim)
      if (fields.length >= 4) {
        val plantId = fields(0)
        val facilityType = fields(1)
        val status = fields(2)
        val plant = facilityType match {
          case "Solar Panel" =>
            val solarPanel = new SolarPanel(plantId, 0.0, weatherData) // Set initial current output as 0.0
            solarPanel.updateOutput() // Update current output based on weather data
            solarPanel
          case "Wind Turbine" =>
            val windTurbine = new WindTurbine(plantId, 0.0, weatherData) // Set initial current output as 0.0
            windTurbine.updateOutput() // Update current output based on weather data
            windTurbine
          case "Hydropower Plant" =>
            val hydropowerPlant = new HydropowerPlant(plantId, 0.0, weatherData) // Set initial current output as 0.0
            hydropowerPlant.updateOutput() // Update current output based on weather data
            hydropowerPlant
          case _ => throw new IllegalArgumentException(s"Invalid facility type: $facilityType")
        }
        plant.setStatus(status)
        plantManager.addPlant(plant)
      }
    }
    source.close()
  }
  private def updateDataFile(): Unit = {
    val file = new File(fileName)
    val writer = new BufferedWriter(new FileWriter(file))

    writer.write("Plant ID, Facility Type, Status, Current Output")
    writer.newLine()

    val allPlants = plantManager.getAllPlants
    allPlants.foreach { case (id, plant) =>
      val facilityType = plant match {
        case _: SolarPanel => "Solar Panel"
        case _: WindTurbine => "Wind Turbine"
        case _: HydropowerPlant => "Hydropower Plant"
      }
      val status = plant.getStatus
      val currentOutput = plant.getCurrentOutput
      writer.write(s"$id, $facilityType, $status, $currentOutput")
      writer.newLine()
    }

    writer.close()
  }

  def collectData(): Unit = {
    plantManager.getAllPlants.foreach { case (_, plant) =>
      plant.updateOutput()
    }
    updateDataFile()
  }

  def loadData(): Unit = {
    loadDataFile()
  }
}

