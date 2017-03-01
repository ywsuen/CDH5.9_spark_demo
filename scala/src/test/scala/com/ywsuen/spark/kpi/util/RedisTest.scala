package com.ywsuen.spark.kpi.util

import org.apache.spark.SparkContext
import org.apache.spark.streaming.{Seconds, StreamingContext}

object RedisTest {
  def redisTest(): Unit = {
    val sc = new SparkContext("local[*]","test")
    val rdd = sc.parallelize((1 to 5),5)
    val redisCli = new RedisUtil("10.60.6.19:6379")
    rdd.foreach(num => new RedisUtil("10.60.6.17:6379,10.60.6.18:6379,10.60.6.24:6379").getClient.set(num.toString, num.toString))
    rdd.map(num => new RedisUtil("10.60.6.17:6379,10.60.6.18:6379,10.60.6.24:6379").getClient.get(num.toString)).collect().foreach(println)

    val start = System.currentTimeMillis()
    (0 to 1000).foreach{ i =>
      val cli = new RedisUtil("10.60.6.17:6379,10.60.6.18:6379,10.60.6.24:6379")
      cli.getClient.close()
    }
    println("连接用时:"+(System.currentTimeMillis() - start)+"ms")
  }

  def main(args: Array[String]) {
    redisTest()
  }
}
