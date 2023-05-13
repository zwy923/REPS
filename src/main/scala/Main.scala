import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.FiniteDuration
import scala.io.StdIn.readLine
import scala.sys.exit
import scala.io.Source

object Main extends App {
  val plantManager = new PlantManager()
  private val plantScheduler = new PlantScheduler(plantManager)
  val weatherData = new WeatherData()
  val alertSystem = new AlertSystem(plantManager)
  val analyzer = new DataAnalyzer("historydata.csv")
  val dataCollector = new DataCollector(plantManager, "data.csv", weatherData)
  // read data
  dataCollector.loadData()

  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  private def printMenu(): Unit = {
    println("\n--- Renewable Energy Plant System ---")
    println("1. Add a new plant")
    println("2. Remove a plant")
    println("3. Start a plant")
    println("4. Shutdown a plant")
    println("6. Check total output")
    println("7. Update energy demand")
    println("8. Balance energy output")
    println("9. Simulate running")
    println("10. Analyze data")
    println("11. Show all facilities")
    println("0. Exit")
    println("--------------------------------------")
  }

  private var running = true

  while(running) {
    printMenu()

    val option = readLine("Select an option: ").trim.toInt

    option match {
      case 1 =>
        // Add a new plant
        val plantType = readLine("Enter the type of the plant (solar, wind, hydro): ").trim.toLowerCase
        val plantId = FacilityUtils.generateId(plantType, 5)
        val maxOutput = readLine("Enter the maximum output of the plant (in MW): ").trim.toDouble
        val plant = plantType match {
          case "solar" => new SolarPanel(plantId, maxOutput, weatherData)
          case "wind" => new WindTurbine(plantId, maxOutput, weatherData)
          case "hydro" => new HydropowerPlant(plantId, maxOutput, weatherData)
          case _ => throw new IllegalArgumentException("Invalid plant type.")
        }
        plantManager.addPlant(plant)
        dataCollector.collectData() // update information
        println(s"Added a new $plantType plant with ID: $plantId")
      case 2 =>
        // Remove a plant
        val plantId = readLine("Enter the ID of the plant to remove: ").trim
        plantManager.removePlant(plantId)
        dataCollector.collectData() // update information
        println(s"Removed the plant with ID: $plantId")
      case 3 =>
        // Start a plant
        val plantId = readLine("Enter the ID of the plant to start: ").trim
        plantManager.startPlant(plantId)
        dataCollector.collectData()
        println(s"Started the plant with ID: $plantId")
      case 4 =>
        // Shutdown a plant
        val plantId = readLine("Enter the ID of the plant to shutdown: ").trim
        plantManager.shutdownPlant(plantId)
        dataCollector.collectData()
        println(s"Shutdown the plant with ID: $plantId")
      case 6 =>
        // Check total output
        val totalOutput = plantManager.getTotalOutput
        println(s"Total output: $totalOutput MW")
      case 7 =>
        // Update energy demand
        val newDemand = readLine("Enter the new energy demand: ").trim.toDouble
        plantScheduler.updateEnergyDemand(newDemand)
        println(s"Updated the energy demand to: $newDemand MW")
      case 8 =>
        // Balance energy output
        plantScheduler.balanceEnergyOutput()
        println("Balanced the energy output.")
      case 9 =>
        // Simulate running
        val period = readLine("Enter the simulation period (day, week, month): ").trim.toLowerCase
        val days = period match {
          case "day" => 1
          case "week" => 7
          case "month" => 30
          case _ => throw new IllegalArgumentException("Invalid period. Please enter day, week, or month.")
        }

        val source = Source.fromFile("historydata.csv")
        val lines = source.getLines().toList
        source.close()
        val nonEmptyLines = lines.filter(_.trim.nonEmpty)
        val startDate = if (nonEmptyLines.isEmpty) {
          LocalDate.of(2023, 5, 1)
        } else {
          val lastDate = LocalDate.parse(nonEmptyLines.last.split(",")(0).trim)
          lastDate.plusDays(1)
        }

        val simulationRunner = new SimulationRunner(startDate, plantManager, alertSystem, weatherData)
        simulationRunner.runSimulation(days)
        println(s"Simulation of $period completed.")

      case 10 =>
        // Analyze data
        println("Please enter the start date (format: yyyy-mm-dd): ")
        val startDateStr = scala.io.StdIn.readLine()
        val startDate = LocalDate.parse(startDateStr, formatter)

        println("Please enter the end date (format: yyyy-mm-dd): ")
        val endDateStr = scala.io.StdIn.readLine()
        val endDate = LocalDate.parse(endDateStr, formatter)

        analyzer.analyze("day", startDate, endDate)
      case 11 =>
        // Show all facilities
        dataCollector.collectData()
        val allPlants = plantManager.getAllPlants
        if (allPlants.nonEmpty) {
          println("All Facilities:")
          allPlants.foreach { case (id, plant) =>
            println(s"ID: $id, Type: ${plant.getClass.getSimpleName}, Status: ${plant.getStatus}, Output: ${plant.getCurrentOutput} MW")
          }
        } else {
          println("No facilities found.")
        }
      case 0 =>
        println("Exiting the program...")

        running = false
        exit(0)

      case _ =>
        println("Invalid option. Please try again.")
    }
  }
}
