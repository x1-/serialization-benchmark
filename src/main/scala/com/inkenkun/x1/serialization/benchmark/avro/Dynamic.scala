package com.inkenkun.x1.serialization.benchmark.avro

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File}

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericDatumReader, GenericDatumWriter, GenericRecord}
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

import com.inkenkun.x1.serialization.benchmark.Loop

@State( Scope.Benchmark )
class Dynamic extends Loop {

  lazy val definition =
    """{
      |  "type": "record",
      |  "name": "AdRequest",
      |  "fields": [
      |    {"name": "rowID"        , "type": "long"},
      |    {"name": "clicks"       , "type": "int"},
      |    {"name": "impression"   , "type": "int"},
      |    {"name": "displayURL"   , "type": ["string", "null"]},
      |    {"name": "adID"         , "type": ["int"   , "null"]},
      |    {"name": "advertiserID" , "type": ["int"   , "null"]},
      |    {"name": "depth"        , "type": ["int"   , "null"]},
      |    {"name": "position"     , "type": ["int"   , "null"]},
      |    {"name": "queryID"      , "type": ["int"   , "null"]},
      |    {"name": "keywordID"    , "type": ["int"   , "null"]},
      |    {"name": "titleID"      , "type": ["int"   , "null"]},
      |    {"name": "descriptionID", "type": ["int"   , "null"]},
      |    {"name": "userID"       , "type": ["int"   , "null"]}
      |  ]
      |}
    """.stripMargin

  lazy val schema = new Schema.Parser().parse(new File( "src/main/avro/kdd12_training.avro" ))
  lazy val adDatumWriter = new GenericDatumWriter[GenericRecord](schema)
  lazy val adDatumReader = new GenericDatumReader[GenericRecord](schema)
  lazy val encoderFactory = EncoderFactory.get()
  lazy val decoderFactory = DecoderFactory.get()

  lazy val adreq = serialization(1)

  @Benchmark
  def serialization$1k() = loop1k( serialization )

  @Benchmark
  def deserialization$1k() = loop1k( deserialization )

  def serialization(i: Int): Array[Byte] = {
    val o = new GenericData.Record(schema)
    o.put("rowID"        , 5325279403472852193L)
    o.put("clicks"       , i)
    o.put("impression"   , 38756)
    o.put("displayURL"   , "http://www.example.com")
    o.put("adID"         , 3)
    o.put("advertiserID" , 3)
    o.put("depth"        , 6415)
    o.put("position"     , 34)
    o.put("queryID"      , 3341)
    o.put("keywordID"    , 2080)
    o.put("titleID"      , 9999)
    o.put("descriptionID", 9999)
    o.put("userID"       , 490234)

    val bao = new ByteArrayOutputStream()
    val encoder = encoderFactory.directBinaryEncoder(bao, null)
    adDatumWriter.write(o, encoder)
    encoder.flush()
    bao.toByteArray
  }

  def deserialization(i: Int): GenericRecord = {
    val bis = new ByteArrayInputStream(adreq)
    val decoder = decoderFactory.directBinaryDecoder(bis, null)
    adDatumReader.read(null, decoder)
  }
}
