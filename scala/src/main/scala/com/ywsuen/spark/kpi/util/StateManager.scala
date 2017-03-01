package com.ywsuen.spark.kpi.util

import java.io.Serializable

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._

class StateManager_localMap[KeyType, ValueType](sc: SparkContext) {
  private val cacheMap = scala.collection.mutable.HashMap[KeyType, List[ValueType]]()

  def upDataAndReturnStaternState(rdd: RDD[Tuple2[KeyType, ValueType]]): RDD[Tuple2[KeyType, List[ValueType]]] = {
    rdd.collect().foreach { case (k, v) =>
      val newV = if (cacheMap.contains(k)) {
        cacheMap(k) :+ v
      } else {
        List(v)
      }
      cacheMap.+=(k -> newV)
    }
    sc.parallelize(cacheMap.toList)
    //rdd.map(t => t._1 -> List(t._2))
  }

  def clear() = {
    cacheMap.clear()
  }

}

class StateManager_redis[KeyType <: Serializable, ValueType <: Serializable](redisServers: String) {

  def upDataAndReturnStaternState(rdd: RDD[Tuple2[KeyType, ValueType]])( f: List[ValueType] => Boolean)
  : RDD[Tuple2[KeyType, List[ValueType]]] = {
    val _redisServers = redisServers
    rdd.mapPartitions { iterator =>
      val cli = new RedisUtil(_redisServers)
      val res = iterator.map { case (k, v) =>
        val key = RedisUtil.ObjectToByte(k)
        val list = cli.getClient.lrange(key, 0L, -1L).map(RedisUtil.ByteToObject(_).asInstanceOf[ValueType]).toList
        val newList = list :+ v
        val res: Tuple2[KeyType, List[ValueType]] = if (f(newList)) {
          cli.getClient.del(key)
          (k, newList)
        } else {
          cli.getClient.rpush(key, RedisUtil.ObjectToByte(v))
          null
        }
        if (!iterator.hasNext) cli.getClient.close() // 因为代码是惰性的, 清理工作需要放在这里
        res
      }
      res
    }.filter(_ != null)
    //    val _redisServers = redisServers
    //    rdd.mapPartitions{ partition =>
    //      val cli = new RedisUtil(_redisServers)
    //      val states = partition.map{case (k,v) =>
    //        println(cli.getClient.echo("echo"))
    //        if(!partition.hasNext){
    //          cli.getClient.close()
    //        }
    //        (k, List(v))
    //      }
    //      println("cli")
    //      states
    //    }
  }


  //  class StateManager_spark[KeyType <: Serializable, ValueType <: Serializable
  //  , StateType <: Serializable, MappedType <: Serializable] {
  //
  //  }
}

object StateManager {
  def getLocalMapStateManager[KeyType, ValueType](sc: SparkContext) = {
    new StateManager_localMap[KeyType, ValueType](sc)
  }

  def getRedisStateManager[KeyType <: Serializable, ValueType <: Serializable](redisSevers: String) = {
    new StateManager_redis[KeyType, ValueType](redisSevers)
  }
}



