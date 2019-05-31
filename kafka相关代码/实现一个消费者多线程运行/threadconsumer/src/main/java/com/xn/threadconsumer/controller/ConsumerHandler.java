package com.xn.threadconsumer.controller;
import com.xn.threadconsumer.domain.Configs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.*;

public class ConsumerHandler {
    // 本例中使用一个consumer将消息放入后端队列，你当然可以使用前一种方法中的多实例按照某张规则同时把消息放入后端队列
    private KafkaConsumer<String, String> consumer;
    private ExecutorService executors;

    public ConsumerHandler(Configs configs) {
        Properties props = new Properties();
        props.put("bootstrap.servers", configs.getBrokerList());
        //props.put("zookeeper.connect", configs.getZook());
        props.put("group.id", configs.getGroupId());
        props.put("enable.auto.commit", configs.getCommit());
        props.put("auto.commit.interval.ms", configs.getInterval());
        props.put("session.timeout.ms", configs.getTimeout());
        props.put("auto.offset.reset",configs.getReset());
        props.put("key.deserializer", configs.getKeyzer());
        props.put("value.deserializer", configs.getValuezer());
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("MposShareTopic"));
    }

    //创建线程池,执行存储逻辑
    public void execute(int workerNum) {
        executors = new ThreadPoolExecutor(workerNum, workerNum, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(200);
            for (final ConsumerRecord record : records) {
                executors.submit(new Worker(record));
            }
        }


    }

    //关闭线程和消费者
    public void shutdown() {
        if (consumer != null) {
            consumer.close();
        }
        if (executors != null) {
            executors.shutdown();
        }
        /*try {
            if (!executors.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("线程超时未处理,请忽略...");
            }
        } catch (InterruptedException ignored) {
            System.out.println("其他线程中断了此关闭，忽略这个消息...");
            //如果线程阻塞了,该线程会得到一个interrupt异常，
            // 可以通过对该异常的处理而退出线程对于正在运行的线程，没有任何作用！
            Thread.currentThread().interrupt();
        }*/
    }

}