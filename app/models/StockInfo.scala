package models

import play.api.libs.json.Json

/**
  * Created by lisa on 4/18/16.
  */
case class StockInfo(symbol : String, name : String, price : BigDecimal, bid : BigDecimal, ask: BigDecimal, change : BigDecimal,
                     peg : BigDecimal, dividend : BigDecimal)

object StockInfo{
  implicit val stockInfoFormat = Json.format[StockInfo]
}

case class StockInfoList(infoList : List[StockInfo])

object StockInfoList{
  implicit val stockInfoListFormat = Json.format[StockInfoList]
}