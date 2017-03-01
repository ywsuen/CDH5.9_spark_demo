package com.ywsuen.spark.kpi.util

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.Queue
import scala.collection.mutable.SynchronizedQueue

object StateManagerTest {

  def stateManagerTest(): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("test").setMaster("local[*]"))
    val rdd = sc.parallelize(1 to 1000).map(i => (i%3) -> i)

//    val manager = StateManager.getLocalMapStateManager[String, String](sc)
//    manager.upDataAndReturnStaternState(rdd).collect().foreach(println)
//    manager.clear()

    case class atype(a: Int)
    val manager_redis = StateManager.getRedisStateManager[atype, atype]("10.60.6.17:6379,10.60.6.18:6379,10.60.6.24:6379")
    //manager_redis.upDataAndReturnStaternState(rdd.repartition(1).map(t => (atype(t._1),atype(t._2))))(_.size >= 10).collect().foreach(println)

    val ssc = new StreamingContext(sc,Seconds(10))
    val rddQueue = new SynchronizedQueue[RDD[(atype,atype)]]()
    rddQueue.enqueue(rdd.map(t => (atype(t._1),atype(t._2))))
    val dstream = ssc.queueStream(rddQueue)
    dstream.foreachRDD{rdd =>
      manager_redis.upDataAndReturnStaternState(rdd)(_.size >= 10).collect().foreach(println)
    }
//    dstream.foreachRDD{rdd =>
//      manager.upDataAndReturnStaternState(rdd).collect().foreach(println)
//    }
//

    ssc.start()
    if(ssc.awaitTerminationOrTimeout(60*1000)){
      ssc.stop()
      println("结束")
    }
  }

  def main(args: Array[String]) {
    stateManagerTest()
  }

}
