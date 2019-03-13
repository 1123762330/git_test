package com.xn.demo.controller;

import com.alibaba.fastjson.JSON;
import com.xn.demo.dao.MessageDao;
import com.xn.demo.domain.PoolWorker;
import com.xn.demo.domain.Result;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * kafka消费者
 */
@Component
public class Consumer2 {

    @Autowired
    private MessageDao messageDao;
    @KafkaListener(topics = "#{'${topicName.topic1}'.split(',')}", groupId = "${group-ids.name2}")
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            //从消息中获取Object对象
            Object object = kafkaMessage.get();
            //System.out.println("产生的"+object);
            //将Object对象转成json数据
            JSON json = JSON.parseObject(object.toString());
            //System.out.println("消费到的json数据是" + json);

            //json数据转成实体类对象
            Result result = json.toJavaObject(Result.class);

            //获取连接类型
            String type = result.getType();
            //System.out.println("类型是"+type);

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
            //System.out.println("user_name=="+user_name);
            if (type.equals("miner_connect")) {
                //List<PoolWorker> list = messageDao.findMessageList();
                messageDao.saveMessage(user_id,user_name);
                //System.out.println("返回的==="+list);
                //System.out.println("存储成功!");
            }
            //System.out.println("内容是" + worker);
            System.out.println("消费者2:" + "message2:" + object);
        }
        System.out.println("增加成功");
    }
}