package com.inkenkun.x1.serialization.benchmark.thrift

import org.apache.thrift.transport.TSocket
import org.apache.thrift.protocol.TBinaryProtocol

import scala.util.control.Exception._

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
    val ms = timerNs {
      val socket = new TSocket("localhost", 9090)
      perform(socket) { client =>
        0 to 5 foreach ( i => client.getRowId(req) )
      }
    }
    println(s"${ms} ns")
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
