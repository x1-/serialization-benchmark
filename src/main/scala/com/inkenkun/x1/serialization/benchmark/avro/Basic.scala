package com.inkenkun.x1.serialization.benchmark.avro

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

import com.inkenkun.x1.serialization.benchmark.Loop

@State( Scope.Benchmark )
class Basic extends Loop {

  lazy val adDatumWriter = new SpecificDatumWriter[AdRequest](classOf[AdRequest])
  lazy val adDatumReader = new SpecificDatumReader[AdRequest](classOf[AdRequest])
  lazy val encoderFactory = EncoderFactory.get()
  lazy val decoderFactory = DecoderFactory.get()

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
    val bao = new ByteArrayOutputStream()
    val encoder = encoderFactory.directBinaryEncoder(bao, null)
    adDatumWriter.write(o, encoder)
    encoder.flush()
    bao.toByteArray
  }

  def deserialization(i: Int): AdRequest = {
    val bis = new ByteArrayInputStream(adreq)
    val decoder = decoderFactory.directBinaryDecoder(bis, null)
    adDatumReader.read(null, decoder)
  }
}
