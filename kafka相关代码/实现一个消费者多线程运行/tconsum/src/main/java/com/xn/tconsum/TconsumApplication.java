package com.xn.tconsum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TconsumApplication {

    public static void main(String[] args) {
        SpringApplication.run(TconsumApplication.class, args);
        String brokerList = "127.0.0.1:9092";
        String groupId = "group1";
        String topic = "test";
        int workerNum = 5;

        ConsumerHandler consumers = new ConsumerHandler(brokerList, groupId, topic);
        consumers.execute(workerNum);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        consumers.shutdown();
    }
}


