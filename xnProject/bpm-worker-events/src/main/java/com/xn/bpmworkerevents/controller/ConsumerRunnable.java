package com.xn.bpmworkerevents.controller;

import com.google.gson.Gson;
import com.xn.bpmworkerevents.config.Configs;
import com.xn.bpmworkerevents.entity.PoolWorker;
import com.xn.bpmworkerevents.entity.Shares;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * FileName:       ConsumerRunnable
 * Author:         Administrator
 * Date:           2019/5/20 16:44
 * Description:
 */

public class ConsumerRunnable implements Runnable {


    // 每个线程维护私有的KafkaConsumer实例
    private KafkaConsumer<String, String> consumer;

    public ConsumerRunnable(Configs configs) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configs.getBrokerList());//kafka地址
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, configs.getCommit());//自动提交
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, configs.getInterval());//自动提交间隔
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, configs.getTimeout());//session过期时间
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, configs.getKeyzer());//key序列化
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, configs.getValuezer());//value序列化
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, configs.getGroupId());//group id
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, configs.getReset());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, configs.getMaxRecords());
        this.consumer = new KafkaConsumer<>(properties);
        List<String> topic = configs.getTopic();
        consumer.subscribe(topic);
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);   // 本例使用200ms作为获取超时时间
            ArrayList<Object> arrayList = new ArrayList<>();
            for (final ConsumerRecord record : records) {
                arrayList.add(record);
            }
            if (arrayList.size() == 0) {
                continue;
            }

            List<Shares> sharesList = new ArrayList<>();
            List<PoolWorker> poolWorkersList = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                ConsumerRecord record = (ConsumerRecord) arrayList.get(i);
                if ("MposShareTopic".equals(record.topic())) {
                    Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                    if (kafkaMessage.isPresent()) {
                        //从消息中获取Object对象
                        Object object = kafkaMessage.get();
                        //log.info("MposShareTopic查询到的数据:"+object);
                        Gson gson = new Gson();
                        Shares shares = gson.fromJson(object.toString(), Shares.class);
                        sharesList.add(shares);
                    }
                }

                if ("BtcCommonEvents".equals(record.topic())) {
                    Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                    if (kafkaMessage.isPresent()) {
                        //从消息中获取Object对象
                        Object object = kafkaMessage.get();
                        //log.info("BtcCommonEvents查询到的数据:"+object);
                        //System.out.println("查询到的数据:"+object);
                        //JSONObject jsonObject = JSONObject.parseObject(object.toString());
                        //获取连接类型
                        //String type = jsonObject.getString("type");
                        Gson gson = new Gson();
                        PoolWorker poolWorker = gson.fromJson(object.toString(), PoolWorker.class);
                        poolWorkersList.add(poolWorker);
                    }
                }


            }


            System.out.println("共" + sharesList.size());
            System.out.println("共" + poolWorkersList.size());


            try {
                if (sharesList.size() != 0) {
                    new Work().insertShares(sharesList);
                    System.out.println("Shares入库成功!");
                    long endTime = System.currentTimeMillis();
                    System.out.println("终止时间:" + endTime);
                } else {
                    System.out.println("sharesList暂无新数据!");
                }

                if (poolWorkersList.size() != 0) {
                    new Work().insertPoolWorker(poolWorkersList);
                    System.out.println("poolWorker入库成功!");
                    long endTime = System.currentTimeMillis();
                    System.out.println("终止时间:" + endTime);
                } else {
                    System.out.println("poolWorkersList暂无新数据!");
                }

            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("数据已存在!");
            }
        }

    }
}
