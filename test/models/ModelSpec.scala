package models

import java.time.{LocalDate, ZoneId, ZonedDateTime}

import model.{MALE, Model}
import org.scalatest.FunSpec
import util.csv.{
  CSVDate,
  CSVDateTime,
  CSVLong,
  CSVOptionString,
  CSVRow,
  CSVString,
  CSVStringValues
}

class ModelSpec extends FunSpec {

  describe("#toCSVRow") {
    it("Modelが正しくCSVRowに変換できていること") {
      val model = Model(
        1,
        "abab",
        MALE,
        Some(30),
        Seq("Elm"),
        LocalDate.of(1988, 1, 1),
        ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"))
      )

      val csvRow = CSVRow(
        CSVLong(1),
        CSVString("abab"),
        CSVString("男"),
        CSVOptionString(Some("30")),
        CSVStringValues(Seq("Elm")),
        CSVDate(LocalDate.of(1988, 1, 1)),
        CSVDateTime(ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC")))
      )

      assert(model.toCSVRow == csvRow)
      // Rowのサイズとヘッダの長さが一致していること
      assert(csvRow.size == Model.csvHeader.values.length)
    }
  }

}
