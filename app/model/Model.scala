package model

import java.time.{LocalDate, ZonedDateTime}

import util.csv.{CSVHeader, CSVRow}
case class Model(id: Long,
                 name: String,
                 gender: Gender,
                 ageOpt: Option[Int],
                 favoriteLanguageSeq: Seq[String],
                 birthDate: LocalDate,
                 registerDateTime: ZonedDateTime) {

  import util.csv.syntax._
  def toCSVRow: CSVRow = CSVRow(
    id.asCSV,
    name.asCSV,
    gender.value.asCSV,
    ageOpt.map(_.toString).asCSV,
    favoriteLanguageSeq.asCSV,
    birthDate.asCSV,
    registerDateTime.asCSV
  )
}

object Model {
  val csvHeader: CSVHeader =
    CSVHeader("ID", "名前", "性別", "年齢", "得意言語", "誕生日", "登録時間")
}

abstract class Gender(val code: String, val value: String)

case object FEMALE extends Gender("FEMALE", "女")

case object MALE extends Gender("MALE", "男")
