abstract class EnergyPlant(val id: String, val maxOutput: Double) {
  protected var currentOutput: Double = 0.0
  private var status: String = "Shutdown"

  def getMaxOutput: Double = maxOutput

  def getCurrentOutput: Double = currentOutput

  def getStatus: String = status

  def setStatus(newStatus: String): Unit = {
    status = newStatus
  }
  def start(): Unit = {
    status = "Running"
    updateOutput()
  }

  def shutdown(): Unit = {
    status = "Shutdown"
    updateOutput()
  }

  def updateOutput(): Unit
}
