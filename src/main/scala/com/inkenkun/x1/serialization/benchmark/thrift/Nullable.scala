package com.inkenkun.x1.serialization.benchmark.thrift

import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.{TDeserializer, TSerializer}
import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

import com.inkenkun.x1.serialization.benchmark.Loop

@State( Scope.Benchmark )
class Nullable extends Loop {

  lazy val adreq = serialization(1)

  @Benchmark
  def serialization$1k() = loop1k( serialization )

  @Benchmark
  def deserialization$1k() = loop1k( deserialization )

  def serialization(i: Int): Array[Byte] = {
    val o = new AdRequestNullable(5325279403472852193L)
    o.setClicks(i)
    o.setImpression(38756)
    o.setDisplayURL("http://www.example.com")
    o.setAdID(3)
    o.setAdvertiserID(3)
    o.setDepth(6415)
    o.setPosition(34)
    o.setQueryID(3341)
    o.setKeywordID(2080)
    o.setTitleID(9999)
    o.setDescriptionID(9999)
    o.setUserID(490234)
    val serializer = new TSerializer(new TBinaryProtocol.Factory())
    serializer.serialize(o)
  }

  def deserialization(i: Int): AdRequestNullable = {
    val deserializer = new TDeserializer(new TBinaryProtocol.Factory())
    val a = new AdRequestNullable()
    deserializer.deserialize(a, adreq)
    a
  }
}
