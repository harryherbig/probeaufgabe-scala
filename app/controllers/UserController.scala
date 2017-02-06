package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import services.UserService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(userService: UserService)(implicit exec: ExecutionContext) extends Controller {

  def getUser(id: String) = Action.async {
    println(s"got id: $id")
    try {
      import models.Formats._
      userService.getUser(id.toInt)(exec).map(user => Ok(Json.toJson(user)))
    }
    catch {
      case e: NumberFormatException => Future {
        BadRequest(s"$id is not a valid ID")
      }
      case _ => Future {
        NotFound(s"At the end of the day something went wrong")
      }
    }

  }

}
