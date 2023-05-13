import scala.io.Source

class DataAnalyzer(fileName: String) {

  // Read data from file and return it as a list of doubles
  private def getData: List[Double] = {
    val source = Source.fromFile(fileName)
    val data = source.getLines().drop(1).map(line => line.split(",")(1).trim.toDouble).toList
    source.close()
    data
  }

  def mean: Double = {
    val data = getData
    data.sum / data.size
  }

  def median: Double = {
    val sortedData = getData.sorted
    val mid = sortedData.size / 2
    // If the number of data points is odd, return the middle one, otherwise return the average of the two middle ones
    if (sortedData.size % 2 == 1) sortedData(mid) else (sortedData(mid - 1) + sortedData(mid)) / 2.0
  }

  def mode: Double = {
    val data = getData
    data.groupBy(identity).mapValues(_.size).maxBy(_._2)._1
  }

  def range: Double = {
    val data = getData
    data.max - data.min
  }

  def midRange: Double = {
    val data = getData
    (data.max + data.min) / 2
  }
}