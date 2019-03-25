package com.xn.consumer.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * kafka消费者
 */
@Component
public class KafkaReceiverTwo {

    @KafkaListener(topics = "#{'${topicName.topic1}'.split(',')}", groupId = "${group-ids.name2}")
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            //从消息中获取Object对象
            Object object = kafkaMessage.get();
            System.out.println("消费者2==:" + record+"，record==:" + record+",message==:" + object+",---");
            System.out.println("消费者2:" + "message:" + object);
        }

    }
}
