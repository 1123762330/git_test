package com.xn.bmpos.manager.bmposmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //Feign
@SpringBootApplication
@MapperScan("com.xn.bmpos.manager.bmposmanager")
public class BmposManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmposManagerApplication.class, args);
    }




}
