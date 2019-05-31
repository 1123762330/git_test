package com.cn.kafkaconsumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName:       KafkaConsumerConfig
 * Author:         Administrator
 * Date:           2019/4/22 10:06
 * Description:    kafka消费者配置
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Autowired
    private Configs configs;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory () {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1);
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory () {

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configs.brokerList);//kafka地址
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);//自动提交
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, configs.interval);//自动提交间隔
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, configs.timeout);//session过期时间
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, configs.keyzer);//key序列化
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, configs.valuezer);//value序列化
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, configs.getGroupId());//group id

        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, configs.maxRecords);//每一批数量

        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,configs.reset);
        ConsumerFactory<String, String> consumer = new DefaultKafkaConsumerFactory<String, String>(properties);
        return consumer;

    }

}
