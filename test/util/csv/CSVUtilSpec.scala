package util.csv

import java.time.{LocalDate, ZoneId, ZonedDateTime}

import org.scalatest.FunSpec

class CSVUtilSpec extends FunSpec {
  describe("CSVUtil") {
    describe("CSVString#toRecord") {
      it("文字列がそのまま表示されること") {
        assert("あ" == CSVString("あ").toRecord)
      }
    }

    describe("CSVOptionString#toRecord") {
      describe("Stringがあるとき") {
        it("文字列がそのまま表示されること") {
          assert("あ" == CSVOptionString(Some("あ")).toRecord)
        }
      }
      describe("Stringがないとき") {
        it("何も表示されること") {
          assert("" == CSVOptionString(None).toRecord)
        }
      }
    }

    describe("CSVStringValues#toRecord") {
      describe("Stringがないとき") {
        it("カンマ区切りで文字列が表示されること") {
          assert("" == CSVStringValues(Seq.empty).toRecord)
        }
      }

      describe("Stringが一つあるとき") {
        it("カンマ区切りで文字列が表示されること") {
          assert("あ" == CSVStringValues(Seq("あ")).toRecord)
        }
      }

      describe("Stringが複数あるとき") {
        it("カンマ区切りで文字列が表示されること") {
          assert("a,b,c" == CSVStringValues(Seq("a", "b", "c")).toRecord)
        }
      }
    }

    describe("CSVLong#toRecord") {
      describe("数値があるとき") {
        it("文字列として表示されること") {
          assert("1" == CSVLong(1).toRecord)
        }
      }

      describe("桁数の大きい数値があるとき") {
        it("文字列として表示されること") {
          assert(
            "1234567890112344567" == CSVLong(1234567890112344567L).toRecord
          )
        }
      }
    }

    describe("CSVDate#toRecord") {
      describe("日付があるとき") {
        it("YYYY/MM/DDの形式の文字列として、表示されること") {
          assert("2020/01/01" == CSVDate(LocalDate.of(2020, 1, 1)).toRecord)
        }
      }
    }

    describe("CSVDateTime#toRecord") {
      describe("時間があるとき") {
        it("UTC時間が日本時間に変換され、yyyy/MM/dd HH:mmの形式の文字列として、表示されること") {
          assert(
            "2020/01/01 09:00" == CSVDateTime(
              ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
            ).toRecord
          )
        }
      }
    }

  }

}
