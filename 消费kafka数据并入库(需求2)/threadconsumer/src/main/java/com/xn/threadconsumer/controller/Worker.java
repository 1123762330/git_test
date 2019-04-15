package com.xn.threadconsumer.controller;
import com.alibaba.fastjson.JSON;

import com.xn.threadconsumer.dao.impl.MessageDaoImpl;
import com.xn.threadconsumer.domain.PoolWorker;
import com.xn.threadconsumer.domain.Result;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Date;
import java.util.Optional;

//业务逻辑层
public class Worker implements Runnable {

    MessageDaoImpl messageDao=new MessageDaoImpl();

    private ConsumerRecord<String, String> consumerRecord;

    public Worker(ConsumerRecord record) {
        this.consumerRecord = record;
    }

    @Override
    public void run() {
        // 这里写你的消息处理逻辑
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
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

            // 获取参数
            Integer user_id = worker.getUser_id();
            String user_name = worker.getUser_name();
            String worker_name = worker.getWorker_name();
            user_name+="."+worker_name;
            if (type.equals("miner_connect")) {
                try {
                    messageDao.saveMessage(user_id,user_name);
                    //long end = System.currentTimeMillis() ;
                    //System.out.println("结束时间是"+end);
                    System.out.println(
                            "线程名称是:"+Thread.currentThread().getName() +
                            ",消费者分区是" + consumerRecord.partition() +
                            ",消息起始:" + consumerRecord.offset()+
                            ",消费的数据是"+consumerRecord.value());
                    System.out.println("存储成功!");
                } catch (Exception e) {
                    //e.printStackTrace();
                    //System.out.println("数据已存在");
                }
            }
        }
    }
}