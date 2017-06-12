package com.inkenkun.x1.serialization.benchmark.avro

import java.net.InetSocketAddress

import org.apache.avro.Protocol
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericData.Record
import org.apache.avro.ipc.NettyTransceiver
import org.apache.avro.ipc.generic.GenericRequestor
import org.openjdk.jmh.annotations.{Benchmark, Scope, State, TearDown}

import com.inkenkun.x1.serialization.benchmark.Loop

object Client {

  val definition =
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

  var data = new Record(protocol.getType("AdRequest"))
  data.put("rowID"        , 5325279403472852193L)
  data.put("clicks"       , 100)
  data.put("impression"   , 38756)
  data.put("displayURL"   , "http://www.example.com")
  data.put("adID"         , 3)
  data.put("advertiserID" , 3)
  data.put("depth"        , 6415)
  data.put("position"     , 34)
  data.put("queryID"      , 3341)
  data.put("keywordID"    , 2080)
  data.put("titleID"      , 9999)
  data.put("descriptionID", 9999)
  data.put("userID"       , 490234)

  val params = new GenericData.Record(protocol.getMessages.get("getRowId").getRequest)
  params.put("adreq", data)

  def main( args: Array[ String ] ): Unit = {
    val ns = timerNs {
      val client = new NettyTransceiver(new InetSocketAddress(65111))
      val proxy = new GenericRequestor(protocol, client)

      println("client built, got proxy.")
      0 to 5 foreach ( i => {
        val res = proxy.request("getRowId", params)
        println(s"got:$res")
      } )
      client.close()
    }
    println(s"$ns ns")
  }

  private def timerNs(f: Unit): Long = {
    val start = System.nanoTime()
    f
    val end = System.nanoTime()
    end - start
  }
}

@State(Scope.Benchmark)
class Client extends Loop {

  val client = new NettyTransceiver(new InetSocketAddress(65111))
  val proxy = new GenericRequestor(Client.protocol, client)

  @Benchmark
  def send$1k() = loop1k( send )

  def send(i: Int): Unit = {
    proxy.request("getRowId", Client.params)
  }

  @TearDown
  def close(): Unit = {
    client.close()
  }
}
