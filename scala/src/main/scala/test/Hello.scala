package test

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Hello
 **/
object Hello {
  def main(args: Array[String]) = {
    val sc = new SparkContext(new SparkConf().setAppName("test").setMaster("local[*]"))
    println("Hello")
    sc.stop()
  }
}
