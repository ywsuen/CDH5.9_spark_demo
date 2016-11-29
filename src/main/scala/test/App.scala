package test

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Hello
 **/
object App {
  def main(args: Array[String]) = {
    val sc = new SparkContext(new SparkConf().setAppName("test").setMaster("local[*]"))
    println("Hello")
    sc.stop()
  }
}
