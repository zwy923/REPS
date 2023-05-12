abstract class EnergyPlant(val id: String, val capacity: Double) {
  protected var currentOutput: Double = 0.0
  protected var status: String = "Shutdown"

  def getStatus: String = status

  def getCurrentOutput: Double = currentOutput

  def updateOutput(): Unit = {

  }

  def start(): Unit = {
    status = "Running"
    updateOutput()
  }

  def shutdown(): Unit = {
    status = "Shutdown"
  }
}