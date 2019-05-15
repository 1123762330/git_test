package com.spring.xnpool.bmpos.tasks.bmpostasks.producer;

import com.google.gson.Gson;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.OffMill;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.TransacTions;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


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

    //支付
    public void send(String topic, TransacTions pay){
        String message = gson.toJson(pay);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic, message);
        long startTime =System.currentTimeMillis();
        ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime,message));
    }

  /*  //支付
    public void send(String topic, String key, String payment){
        String message = gson.toJson(payment);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic, key, message);
        long startTime =System.currentTimeMillis();
        ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime,key,message));
    }
*/
    //离线
    public void send(String topic, OffMill offline){
        String message = gson.toJson(offline);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic, message);
        long startTime =System.currentTimeMillis();
        ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime,message));
    }

}
