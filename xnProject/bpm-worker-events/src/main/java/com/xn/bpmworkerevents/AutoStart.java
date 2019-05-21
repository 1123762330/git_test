package com.xn.bpmworkerevents;

import com.xn.bpmworkerevents.controller.ConsumerHandler;
import com.xn.bpmworkerevents.config.Configs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AutoStart implements CommandLineRunner {
    @Autowired
    private Configs configs;

    @Override
    public void run(String... strings) throws Exception {
        log.info("开始执行程序");
        //调用存储方法
        save();
    }

    public void save() {
        //创建的线程数
        int workerNum = 5;
        //创建消费者
        ConsumerHandler consumers = new ConsumerHandler(configs);
        consumers.execute(workerNum);
        consumers.shutdown();
    }
}