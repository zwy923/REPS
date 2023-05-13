import java.util.{Timer, TimerTask}
import scala.util.Random

class WeatherData {
  private var sunlightIntensity: Double = Random.nextDouble
  private var windSpeed: Double = Random.nextDouble
  private var waterFlow: Double = Random.nextDouble

  private val timer: Timer = new Timer()

  // TimerTask to update weather data every minute
  private val task: TimerTask = new TimerTask {
    def run(): Unit = {
      sunlightIntensity = Random.nextDouble
      windSpeed = Random.nextDouble
      waterFlow = Random.nextDouble
    }
  }

  // Schedule the task to run starting now and then every minute...
  timer.schedule(task, 0, 60 * 1000)

  def getSunlightIntensity: Double = sunlightIntensity
  def getWindSpeed: Double = windSpeed
  def getWaterFlow: Double = waterFlow

  def updateData(): Unit = {
    // Simulate random weather data
    waterFlow = Random.nextDouble()
    sunlightIntensity = Random.nextDouble()
    windSpeed = Random.nextDouble()
  }
}
