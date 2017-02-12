package com.ywsuen.spark.kpi.util

import net.liftweb.json.{DefaultFormats, Serialization}

object JsonParserUtil {
  def parse2CaseClass[T](jsonString: String)(implicit m: Manifest[T]): Either[T, Exception] = {
    implicit val formats = DefaultFormats

    val obj: T = try {
      Serialization.read[T](jsonString)
    } catch {
      case e: Exception => return Right(e)
    }

    Left(obj)
  }

  def parse2Map(jsonString: String):Map[String, Any] = {
    val jsonObj = net.liftweb.json.parse(jsonString).values.asInstanceOf[Map[String, Any]]
    jsonObj
  }
}

