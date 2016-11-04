package dao

import anorm._
import anorm.SqlParser._
import javax.inject.Inject
import play.api.db.Database
import javax.inject.Singleton
import java.util.Date
import models.Currency
import models.Rate
import java.text.SimpleDateFormat

@Singleton
class CurrencyDao @Inject() (db: Database) {

  private val currency: RowParser[Currency] = for {
    id <- int("CurrencyId")
    number <- str("NumCode")
    code <- str("CharCode")
    scale <- int("Scale")
    name <- str("CurrencyName")
  } yield Currency(id, number, code, scale, name)
  
  private val rate: RowParser[Rate] = for {
    date <- date("Date")
    rate <- double("ExchangeRate")
  } yield Rate(date.toString(), rate)

  def getCurrencies: List[Currency] = db.withTransaction { implicit conn =>
    SQL("SELECT * FROM CURRENCY").as(currency.*)
  }

  def getRates(currencyId: Int): List[Rate] = db.withTransaction { implicit conn =>
    SQL(
      """
          SELECT Date, ExchangeRate
          FROM CURRENCY, RATE_ON_DATE
          WHERE CURRENCY.CurrencyId = RATE_ON_DATE.CurrencyId
          AND CURRENCY.CurrencyId = {requestedId}
        """)
      .on("requestedId" -> currencyId)
      .as(rate.*)
  }

}