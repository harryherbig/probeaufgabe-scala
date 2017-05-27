package models

import play.api.libs.json.Json

/**
  * Created by harry on 27.05.2017.
  */
case class Post(userId: Long, id: Long, title: String, body: String)

object Post {
  implicit lazy val PostReads = Json.reads[Post]
}