package view

import models._
import scalajs.js
import org.scalajs.jquery.jQuery
import com.highcharts.HighchartsUtils._
import com.highcharts.HighchartsAliases._
import com.highcharts.config._
import scalajs.js.JSConverters._

class Drawer {
  
  def drawChart(currency: Currency, rates: Seq[Rate]): Unit = {
    jQuery("#chart").highcharts(new HighchartsConfig {
      override val chart: Cfg[Chart] = Chart(`type` = "line")
      override val title: Cfg[Title] = Title(text = currency.name)
      override val xAxis: Cfg[XAxis] = XAxis(categories = rates.map { _.date }.toJSArray)
      override val yAxis: Cfg[YAxis] = YAxis(title = YAxisTitle(text = "Rate"))
      override val series: SeriesCfg = js.Array[AnySeries](
        SeriesLine(name = s"BYR/${currency.code}", data = rates.map { _.rate * currency.scale }.toJSArray))
    })
  }
  
  def drawTable(currency: Currency, rates: Seq[Rate]): Unit = {
    val table = jQuery("<table>").append(
      jQuery("<thead>").append(
        jQuery("<tr>").append(
          jQuery("<th>").html("Date"),
          jQuery("<th>").html(s"BYR/${currency.code}")
        )
      )
    )
    rates.map { rate =>
      jQuery("<tr>").append(
        jQuery("<td>").html(rate.date),
        jQuery("<td>").html(rate.rate * currency.scale toString)
      )
    }.foreach { _.appendTo(table) }
    jQuery("#table").empty().append(table)
  }
}