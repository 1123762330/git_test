package com.xn.bpmworkerevents.controller;

import com.xn.bpmworkerevents.config.Configs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConsumerHandler {
    // 本例中使用一个consumer将消息放入后端队列，你当然可以使用前一种方法中的多实例按照某张规则同时把消息放入后端队列
    private KafkaConsumer<String, String> consumer;
    private ExecutorService executors;
    private final static Logger logger = LoggerFactory.getLogger(ConsumerHandler.class);

    public ConsumerHandler (Configs configs) {
        Properties props = new Properties();
        props.put("bootstrap.servers", configs.getBrokerList());
        //props.put("zookeeper.connect", configs.getZook());
        props.put("group.id", configs.getGroupId());
        props.put("max.poll.records",configs.getMaxRecords());
        props.put("enable.auto.commit", configs.getCommit());
        props.put("auto.commit.interval.ms", configs.getInterval());
        props.put("session.timeout.ms", configs.getTimeout());
        props.put("auto.offset.reset", configs.getReset());
        props.put("key.deserializer", configs.getKeyzer());
        props.put("value.deserializer", configs.getValuezer());
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(configs.getTopic()));
    }

    //创建线程池,执行存储逻辑
    public void execute (int workerNum) {
        executors = new ThreadPoolExecutor(workerNum, workerNum, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());


        long startTime = System.currentTimeMillis();
        logger.info("起始时间"+startTime);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(200);
            ArrayList<Object> arrayList = new ArrayList<>();
            for (final ConsumerRecord record : records) {
                arrayList.add(record);
            }
            if(arrayList.size()==0){
                continue;
            }
            executors.submit(new Worker(arrayList));
        }
    }

    //关闭线程和消费者
    public void shutdown () {
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