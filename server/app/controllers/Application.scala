package controllers

import play.api.mvc._

object Application extends Controller {

  def version = Action {
    Ok("0.0.1")
  }

}