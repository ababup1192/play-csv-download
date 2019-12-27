package controllers

import java.io.{File, FileOutputStream}
import java.nio.file.Files

import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import javax.inject._
import play.api.http.HttpEntity
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    {
      val tempFile = File.createTempFile("test", ".csv", null)
      val fileOutputStream = new FileOutputStream(tempFile)
      fileOutputStream.write(1234.toByte)
      val contentLength = Some(Files.size(tempFile.toPath))
      val source: Source[ByteString, _] = FileIO.fromPath(tempFile.toPath)

      Result(
        header = ResponseHeader(
          status = 200,
          headers = Results
            .contentDispositionHeader(inline = false, name = Some("hoge.csv"))
        ),
        body = HttpEntity.Streamed(source, contentLength, Some("text/csv"))
      )
    }
  }
}
