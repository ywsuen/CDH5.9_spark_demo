package com.test

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Hello
 **/
object Hello {
  def main(args: Array[String]) = {
    val sc = new SparkContext(new SparkConf().setAppName("test").setMaster("local[*]"))
    val res = sc.parallelize(1 to 10000).reduce(_ + _)
    println(s"1+2+3+...+10000 = ${res}")
    sc.stop()
  }
}
