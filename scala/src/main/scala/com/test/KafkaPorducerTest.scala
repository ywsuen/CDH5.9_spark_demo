package com.test

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.collection.JavaConversions._

object KafkaPorducerTest {
  def main(args: Array[String]) {
    //KafkaTest_java.main(Array[String]())
    {
      val props = new Properties()
      /* 参考: http://kafka.apache.org/documentation/#producerconfigs */
      ////////////////重要配置/////////////////////
      props.put("bootstrap.servers", "10.60.6.31:9092,10.60.6.32:9092")
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("acks", "all") //可选[all,1,0] 默认1, 代表接受到多少kafka服务器应答, 就确认发送成功
      props.put("buffer.memory", "33554432") // 默认33554432B=32MB, 发送缓存, 如果来不及发送超过max.block.ms(默认60s)会抛出错误
      props.put("compression.type", "none") // 可选[none,gzip,snappy,lz4], 默认none, 压缩类型
      props.put("retries", "0") // 失败重发次数, 默认0, 重发可能会改变分区顺序
      props.put("batch.size", "16384") // 默认16KB, kafka会合并两次发送间的数据, 配置指明合并的总大小上限, 一个batch确认, 过小会导致频繁确认而降低了吞吐量
      props.put("linger.ms", "0") // 默认0, 发送前进进行延迟, 并合并新的数据, 有助于提高吞吐量
      ////////////////其他配置///////////////////
      props.put("max.block.ms", "60000") // 阻塞时间上限, 当缓冲区满或元数据不可用时, producter.send()函数会阻塞, 超过这个时间会抛出错误

      val producer = new KafkaProducer[String, String](props)
      for (i <- 0 to 99) {
        val record = new ProducerRecord[String, String]("test", Integer.toString(i % 2), Integer.toString(i))
        println("sending: " + record)
        producer.send(record)
      }
      producer.close()
    }
  }
}
