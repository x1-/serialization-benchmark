package com.inkenkun.x1.serialization.benchmark.thrift

import scala.util.control.Exception._

import org.apache.thrift.server.TThreadPoolServer
import org.apache.thrift.transport.TServerSocket

object Server {
  val handler = new AdRequestHandler()
  val processor = new AdRequestReceiver.Processor(handler)

  def main( args: Array[ String ] ): Unit = {
    val simple = new Runnable {
      override def run(): Unit = multi() match {
        case Left(e) => e.printStackTrace()
        case _ => {}
      }
    }
    new Thread(simple).start()
  }

  def multi(): Either[Throwable, Unit] = allCatch either {
    val socket = new TServerSocket(9090)
    val server = new TThreadPoolServer(new TThreadPoolServer.Args(socket).processor(processor))
    println("Starting the multi-thread server...")
    server.serve()
  }
}

class AdRequestHandler extends AdRequestReceiver.Iface {
  override def ping() = println("PING!")
  override def getRowId(adreq: AdRequest): Long = adreq.rowID
}
