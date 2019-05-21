package com.xn.bpmworkerevents.controller;

import com.alibaba.fastjson.JSONObject;
import com.xn.bpmworkerevents.dao.PoolWorkerMapper;
import com.xn.bpmworkerevents.domain.Result;
import com.xn.bpmworkerevents.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.*;

//业务逻辑层
@Slf4j
public class Worker implements Runnable {

    private PoolWorkerMapper poolWorkerMapper;

    //private ConsumerRecord<String, String> consumerRecord;
    private List recordlist;

    public Worker(List list) {
        this.recordlist = list;
        this.poolWorkerMapper = SpringUtils.getBean(PoolWorkerMapper.class);
    }



    @Override
    public void run() {
        // 这里写你的消息处理逻辑
        List<Result> resultsList = new ArrayList<>();
        for (int i = 0; i < recordlist.size(); i++) {
            ConsumerRecord record = (ConsumerRecord) recordlist.get(i);
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                //从消息中获取Object对象
                Object object = kafkaMessage.get();
                log.info("查询到的数据:"+object);
                //System.out.println("查询到的数据:"+object);
                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                //获取连接类型
                String type = jsonObject.getString("type");
                String user_id = jsonObject.getString("id");
                String user_name = jsonObject.getString("username");

                if (type.equals("miner_connect")) {
                    Result result = new Result();
                    result.setUserId(user_id);
                    result.setUserName(user_name);
                    resultsList.add(result);
                }
            }
        }
        //System.out.println("共"+resultsList.size()+"resultsList的集合"+resultsList);
        try {
            if(resultsList.size()!=0){
                poolWorkerMapper.saveMessage(resultsList);
                log.info("存储成功!");
                long endTime = System.currentTimeMillis();
                log.info("终止时间:"+endTime);
            }else {
               log.info("暂无新数据!");
            }

        }catch (Exception e){
            log.info("数据已存在!");
        }
    }
}