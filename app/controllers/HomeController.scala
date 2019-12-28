package controllers

import java.time.{LocalDate, ZoneId, ZonedDateTime}

import javax.inject._
import play.api.http.HttpEntity
import play.api.mvc._
import util.{CSVHeader, CSVRow, CSVUtil}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      {

        import CSVUtil._
        val csvResult = CSVUtil.createCSV(
          CSVHeader("文字列", "Option[文字列]", "CSV in CSV", "数値", "日付", "時間"),
          CSVRow(
            "文字列".asCSV,
            Some("オプション文字列").asCSV,
            Seq("a", "b", "c").asCSV,
            2019.asCSV,
            LocalDate.of(2019, 12, 31).asCSV,
            ZonedDateTime.now(ZoneId.of("UTC")).asCSV
          ),
          CSVRow(
            "文字列2".asCSV,
            None.asCSV,
            Seq("1", "2", "3").asCSV,
            2020.asCSV,
            LocalDate.of(2020, 1, 1).asCSV,
            ZonedDateTime.now(ZoneId.of("UTC")).asCSV
          ),
        )

        Result(
          header = ResponseHeader(
            status = 200,
            headers = Results
              .contentDispositionHeader(inline = false, name = Some("hoge.csv"))
          ),
          body = HttpEntity
            .Streamed(
              csvResult.source,
              csvResult.contentLength,
              Some("text/csv")
            )
        )
      }
  }
}
