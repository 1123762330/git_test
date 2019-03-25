package com.xn.producert.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xn.producert.domain.PoolWorker;
import com.xn.producert.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * kafka生产者
 */
@RestController
public class KafkaSender {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    //发送消息方法
    @RequestMapping("/sendMessage")
    @ResponseBody
    public String send() throws Exception {
        PoolWorker worker=new PoolWorker();
        for (int i = 0; i < 5; i++) {
            String username = "atiet41";
            username += i;
            System.out.println(username);
            worker = new PoolWorker(202, username, 2853241594001576701L, "121x30", "cgminer/4.9.0", "120.71.225.203", "01003acf");
            Result data = new Result();

            //发送时间,转换格式
            Date time = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time1 = format.format(time);

            //将数据进行封装
            data.setCreated_at(time1);
            data.setType("miner_connect");
            data.setContent(worker);

            //发送这个数据
            kafkaTemplate.send("test", gson.toJson(data));
            System.out.println("message =" + gson.toJson(data));
        }
            System.out.println("消息发送成功");
            return "消息已经发送";
    }
}
