package com.banno.salat.avro.test

import java.io.ByteArrayOutputStream
import java.util.UUID

import org.apache.avro.io._
import org.apache.avro.reflect.{ReflectData, CustomEncoding}
import org.apache.avro.{Conversion, LogicalType, LogicalTypes, Schema}
import org.joda.time.DateTime

import com.banno.salat.avro._
import global._
import org.apache.avro.Schema

import scala.Predef


case class TestDate2(uuid:UUID,dt:DateTime)

object TestDate2 {

  def newR: TestDate2 = TestDate2(UUID.randomUUID(),DateTime.now())
}

object AvroLogicalType {


  def main(args: Array[String]) {

    val grater1: AvroGrater[TestDate2] = grater[TestDate2]

    val schema: Schema = grater1.asAvroSchema
    println(schema)


    val baos = new ByteArrayOutputStream

    val jsonEncoder = EncoderFactory.get().jsonEncoder(schema, baos)
    val r = TestDate2.newR
    grater1.serialize(r, jsonEncoder)

    jsonEncoder.flush()


    val x: String = new Predef.String(baos.toByteArray)
    println(x)


    val decoder: JsonDecoder = DecoderFactory.get().jsonDecoder(schema, x)

    println(grater1.asObject(decoder))


    //new ReflectData().addLogicalTypeConversion()


  }



}

