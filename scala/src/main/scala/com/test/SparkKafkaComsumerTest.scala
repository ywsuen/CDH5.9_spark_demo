package com.test

import java.util.{Date, UUID}

import kafka.serializer.StringDecoder
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.{OffsetRange, HasOffsetRanges, KafkaUtils}
import org.apache.spark.{TaskContext, SparkContext, SparkConf}

import scala.io.Source

object SparkKafkaComsumerTest {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(1))
    val kafkaParams: Map[String, String] = Source.fromFile("F:\\yw\\GitHub\\Spark2.0Demo\\scala\\resource\\SparkKafkaComsumerTestConf.txt")
      .getLines().toList.map(_.split("=")).map(a => (a(0), a(1))).toMap
    val topicSet = Set[String]("test")
    val dstream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc, kafkaParams, topicSet)
    dstream
      .foreachRDD { rdd =>
        rdd.collect().foreach(println)
      }
    ssc.start()
    ssc.awaitTermination()
  }
}
