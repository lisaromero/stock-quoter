package actors

import akka.actor.{Props, ActorRef, Actor}
import play.Logger
import services.StockFinder

import scala.collection.mutable.ListBuffer

/**
  * Created by lisa on 4/20/16.
  */
class SessionActor extends Actor{

  var startList = ListBuffer("AAPL", "F", "GOOG", "DIS")
  var sessionList = startList

  object SessionActor {
    def props(out: ActorRef) = Props(new SessionActor())
  }


  def receive = {
    case RefreshList =>
      updateList()
    case Remove(sym) =>
      Logger.debug("removing symbol .... " + sym.get)
      if(sym.isDefined) {
        val symIndex = sessionList.indexOf(sym.get)
        sessionList.remove(symIndex)
      }
      else sessionList = startList
      updateList()

    case Add(sym) =>
      sessionList += sym
      updateList()

  }

  def updateList() ={
    val stockArray = (sessionList map(_.toString)).toArray
    val refreshedList = StockFinder.findStocks(stockArray)
    sender ! refreshedList
  }

}

case object RefreshList
case class Remove(symbol : Option[String])
case class Add(Symbol : String)