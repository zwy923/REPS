import java.io.{File, PrintWriter}

class DataCollector(plantManager: PlantManager, fileName: String) {
  def collectData(): Unit = {
    val writer = new PrintWriter(new File(fileName))

    writer.write("Plant ID, Current Output\n")

    for ((id, plant) <- plantManager.getAllPlants) {
      writer.write(s"$id, ${plant.getCurrentOutput}\n")
    }

    writer.close()
  }
}
