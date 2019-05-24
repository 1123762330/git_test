package com.xn.bpmworkerevents.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Configs {

    /*@Value("${kafka.consumer.zookeeper.connect}")
    String zook;*/

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    String brokerList;

    @Value("${kafka.consumer.group.id}")
    String groupId;

    @Value("#{'${kafka.consumer.topic}'.split(',')}")
    List<String> topic;

    @Value("${kafka.consumer.enable.auto.commit}")
    String commit;

    @Value("${kafka.consumer.auto.commit.interval}")
    String interval;

    @Value("${kafka.consumer.session.timeout}")
    String timeout;

    @Value("${kafka.consumer.auto.offset.reset}")
    String reset;

    @Value("${spring.kafka.consumer.key-serializer}")
    String keyzer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    String valuezer;

    @Value("${spring.kafka.consumer.max-poll-records}")
    String maxRecords;

}
