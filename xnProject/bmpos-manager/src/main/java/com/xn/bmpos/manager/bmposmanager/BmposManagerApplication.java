package com.xn.bmpos.manager.bmposmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients //Feign
@SpringBootApplication
@MapperScan("com.xn.bmpos.manager.bmposmanager")
@EnableScheduling
public class BmposManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmposManagerApplication.class, args);
    }


}
