package services

import javax.inject.Inject

import com.typesafe.config.ConfigObject
import models.User
import play.api.Configuration
import play.api.libs.json.{JsSuccess, JsValue, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(ws: WSClient,
                            configuration: Configuration) {
  val userConfig: ConfigObject = configuration.underlying.getObject("api.user")
  val host: String = userConfig.toConfig.getString("host")
  val endpoint: String = userConfig.toConfig.getString("endpoint")

  def getUser(id: Int)(implicit exec: ExecutionContext) : Future[User] = {
    val eventualWSResponse = ws.url(s"$host/$endpoint/$id").get()
    eventualWSResponse.map { response =>
      import models.Reads._
      val parse: JsValue = Json.parse(response.body)
      println(parse.toString())
      parse.validate[User] match {
        case user: JsSuccess[User] => user.value
        case _ => throw new RuntimeException("Error while parsing Json")
      }
    }
  }

}
