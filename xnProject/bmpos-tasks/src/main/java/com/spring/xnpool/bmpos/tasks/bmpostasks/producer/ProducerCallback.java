package com.spring.xnpool.bmpos.tasks.bmpostasks.producer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * FileName:       ProducerCallBack
 * Author:         Administrator
 * Date:           2019/4/22 12:12
 * Description:
 */
@Data
@Slf4j
public class ProducerCallback implements ListenableFutureCallback<SendResult<String,String>> {

    private final long startTime;


    private final String message;

    @Override
    public void onFailure(Throwable ex) {
        ex.printStackTrace();
    }

    @Override
    public void onSuccess(@Nullable SendResult<String,String> result) {
        if(result == null){
            return;
        }
        long elapasedTime = System.currentTimeMillis() - startTime;
        RecordMetadata metadata = result.getRecordMetadata();
        if(metadata != null){
            StringBuilder record = new StringBuilder();
            record.append("message(")
                    .append("message = ").append(message).append(")")
                    .append("send to partition(").append(metadata.partition()).append(")")
                    .append("with offset(").append(metadata.offset()).append(")")
                    .append("in").append(elapasedTime).append(" ms");
            log.info(record.toString());
        }
    }


}
