package client

import models._
import upickle.default._
import org.scalajs.dom.ext.Ajax
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CurrencyService {
  
  lazy val currencies = 
    Ajax.get("currencies").map { xhr => read[Seq[Currency]](xhr.responseText) }
 
  def ratesFor(currency: Currency) =
    Ajax.get("rate/" + currency.id).map { xhr => read[Seq[Rate]](xhr.responseText) }
  
}