package com.inkenkun.x1.serialization.benchmark.avro

import java.io.IOException
import java.net.InetSocketAddress

import org.apache.avro.ipc.{NettyServer, NettyTransceiver, Server => IpcServer}
import org.apache.avro.ipc.specific.{SpecificRequestor, SpecificResponder}
import org.apache.avro.util.Utf8

object Server {

  lazy val server = new NettyServer(new SpecificResponder(Mail.class, new MailImpl()), new InetSocketAddress(65111))

  def main(args: Array[String]): Unit = {
    
    println("Starting the simple server...")
  }
}
