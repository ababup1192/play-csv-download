package util.csv

import java.time.{LocalDate, ZonedDateTime}

package object syntax {
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
