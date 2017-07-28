package com.inkenkun.x1.serialization.benchmark.avro

import java.io.IOException
import java.net.InetSocketAddress

import org.apache.avro.Protocol
import org.apache.avro.generic.GenericData.Record
import org.apache.avro.ipc.NettyServer
import org.apache.avro.ipc.generic.GenericResponder

object Server {

  def main(args: Array[String]): Unit = {

    lazy val definition =
      """{
        |  "protocol": "AdRequestReceiver",
        |  "types": [
        |    {
        |      "type": "record",
        |      "name": "AdRequest",
        |      "fields": [
        |        {"name": "rowID"        , "type": "long"},
        |        {"name": "clicks"       , "type": "int"},
        |        {"name": "impression"   , "type": "int"},
        |        {"name": "displayURL"   , "type": "string"},
        |        {"name": "adID"         , "type": "int"},
        |        {"name": "advertiserID" , "type": "int"},
        |        {"name": "depth"        , "type": "int"},
        |        {"name": "position"     , "type": "int"},
        |        {"name": "queryID"      , "type": "int"},
        |        {"name": "keywordID"    , "type": "int"},
        |        {"name": "titleID"      , "type": "int"},
        |        {"name": "descriptionID", "type": "int"},
        |        {"name": "userID"       , "type": "int"}
        |      ]
        |    }
        |  ],
        |  "messages": {
        |    "getRowId": {
        |      "request":  [{"name": "adreq", "type": "AdRequest"}],
        |      "response": "long"
        |    }
        |  }
        |}
      """.stripMargin

    val protocol = Protocol.parse(definition)
    val responder = new GenericResponder(protocol) {
      override def respond( message: Protocol#Message, request: Any ): Object = {
//        println(message)
//        println(request)
        if (message.getName.equals("getRowId")) {
          val r: Record = request.asInstanceOf[Record]
          val req: Record = r.get("adreq").asInstanceOf[Record]
          req.get("rowID")
        } else {
          throw new IOException(s"it doesn't know message:${message.getName}")
        }
      }
    }
    val server = new NettyServer(responder, new InetSocketAddress(65111))
    server.start()
    println(s"Starting the netty server port: ${server.getPort}")
    server.join()
  }
}
