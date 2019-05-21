package com.spring.xnpool.bmpos.tasks.bmpostasks.producer;

import com.google.gson.Gson;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.MessageBeens;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.OffMill;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.TransacTions;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;


/**
 * FileName:       Producer
 * Author:         Administrator
 * Date:           2019/4/22 12:02
 * Description:
 */
@Component
public class Producer {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new Gson();

    //MessageBeens
    public void send(String topic, Map<String,Object> beens){
        String message = gson.toJson(beens);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic, message);
        long startTime =System.currentTimeMillis();
        ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime,message));
    }

}
