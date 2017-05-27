package controllers

import javax.inject._

import play.api.mvc._
import services.{PostsService, UserService}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(userService: UserService, postsService: PostsService)(implicit exec: ExecutionContext) extends Controller {

  def getUser(id: String) = Action.async {
    println(s"got id: $id")
    try {
      for {
        posts <- postsService.getPostsForUser(id.toInt)
        user <- userService.getUser(id.toInt)
      } yield {
        Ok(views.html.users(user, posts))
      }
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

  def userMenu() = Action {
    Ok(views.html.menu())
  }

}
