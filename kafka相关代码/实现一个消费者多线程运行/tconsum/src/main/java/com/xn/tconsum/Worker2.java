package com.xn.tconsum;
import org.apache.kafka.clients.consumer.ConsumerRecord;

//业务逻辑层
public class Worker2 implements Runnable {

    private ConsumerRecord<String, String> consumerRecord;


    public Worker2(ConsumerRecord record) {
        this.consumerRecord = record;
    }

    @Override
    public void run() {
        // 这里写你的消息处理逻辑，本例中只是简单地打印消息
        System.out.println(
         "线程2名称是:"+Thread.currentThread().getName() +
         ",消费2者分区是" + consumerRecord.partition() +
         ",消息2起始:" + consumerRecord.offset()+
         ",消费2的数据是"+consumerRecord.value());
    }
}