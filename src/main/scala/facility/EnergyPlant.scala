abstract class EnergyPlant(val id: String, val maxOutput: Double) {
  protected var currentOutput: Double = 0.0
  private var deviceStatus: DeviceStatus = DeviceStatus.Normal
  private var status: String = "Shutdown"

  def getMaxOutput: Double = maxOutput
  def getCurrentOutput: Double = currentOutput
  def getDeviceStatus: DeviceStatus = deviceStatus
  def getStatus: String = status
  def setStatus(newStatus: String): Unit = {
    status = newStatus
  }

  def setDeviceStatus(status: DeviceStatus): Unit = {
    deviceStatus = status
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

sealed trait DeviceStatus
object DeviceStatus {
  case object Normal extends DeviceStatus
  case object Damaged extends DeviceStatus
}
