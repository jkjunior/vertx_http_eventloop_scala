package main

import io.vertx.core.http.HttpMethod
import io.vertx.core.net.ProxyType
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.Message
import io.vertx.scala.core.http.{HttpClient, HttpClientOptions, HttpClientResponse, RequestOptions}
import io.vertx.scala.core.net.ProxyOptions

//object WClient{
//  def create = {
//    val options = HttpClientOptions()
//      .setProxyOptions(ProxyOptions()
//        .setType(ProxyType.HTTP)
//        .setHost("localhost")
//        .setPort(3128)
//      )
//    Vertx.vertx().createHttpClient(options)
//  }
//}

class WClient(client: HttpClient) extends ScalaVerticle {

  // Convenience method so you can run it in your IDE
  override def start() {
    vertx.eventBus().consumer("anAddress").handler((message: Message[String]) => {
      if(message.body().toString == "2"){
        client.request(HttpMethod.GET, RequestOptions.apply.setHost("172.16.165.141").setPort(8000).setURI("/segundo"), (response: HttpClientResponse) => {
//          println(s"Received response with status code ${response.statusCode()}; ${response.handler(buffer => print(s"Received a part of the response body: $buffer"))}")
          message.reply(message.body().toString)
        }).setTimeout(10000).end()
      } else {
        client.request(HttpMethod.GET, RequestOptions.apply.setHost("172.16.165.141").setPort(20550).setURI("/teste/1"), (response: HttpClientResponse) => {
          //        println(s"Received response with status code ${response.statusCode()}; ${response.handler(buffer => print(s"Received a part of the response body: $buffer"))}")
          message.reply(message.body().toString)
        }).putHeader("Accept", "application/protobuf").putHeader("Accept-Encoding", "gzip").end()
      }



    })

  }
}
