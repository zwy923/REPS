abstract class EnergyPlant(val id: String) {
  protected var currentOutput: Double = 0.0
  private var status: String = "Shutdown"

  def getCurrentOutput: Double = currentOutput

  def getStatus: String = status

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
