package com.test

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.SynchronizedQueue
import scala.collection.mutable

object QueueRdd {
  def main(args: Array[String]) {
    val sc = new SparkContext("local[*]","queueRdd")
    val ssc = new StreamingContext(sc, Seconds(1))

    val queue = new SynchronizedQueue[RDD[Int]]()
    val dstream = ssc.queueStream(queue)

    val rddQueue = new mutable.Queue[RDD[Int]]()
    dstream.window(Seconds(10)).foreachRDD{(rdd, time) =>
      println(time," ",rdd.collect().toList)
    }
    ssc.start()

    (0 to 10).foreach{i =>
      val aRdd = sc.makeRDD(0 to 10)
      queue.enqueue(aRdd)
      Thread.sleep(1000)
    }
    ssc.stop()
  }
}
