package com.inkenkun.x1.serialization.benchmark.thrift

import scala.util.control.Exception._

import org.apache.thrift.transport.TSocket
import org.apache.thrift.protocol.TBinaryProtocol
import org.openjdk.jmh.annotations.{Benchmark, Scope, Setup, State, TearDown}

import com.inkenkun.x1.serialization.benchmark.Loop

object Client {

  val req = new AdRequest(
    5325279403472852193L,
    1,
    38756,
    "http://www.example.com",
    3,
    3,
    6415,
    34,
    3341,
    2080,
    9999,
    9999,
    490234
  )

  def main( args: Array[ String ] ): Unit = {
    val ns = timerNs {
      val socket = new TSocket("localhost", 9090)
      perform(socket) { client =>
        0 to 5 foreach ( i => {
          val res = client.getRowId(req)
          println(s"got:$res")
        } )
      }
    }
    println(s"$ns ns")
  }

  private def perform(socket: TSocket)(f: AdRequestReceiver.Client => Unit) = {
    allCatch withApply { t: Throwable => t.printStackTrace() } andFinally { socket.close() } apply {
      socket.open()
      val protocol = new  TBinaryProtocol(socket)
      val client = new AdRequestReceiver.Client(protocol)
      f(client)
    }
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

  lazy val socket: TSocket = new TSocket("localhost", 9090)

  @Setup
  def setup(): Unit = {
    socket.open()
  }

  @Benchmark
  def send$1k() = {
    val protocol = new TBinaryProtocol(socket)
    val client = new AdRequestReceiver.Client(protocol)

    loop1k( send(client) )
  }


  def send(client: AdRequestReceiver.Client)(i: Int): Unit = {
    client.getRowId(Client.req)
  }

  @TearDown
  def close(): Unit = {
    socket.close()
  }
}
