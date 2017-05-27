package services

import javax.inject.Inject

import com.typesafe.config.ConfigObject
import models.Post
import play.api.Configuration
import play.api.libs.json.{JsSuccess, JsValue, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

class PostsService @Inject()(ws: WSClient,
                             configuration: Configuration) {
  val postConfig: ConfigObject = configuration.underlying.getObject("api.posts")
  val host: String = postConfig.toConfig.getString("host")
  val endpoint: String = postConfig.toConfig.getString("endpoint")

  def getPostsForUser(id: Int)(implicit exec: ExecutionContext) : Future[Seq[Post]] = {
    val eventualWSResponse = ws.url(s"$host/$endpoint").get()
    eventualWSResponse.map { response =>
      import models.Post.PostReads
      val parse: JsValue = Json.parse(response.body)
      parse.validate[Seq[Post]] match {
        case users: JsSuccess[Seq[Post]] => users.value.filter(_.userId == id)
        case _ => throw new RuntimeException("Error while parsing Json")
      }
    }
  }

}
