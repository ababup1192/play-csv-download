package util

import java.io.File
import java.nio.file.Files
import java.time.{LocalDate, ZoneId, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.util.Locale

import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import org.apache.commons.csv.CSVFormat

object CSVUtil {
  def createCSVStream(header: CSVHeader, rows: CSVRow*): CSVResult = {

    val tempFile = File.createTempFile("test", ".csv", null)
    val writer = Files.newBufferedWriter(tempFile.toPath)

    val csvPrinter =
      CSVFormat.EXCEL.withHeader(header.values: _*).print(writer)

    rows.foreach(row => csvPrinter.printRecord(row.values.map(_.toRecord): _*))
    csvPrinter.flush()

    CSVResult(
      FileIO.fromPath(tempFile.toPath),
      Some(Files.size(tempFile.toPath))
    )
  }

  implicit class RichString(val self: String) {
    def asCSV: CSVItem = CSVString(self)
  }

  implicit class RichOptionString(val self: Option[String]) {
    def asCSV: CSVItem = CSVOptionString(self)
  }

  implicit class RichStringValues(val self: Seq[String]) {
    def asCSV: CSVItem = CSVStringValues(self)
  }

  implicit class RichLong(val self: Long) {
    def asCSV: CSVItem = CSVLong(self)
  }

  implicit class RichDate(val self: LocalDate) {
    def asCSV: CSVItem = CSVDate(self)
  }

  implicit class RichDateTime(val self: ZonedDateTime) {
    def asCSV: CSVItem = CSVDateTime(self)
  }
}

case class CSVResult(source: Source[ByteString, _], contentLength: Option[Long])

case class CSVHeader(values: String*)

case class CSVRow(values: CSVItem*)

sealed trait CSVItem {
  def toRecord: String
}

case class CSVString(value: String) extends CSVItem {
  override def toRecord: String = value
}
case class CSVOptionString(value: Option[String]) extends CSVItem {
  override def toRecord: String = value.getOrElse("")
}
case class CSVStringValues(values: Seq[String]) extends CSVItem {
  override def toRecord: String = values.mkString(",")
}
case class CSVLong(value: Long) extends CSVItem {
  override def toRecord: String = value.toString
}
case class CSVDate(value: LocalDate) extends CSVItem {
  override def toRecord: String = value.toString.replaceAll("-", "/")
}
case class CSVDateTime(value: ZonedDateTime) extends CSVItem {
  override def toRecord: String = formatJapanTime(value)

  private def formatJapanTime(dateTime: ZonedDateTime): String =
    DateTimeFormatter
      .ofPattern("yyyy/MM/dd HH:mm", Locale.JAPANESE)
      .format(dateTime.withZoneSameInstant(ZoneId.of("Asia/Tokyo")))
}
