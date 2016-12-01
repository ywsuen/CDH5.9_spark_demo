package test

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, OffsetRange, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingDemo {
  val CHECK_POINT_DIRECTORY = "C:\\Users\\ThinkPad\\Desktop\\basis_configuration\\checkpoint"

  def main(args: Array[String]) = {
    if (args.length < 2) {
      System.err.println(
        s"""
           |Usage: DirectKafkaWordCount <brokers> <topics>
           |  <brokers> is a list of one or more Kafka brokers
           |  <topics> is a list of one or more kafka topics to consume from
           |
        """.stripMargin)
      System.exit(1)
    }

    val Array(brokers, topics) = args

    // Function to create and setup a new StreamingContext
    def functionToCreateContext(): StreamingContext = {
      // Create context with 2 second batch interval
      val sparkConf = new SparkConf().setAppName("StreamingDemo") //.setMaster("local[*]")
      val ssc = new StreamingContext(sparkConf, Seconds(1)) // new context

      // Creating dstream
      // Create direct kafka stream with brokers and topics
      val topicsSet = topics.split(",").toSet
      val kafkaParams = Map[String, String](("metadata.broker.list", brokers),
        ("serializer.class", "kafka.serializer.StringEncoder"),
        ("request.required.acks", "1"),
        ("producer.type", "async"))

      val dstream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

      // Hold a reference to the current offset ranges, so it can be used downstream
      var offsetRanges = Array[OffsetRange]()

      dstream
      .transform { rdd =>
        offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        rdd
      }
      .foreachRDD { (rdd, curTime) =>
        for (o <- offsetRanges) {
          // 可以在此处上传各个topic的偏移量到zookeeper
          println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
        }

        // 输出是幂等的或是原子的

      }

      ssc.checkpoint(CHECK_POINT_DIRECTORY) // set checkpoint directory
      ssc
    }

    // Get StreamingContext from checkpoint data or create a new one
    val context = StreamingContext.getOrCreate(CHECK_POINT_DIRECTORY, functionToCreateContext)

    // Do additional setup on context that needs to be done,
    // irrespective of whether it is being started or restarted
    // context. ...

    // Start the context
    context.start()
    context.awaitTermination()
  }
}
