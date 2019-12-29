package controllers

import java.time.{LocalDate, ZoneId, ZonedDateTime}

import javax.inject._
import model.{FEMALE, MALE, Model}
import play.api.http.HttpEntity
import play.api.mvc._
import util.csv.CSVUtil

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

        val models = Seq(
          Model(
            1,
            "abab",
            MALE,
            Some(30),
            Seq("Elm"),
            LocalDate.of(1988, 1, 1),
            ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"))
          ),
          Model(
            2,
            "↑↓",
            MALE,
            Some(15),
            Seq("Scala", "TypeScript"),
            LocalDate.of(2000, 4, 1),
            ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
          ),
          Model(
            3,
            "BA",
            FEMALE,
            None,
            Seq(),
            LocalDate.of(1000, 10, 1),
            ZonedDateTime.of(100, 1, 1, 8, 0, 0, 0, ZoneId.of("UTC"))
          )
        )

        val csvResult =
          CSVUtil.createCSV(Model.csvHeader, models.map(_.toCSVRow))

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
