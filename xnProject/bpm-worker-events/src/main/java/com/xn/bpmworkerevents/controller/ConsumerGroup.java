package com.xn.bpmworkerevents.controller;

import com.xn.bpmworkerevents.config.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName:       ConsumerGroup
 * Author:         Administrator
 * Date:           2019/5/20 16:53
 * Description:
 */
@Component
public class ConsumerGroup {

    private static int consumerNum;

    public ConsumerGroup() {
    }


    //消费者集合
    private List<ConsumerRunnable> consumers = new ArrayList<ConsumerRunnable>();

    public ConsumerGroup(int consumerNum,Configs configs) {
        //创建多个消费者加入集合中,用来执行线程
        for (int i = 0; i < consumerNum; ++i) {
            ConsumerRunnable consumerThread = new ConsumerRunnable(configs);
            consumers.add(consumerThread);
        }
    }

    //执行线程方法
    public void execute() {
        for (ConsumerRunnable consumer : consumers) {
            new Thread(consumer).start();
        }
    }

}
