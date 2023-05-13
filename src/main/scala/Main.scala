import scala.io.StdIn.readLine

object Main extends App {
  val plantManager = new PlantManager()
  private val plantScheduler = new PlantScheduler(plantManager)
  val weatherData = new WeatherData()
  private val dataCollector = new DataCollector(plantManager, "data.csv", weatherData)
  // read data
  dataCollector.loadData()
  private def printMenu(): Unit = {
    println("\n--- Renewable Energy Plant System ---")
    println("1. Add a new plant")
    println("2. Remove a plant")
    println("3. Start a plant")
    println("4. Shutdown a plant")
    println("6. Check total output")
    println("7. Update energy demand")
    println("8. Balance energy output")
    println("9. Collect data")
    println("10. Analyze data")
    println("11. Show all facilities")
    println("0. Exit")
    println("--------------------------------------")
  }

  var running = true

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
        // Collect data
        dataCollector.collectData()
        println("Collected the data.")
      case 10 =>
        // Analyze data
        val dataAnalyzer = new DataAnalyzer("data.csv")
        println(s"Mean: ${dataAnalyzer.mean}")
        println(s"Median: ${dataAnalyzer.median}")
        println(s"Mode: ${dataAnalyzer.mode}")
        println(s"Range: ${dataAnalyzer.range}")
        println(s"Midrange: ${dataAnalyzer.midRange}")
      case 11 =>
        // Show all facilities
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
      case _ =>
        println("Invalid option. Please try again.")
    }
  }
}
