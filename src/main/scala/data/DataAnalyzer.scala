import scala.io.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DataAnalyzer(fileName: String) {
  private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  private def getData(period: String, startDate: LocalDate, endDate: LocalDate): List[Double] = {
    val source = Source.fromFile(fileName)
    val data = source.getLines().drop(1).map { line =>
      val fields = line.split(",").map(_.trim)
      val date = LocalDate.parse(fields(0), dateFormat)
      val output = fields(1).toDouble

      (date, output)
    }.toList.filter { case (date, _) =>
      date.isAfter(startDate) && date.isBefore(endDate.plusDays(1)) // plusDays(1) to include the endDate
    }.map(_._2)

    source.close()
    data
  }

  def analyze(period: String, startDate: LocalDate, endDate: LocalDate): Unit = {
    val data = getData(period, startDate, endDate)

    if (data.nonEmpty) {
      val mean = data.sum / data.size
      val sortedData = data.sorted
      val median = if (data.size % 2 == 1) sortedData(data.size / 2) else (sortedData(data.size / 2 - 1) + sortedData(data.size / 2)) / 2.0
      val mode = data.groupBy(identity).mapValues(_.size).maxBy(_._2)._1
      val range = data.max - data.min
      val midRange = (data.max + data.min) / 2

      println(s"Analysis from $startDate to $endDate:")
      println(f"Average daily output(Mean): $mean%.2f")
      println(f"Median daily output: $median%.2f")
      println(f"The most common daily output(Mode): $mode%.2f")
      println(f"The range of daily output: $range%.2f")
      println(f"The midrange of daily output: $midRange%.2f")
    } else {
      println(s"No data found from $startDate to $endDate.")
    }
  }
}

