package com.xn.tconsum;
import org.apache.kafka.clients.consumer.ConsumerRecord;

//业务逻辑层
public class Worker implements Runnable {


    private ConsumerRecord<String, String> consumerRecord;

    public Worker(ConsumerRecord record) {
        this.consumerRecord = record;
    }

    @Override
    public void run() {
        // 这里写你的消息处理逻辑，本例中只是简单地打印消息
        System.out.println(
         "线程名称是:"+Thread.currentThread().getName() +
         ",消费者分区是" + consumerRecord.partition() +
         ",消息起始:" + consumerRecord.offset()+
         ",消费的数据是"+consumerRecord.value());
    }
}