package main

import io.vertx.core.AsyncResult
import io.vertx.scala.core.{DeploymentOptions, Vertx}
import io.vertx.scala.core.eventbus.Message
import io.vertx.scala.core.http.HttpClientOptions

object WCliente{
  def create = {
    Main.vertx.createHttpClient()
  }
}

object Main {
  val vertx = Vertx.vertx()

  val httpOptions = HttpClientOptions().setKeepAlive(true).setPipelining(true).setTcpKeepAlive(true).setMaxPoolSize(1)
  val client = vertx.createHttpClient(httpOptions)
  val options = DeploymentOptions().setInstances(2)
  vertx.deployVerticle(new WClient(client))

  def main(args: Array[String]): Unit = {
    Range(1,6).foreach(i => {
      vertx.eventBus().send("anAddress", i.toString, (fut: AsyncResult[Message[String]]) =>{
        if (fut.succeeded) {
          println(s"Received reply: ${fut.result.body}")
        }
      })
    })
  }
}
