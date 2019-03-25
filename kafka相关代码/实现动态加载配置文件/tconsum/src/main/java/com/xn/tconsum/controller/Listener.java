package com.xn.tconsum.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
    @KafkaListener(topics = "#{'${topicName.topic1}'.split(',')}")
    public void listen(ConsumerRecord<?, ?> record) {
        //System.out.println("kafka的key: " + record.key());
        //System.out.println("kafka的value: " + record.value().toString());
    }
}