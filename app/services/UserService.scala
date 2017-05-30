package services

import javax.inject.Inject

import com.typesafe.config.ConfigObject
import models.User
import org.apache.commons.lang3.time.StopWatch
import play.api.{Configuration, Logger}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(ws: WSClient,
                            configuration: Configuration) {
  val userConfig: ConfigObject = configuration.underlying.getObject("api.user")
  val host: String = userConfig.toConfig.getString("host")
  val endpoint: String = userConfig.toConfig.getString("endpoint")

  val log: Logger = Logger(this.getClass)
  val stopWatch = new StopWatch()

  def getUser(id: Int)(implicit exec: ExecutionContext): Future[User] = {
    stopWatch.reset()
    stopWatch.start()
    log.debug(s"[User] started")
    ws.url(s"$host/$endpoint/$id")
      .execute()
      .map { response =>
        import models.Reads._
        Json.parse(response.body)
          .validate[User] match {
          case user: JsSuccess[User] =>
            stopWatch.stop()
            log.debug(s"[User] took: ${stopWatch.getTime}")
            user.value
          case error: JsError => throw new RuntimeException(
            s"""JsError '$error'
               |while parsing Json:
               |${response.body}""".stripMargin)

        }
      }
  }

}
