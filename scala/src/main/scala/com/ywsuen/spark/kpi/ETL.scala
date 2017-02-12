package com.ywsuen.spark.kpi

import java.util.{Date, UUID}

import com.ywsuen.debugger.Debugger
import com.ywsuen.spark.kpi.bean.{Constant, ExtractConf}
import com.ywsuen.spark.kpi.util.{JsonParserUtil, Configuration}
import kafka.serializer.StringDecoder
import org.apache.spark.{TaskContext, SparkConf}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, OffsetRange, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ETL extends Debugger{
  def main(args: Array[String]) = {
    if (args.length == 0) {
      System.err.println(
        s"""
           |Usage:
        """.stripMargin)
      System.exit(1)
    }

    val Array(filePath, brokers, topics, checkpiontDircetory) = args

    val cfgObj = Configuration.createCfg[ExtractConf](filePath)

    def functionToCreateContext(): StreamingContext = {
      val sparkConf = new SparkConf().setAppName("StreamingDemo") // .setMaster("local[*]")
      val ssc = new StreamingContext(sparkConf, Seconds(60))

      val topicsSet = topics.split(",").toSet
      val kafkaParams = Map[String, String](("metadata.broker.list", brokers))

      val dstream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

      var offsetRanges = List[OffsetRange]() // 使用Array()会在集群中触发ClassNoFound Exception, 这是一个bug

      dstream
        .transform { rdd =>
          offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges.toList
          rdd
        }
        .foreachRDD { (rdd, time) =>
          for (o <- offsetRanges) {
            // 可以在此处上传各个topic的偏移量到zookeeper
            println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
          }

          // 输出是幂等的或是原子的
          // 下为一种用事务实现精确一次语义的方法
          rdd.foreachPartition { partitionIterator =>
            val partitionId = TaskContext.get.partitionId()
            val uniqueId = UUID.fromString(s"${time.milliseconds}$partitionId")
            // use this uniqueId to transactionally commit the data in partitionIterator
            println(new Date()+s" uniqueId:$uniqueId partitionIterator: ")
            partitionIterator.foreach(println)
          }
        }

      ssc.checkpoint(checkpiontDircetory)
      ssc
    }

    val context = StreamingContext.getOrCreate(checkpiontDircetory,functionToCreateContext)

    // Do additional setup on context that needs to be done,
    // irrespective of whether it is being started or restarted
    // context. ...

    context.start()
    context.awaitTermination()
  }

  // 把字符串转换为一个代表一行数据的bean, 所有解析处理时都会用这个bean
  def string2KpiBean(s: String):KpiBean = {
    val map = JsonParserUtil.parse2Map(s)
    val index = map.get(Constant.INDEX)
    val sourceType = map.get(Constant.SOURCE_HOST)
    val log = map.get(Constant.LOG)
    val file_path = map.get(Constant.FILE_PATH)


    new KpiBean()
  }
}

class KpiBean()
