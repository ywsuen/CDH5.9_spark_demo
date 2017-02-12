package com.test

import net.liftweb.json.{Serialization, DefaultFormats}

object JsonParser {
  case class Topics(
                   topic: Map[String, Topic]
                     )
  case class Topic(topicName: String, bizName: String = "zz")

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats

    val topics = Topics(Map("zzt1" -> Topic("zzt1","zzt"),"zzt2" -> Topic("zzt2","zzt")))
    val jsonStr = Serialization.write(topics)
    println(jsonStr)

    val res = Serialization.read[Topics](
      """
        |{"topic":{"zzt1":{"topicName":"zzt1","bizName":"zzt"},"zzt2":{"topicName":"zzt2","bizName":"zzt"}}}
      """.stripMargin)
    println(res)
  }
}
