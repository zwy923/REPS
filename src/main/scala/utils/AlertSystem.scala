import Main.dataCollector

import scala.util.Random

class AlertSystem(plantManager: PlantManager) {
  private val random = new Random()

  def checkAlerts(): Unit = {
    plantManager.getAllPlants.foreach { case (id, plant) =>

      if (plant.getStatus == "running" && plant.getCurrentOutput < plant.maxOutput / 10) {
        println(s"Low output warning: $id")
      }
    }
  }

  def simulateDeviceDamage(): Unit = {
    plantManager.getAllPlants.foreach { case (id, plant) =>
      if (random.nextDouble() < 0.01) { // 1% probability of device damage
        plant.setDeviceStatus(DeviceStatus.Damaged)
        dataCollector.collectData()
        println(s"Device damaged: $id")
      }
    }
  }
}
