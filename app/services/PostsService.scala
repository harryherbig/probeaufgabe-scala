package services

import javax.inject.Inject

import com.typesafe.config.ConfigObject
import models.Post
import org.apache.commons.lang3.time.StopWatch
import play.api.{Configuration, Logger}
import play.api.libs.json.{JsSuccess, JsValue, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

class PostsService @Inject()(ws: WSClient,
                             configuration: Configuration) {
  val postConfig: ConfigObject = configuration.underlying.getObject("api.posts")
  val host: String = postConfig.toConfig.getString("host")
  val endpoint: String = postConfig.toConfig.getString("endpoint")

  val log: Logger = Logger(this.getClass)
  val stopWatch = new StopWatch()

  def getPostsForUser(id: Int)(implicit exec: ExecutionContext) : Future[Seq[Post]] = {
    stopWatch.reset()
    stopWatch.start()
    log.debug(s"[Posts] started")
    val eventualWSResponse = ws.url(s"$host/$endpoint").get()
    eventualWSResponse.map { response =>
      import models.Post.PostReads
      val parse: JsValue = Json.parse(response.body)
      parse.validate[Seq[Post]] match {
        case users: JsSuccess[Seq[Post]] =>
          stopWatch.stop()
          log.debug(s"[Posts] took: ${stopWatch.getTime}")
          users.value.filter(_.userId == id)
        case _ => throw new RuntimeException("Error while parsing Json")
      }
    }
  }

}
