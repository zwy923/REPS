import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    val plantManager = new PlantManager
    val plantScheduler = new PlantScheduler(plantManager)
    val reportSystem = new ReportSystem(plantManager)

    var running = true

    while (running) {
      println(
        """
          |1. Add a plant
          |2. Remove a plant
          |3. Get a plant
          |4. Shutdown plant
          |5. Start plant
          |6. Update energy demand
          |7. Balance energy output
          |8. Generate status report
          |9. Generate output report
          |0. Exit
          |Choose an option:
        """.stripMargin)
      val option = StdIn.readInt()

      option match {
        case 1 =>
          println(
            """
              |Choose a plant type:
              |1. Solar Plant
              |2. Wind Plant
              |3. Hydro Plant
            """.stripMargin)
          val plantType = StdIn.readInt()
          println("Enter plant ID:")
          val id = StdIn.readLine()
          println("Enter plant capacity:")
          val capacity = StdIn.readDouble()

          plantType match {
            case 1 =>
              println("Enter the number of solar panels:")
              val panels = StdIn.readInt()
              val solarPlant = new SolarPlant(id, capacity, panels)
              plantManager.addPlant(solarPlant)
            case 2 =>
              println("Enter the number of turbines:")
              val turbines = StdIn.readInt()
              val windPlant = new WindPlant(id, capacity, turbines)
              plantManager.addPlant(windPlant)
            case 3 =>
              println("Enter the number of turbines:")
              val turbines = StdIn.readInt()
              val hydroPlant = new HydroPlant(id, capacity, turbines)
              plantManager.addPlant(hydroPlant)
            case _ =>
              println("Invalid plant type. Please choose a number between 1 and 3.")
          }
        case 2 =>
          println("Enter plant ID:")
          val id = StdIn.readLine()
          plantManager.removePlant(id)
        case 3 =>
          println("Enter plant ID:")
          val id = StdIn.readLine()
          val plant = plantManager.getPlant(id)
          plant match {
            case Some(p) => println(s"Plant: ${p.id}, Status: ${p.getStatus}, Current Output: ${p.getCurrentOutput}")
            case None => println("No plant found with the given ID.")
          }
        case 4 =>
          println("Enter plant ID:")
          val id = StdIn.readLine()
          plantManager.shutdownPlant(id)
          plantManager.updatePlantOutput(id)
        case 5 =>
          println("Enter plant ID:")
          val id = StdIn.readLine()
          plantManager.startPlant(id)
          plantManager.updatePlantOutput(id)
        case 6 =>
          println("Enter new energy demand:")
          val newDemand = StdIn.readDouble()
          plantScheduler.updateEnergyDemand(newDemand)
        case 7 =>
          plantScheduler.balanceEnergyOutput()
        case 8 =>
          reportSystem.generateStatusReport()
        case 9 =>
          reportSystem.generateOutputReport()
        case 0 =>
          running = false
        case _ =>
          println("Invalid option. Please choose a number between 1 and 11.")
      }
    }
  }
}
