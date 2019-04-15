package com.xn.threadconsumer;

import com.xn.threadconsumer.controller.ConsumerHandler;
import com.xn.threadconsumer.domain.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class AutoStart implements CommandLineRunner {
    @Autowired
    private Configs configs;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("开始执行程序");
        //调用存储方法
        save();
    }

    public void save() {
        //创建的线程数
        int workerNum = 10;
        //创建消费者
        ConsumerHandler consumers = new ConsumerHandler(configs);
        //开启线程

        consumers.execute(workerNum);
        consumers.shutdown();
    }
}