package com.cn.kafkaconsumer.consumer;

import com.cn.kafkaconsumer.dao.model.PoolWorker;
import com.cn.kafkaconsumer.dao.mapper.PoolWorkerMapper;
import com.cn.kafkaconsumer.dao.mapper.SharesMapper;
import com.cn.kafkaconsumer.dao.model.SharesModel;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * FileName:       Comsumer
 * Author:         Administrator
 * Date:           2019/4/22 13:17
 * Description:
 */
@Component
@Slf4j
public class ComsumerController {
    @Autowired
    private SharesMapper sharesMapper;

    @Autowired
    private PoolWorkerMapper poolWorkerMapper;

    @KafkaListener(topics = "#{'${kafka.consumer.topics}'.split(',')}", groupId = "${kafka.consumer.group.id}")
    public void listenss (ConsumerRecords<String, String> records) {

        List<SharesModel> sharesList = new ArrayList<>();
        List<PoolWorker> poolWorkerlist = new ArrayList<>();
        for (ConsumerRecord<String, String> record : records) {
            Optional<?> kafkaMessage = null;
            if (record.topic().equals("MposShareTopic")) {
                kafkaMessage = Optional.ofNullable(record.value());
                if (kafkaMessage.isPresent()) {
                    Object message = kafkaMessage.get();
                    //log.info("MposShareTopic找到的数据" + message);
                    Gson gson = new Gson();
                    SharesModel shares = gson.fromJson(message.toString(), SharesModel.class);
                    sharesList.add(shares);
                }

            }

            if (record.topic().equals("BtcCommonEvents")) {
                kafkaMessage = Optional.ofNullable(record.value());
                if (kafkaMessage.isPresent()) {
                    Object message = kafkaMessage.get();
                    //log.info("BtcCommonEvents找到的数据" + message);
                    Gson gson = new Gson();
                    PoolWorker poolWorker = gson.fromJson(message.toString(), PoolWorker.class);
                    poolWorkerlist.add(poolWorker);
                }
            }

        }

        log.info("sharesList共有:"+sharesList.size());
        log.info("poolWorkerlist共有:"+poolWorkerlist.size());
        if (sharesList.size() > 0) {
            sharesMapper.insertShares(sharesList);
        }

        if (poolWorkerlist.size() > 0) {
            poolWorkerMapper.insertPoolWorker(poolWorkerlist);
        }


    }


}
