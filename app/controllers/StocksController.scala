package controllers

import actors.{Add, Remove, RefreshList, SessionActor}
import models.{StockInfoList, StockInfo}
import play.Logger
import play.api.Play.current
import akka.actor.{Props, Actor, ActorRef}
import play.api.libs.json._
import play.api.mvc._
import play.libs.{ Akka}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc.WebSocket.FrameFormatter


import scala.concurrent.duration.Duration

/**
  * Created by lisa on 4/20/16.
  */
object StocksController extends Controller{


  implicit val inEventFormat = Json.format[StockInfoList]
  implicit val inEventFrameFormatter = FrameFormatter.jsonFrame[StockInfoList]

  def socket = WebSocket.acceptWithActor[JsValue, StockInfoList] {
    (request: RequestHeader) =>
    (out: ActorRef) =>
    MyWebSocketActor.props(out)
  }

  object MyWebSocketActor {
    def props(out: ActorRef) = Props(new MyWebSocketActor(out))
  }

  class MyWebSocketActor(out: ActorRef) extends Actor {

    lazy val stocksActor: ActorRef = Akka.system.actorOf(Props(classOf[SessionActor]))

    val stockTick = context.system.scheduler.schedule(Duration.Zero, 5 seconds, stocksActor , RefreshList)


    def receive = {

      case request : JsValue =>
        val action = (request \ "action").as[String]
        val symbol = (request \ "symbol").asOpt[String]
        Logger.debug("Got new " + action + "request for sym: " + symbol.get)
        if(action.equals("Remove"))
          stocksActor ! Remove(symbol)
        else stocksActor ! Add(symbol.get)

      case refreshedList : List[StockInfo] =>
        out ! new StockInfoList(refreshedList)

    }
  }

}

//actions : Add/Remove todo.. maybe make enum
case class StockRequest(action: String, symbol : String)

