package com.inkenkun.x1.serialization.benchmark

import org.apache.thrift.{TDeserializer, TSerializer}
import org.apache.thrift.protocol.TBinaryProtocol
import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

import com.inkenkun.x1.serialization.benchmark.thrift.AdRequest

@State( Scope.Benchmark )
class Thrift extends Loop {

  lazy val adreq = serialization(1)

  @Benchmark
  def serialization$1k() = loop1k( serialization )

  @Benchmark
  def deserialization$1k() = loop1k( deserialization )

  def serialization(i: Int): Array[Byte] = {
    val o = new AdRequest(
      5325279403472852193L,
      i,
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
    val serializer = new TSerializer(new TBinaryProtocol.Factory())
    serializer.serialize(o)
  }

  def deserialization(i: Int): AdRequest = {
    val deserializer = new TDeserializer(new TBinaryProtocol.Factory())
    val a = new AdRequest()
    deserializer.deserialize(a, adreq)
    a
  }

}
