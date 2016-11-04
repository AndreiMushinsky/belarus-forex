package main

import scala.scalajs.js.JSApp
import org.scalajs.jquery.jQuery
import client.CurrencyService
import client.CurrencyService
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.jquery.JQueryEventObject
import view.Drawer

object JSMain extends JSApp {
  
  lazy val service = new CurrencyService
  
  lazy val drawer = new Drawer
  
  lazy val currencyList = jQuery("#currency-list")
  
  def main(): Unit = {
    service.currencies.foreach { _.map { currency =>
        jQuery("<img>")
          .attr("src", s"/assets/images/signs/${currency.id}.jpg")
          .attr("title", currency.name)
          .attr("height", 80)
          .attr("width",  80)
          .on("click", () => service.ratesFor(currency).foreach { rates => 
            drawer.drawChart(currency, rates)
            drawer.drawTable(currency, rates)
          })
          .appendTo(currencyList)
      }   
    }
  }
}
