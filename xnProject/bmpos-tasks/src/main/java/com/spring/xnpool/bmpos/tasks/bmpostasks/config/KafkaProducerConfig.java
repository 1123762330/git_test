package com.spring.xnpool.bmpos.tasks.bmpostasks.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName:       KafkaProducerConfig
 * Author:         Administrator
 * Date:           2019/4/22 10:40
 * Description:    KafkaProducer提供者配置
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

    /**
     * 生产者配置
     * @return
     */
    public ProducerFactory<String, String> producerFactory() {

        // set the producer properties
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.131:9092");//kafka地址
        properties.put(ProducerConfig.RETRIES_CONFIG,0);//失败后 自动提交次数
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 4096);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<String, String>(properties);
    }

    /**
     * 创建kafkatemplate
     * @return
     */
    @Bean("kafkaTemplate")
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(producerFactory());
        return kafkaTemplate;
    }

}
