package controllers

import play.api.mvc._
import javax.inject.Inject
import dao.CurrencyDao
import json.Implicits._
import play.api.libs.json._

class Application @Inject() (currencyDao: CurrencyDao) extends Controller {
  
  def index = Action {
    Ok(views.html.index(currencyDao.getCurrencies))
  }
  
  def getCurrencies = Action {
    Ok(Json.toJson(currencyDao.getCurrencies))
  }

  def getRate(currencyId: Int) = Action {
    Ok(Json.toJson(currencyDao.getRates(currencyId)))
  }

}