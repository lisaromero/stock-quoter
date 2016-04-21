package services


import scala.collection.JavaConversions._
import _root_.yahoofinance.YahooFinance
import models.StockInfo


/**
  * Created by lisa on 4/20/16.
  */
object StockFinder {

  def findStock(symbol : String): StockInfo = {
    val stock = YahooFinance.get(symbol)
    val quote = stock.getQuote
    new StockInfo(symbol, stock.getName, quote.getPrice, quote.getBid , quote.getAsk, quote.getChange, stock.getStats.getPeg,
      stock.getDividend.getAnnualYieldPercent)
  }

  def findStocks(symbolList : Array[String]) : List[StockInfo] = {

    val  stockMap = YahooFinance.get(symbolList)

    stockMap.toMap
    val stockInfoMap = for{
      (k, v) <- stockMap
    }yield (k, new StockInfo(k, v.getName, v.getQuote.getPrice, v.getQuote.getBid,  v.getQuote.getAsk, v.getQuote.getChange, v.getStats.getPeg,
      v.getDividend.getAnnualYieldPercent))

    stockInfoMap.toMap.values.toList
  }

}
