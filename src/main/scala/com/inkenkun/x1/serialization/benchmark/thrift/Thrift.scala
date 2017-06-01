package com.inkenkun.x1.serialization.benchmark.thrift

import org.apache.thrift.TException
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.TSerializer
import org.apache.thrift.TDeserializer

import org.openjdk.jmh.annotations.{Benchmark, Scope, State}


@State( Scope.Benchmark )
class Thrift {

  val   n1k = 10 * 10 * 10
  val  n10k = 10 * 10 * 10 * 10
  val n100k = 10 * 10 * 10 * 10 * 10

  def loop1k( f: Int => Unit )   = 1 to   n1k foreach ( i => f(i) )
  def loop10k( f: Int => Unit )  = 1 to  n10k foreach ( i => f(i) )
  def loop100k( f: Int => Unit ) = 1 to n100k foreach ( i => f(i) )

  @Benchmark
  def serialization$1k() = loop1k( serialization )

  def serialization(i: Int): Byte = {
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
    val bytes = serializer.serialize(o)
    bytes(0)
  }
}
