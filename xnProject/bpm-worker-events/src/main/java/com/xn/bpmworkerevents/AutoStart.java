package com.xn.bpmworkerevents;


import com.xn.bpmworkerevents.config.Configs;
import com.xn.bpmworkerevents.config.SpringUtils;
import com.xn.bpmworkerevents.controller.ConsumerGroup;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * FileName:       Comsumer
 * Author:         Administrator
 * Date:           2019/4/22 13:17
 * Description:
 */
@Component
public class AutoStart implements CommandLineRunner {
    private Configs configs;

    public AutoStart(Configs configs) {
        this.configs = configs;
    }


    @Override
    public void run(String... args) throws Exception {
        //开启10个消费者线程
        ConsumerGroup consumerGroup = new ConsumerGroup(10,configs);
        //执行线程方法
        consumerGroup.execute();
    }

}
