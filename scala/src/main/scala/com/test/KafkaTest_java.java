package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kafka.producer.KeyedMessage;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

public class KafkaTest_java {
    public static void main(String[] args) throws InterruptedException {


        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", "10.60.6.31:9092");
        //props.put("partitioner.class", "kafka.producer.Partitioner#kafka.producer.DefaultPartitioner");
        props.put("request.required.acks", "1");
        //props.put("compression.codec", "snappy");
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);


//        // 单个发送
//        for (int i = 0; i <= 1000000; i++) {
//            KeyedMessage<String, String> message =
//                    new KeyedMessage<String, String>("test", i + "", "Message" + i);
//            System.out.println("message" + i);
//            producer.send(message);
//            Thread.sleep(1000);
//        }


        // 批量发送
        List<KeyedMessage<String, String>> messages = new ArrayList<KeyedMessage<String, String>>(100);
        for (int i = 0; i <= 10000; i++) {
            KeyedMessage<String, String> message =
                    new KeyedMessage<String, String>("st4", "abc", "Message" + i);
            messages.add(message);
            if (i % 10 == 0) {
                producer.send(messages);
                messages.clear();
            }
        }
        producer.send(messages);
    }

}
