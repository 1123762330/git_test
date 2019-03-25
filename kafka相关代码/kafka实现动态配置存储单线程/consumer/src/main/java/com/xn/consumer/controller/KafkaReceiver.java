package com.xn.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.xn.consumer.dao.MessageDao;
import com.xn.consumer.domain.PoolWorker;
import com.xn.consumer.domain.Result;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * kafka消费者
 */
@Component
public class KafkaReceiver {

    @Autowired
    private MessageDao messageDao;

    @KafkaListener(topics ="#{'${topicName.topic1}'.split(',')}", groupId = "${group-ids.name}")
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            //从消息中获取Object对象
            Object object = kafkaMessage.get();
            //将Object对象转成json数据
            JSON json = JSON.parseObject(object.toString());
            //json数据转成实体类对象
            Result result = json.toJavaObject(Result.class);
            //获取连接类型
            String type = result.getType();
            //数据类型转换
            Object content = result.getContent();
            //将Object对象转成json数据
            JSON json2 = JSON.parseObject(content.toString());
            //json数据转成实体类对象
            PoolWorker worker = json2.toJavaObject(PoolWorker.class);

            Integer user_id = worker.getUser_id();
            String user_name = worker.getUser_name();
            String worker_name = worker.getWorker_name();
            user_name+="."+worker_name;
            System.out.println("消费者1:" + "message:" + object);

            if (type.equals("miner_connect")) {
                messageDao.saveMessage(user_id,user_name);
                System.out.println("存储成功!");
            }

        }

    }
}