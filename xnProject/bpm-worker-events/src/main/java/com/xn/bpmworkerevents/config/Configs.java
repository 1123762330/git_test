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
    private String brokerList;

    @Value("${kafka.consumer.group.id}")
    private String groupId;

    @Value("#{'${kafka.consumer.topics}'.split(',')}")
    private List<String> topic;

    @Value("${kafka.consumer.enable.auto.commit}")
    private String commit;

    @Value("${kafka.consumer.auto.commit.interval}")
    private String interval;

    @Value("${kafka.consumer.session.timeout}")
    private String timeout;

    @Value("${kafka.consumer.auto.offset.reset}")
    private String reset;

    @Value("${spring.kafka.consumer.key-serializer}")
    private String keyzer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valuezer;

    @Value("${spring.kafka.consumer.max-poll-records}")
    private String maxRecords;
}
