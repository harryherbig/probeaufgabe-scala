package controllers

import javax.inject._

import models.{Post, User}
import play.api.Logger
import play.api.mvc._
import services.{PostsService, UserService}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(userService: UserService, postsService: PostsService)(implicit exec: ExecutionContext) extends Controller {
val log = Logger(this.getClass)

  def getUser(id: String): Action[AnyContent] = Action.async {
    val eventualPosts: Future[Seq[Post]] = postsService
      .getPostsForUser(id.toInt)
      .recover {
        case err: Exception =>
          log.error("Post Request failed, returning empty List", err)
          Nil
      }
    val eventualUser: Future[User] = userService
      .getUser(id.toInt)
      .recover {
      case err: Exception =>
        log.error("User Request failed, returning empty User", err)
        User.empty
    }
    for {
      posts <- eventualPosts
      user <- eventualUser
    } yield {
      Ok(views.html.users(user, posts))
    }
  }

  def userMenu(): Action[AnyContent] = Action {
    Ok(views.html.menu())
  }
}
