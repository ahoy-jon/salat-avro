package com.banno.salat.avro

import org.apache.avro.{Conversion, Schema, LogicalType}
import org.joda.time.DateTime



object DateSupport {

  //https://github.com/edn-format/edn#inst-rfc-3339-format
  class IsoDateTimeLogicalTime extends LogicalType("inst") {
    override def validate(schema: Schema) {
      super.validate(schema)
      if (schema.getType != Schema.Type.STRING) {
        throw new IllegalArgumentException("Date Time can only be used with an underlying string type")
      }
    }
  }


  class DateTimeAsIsoDate extends Conversion[DateTime] {

    override def getRecommendedSchema: Schema = (new IsoDateTimeLogicalTime).addToSchema(Schema.create(Schema.Type.STRING))

    override def fromCharSequence(value: CharSequence, schema: Schema, `type`: LogicalType): DateTime = {
      DateTime.parse(value.toString)
    }

    override def toCharSequence(value: DateTime, schema: Schema, `type`: LogicalType): CharSequence = {
      value.toString
    }

    override def getConvertedType: Class[DateTime] = classOf[DateTime]

    override def getLogicalTypeName: String = "inst"
  }

}