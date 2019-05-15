package com.xn.bmpos.api.bmposapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 矿池api 服务
 */
@Configuration
@EnableFeignClients //Feign
@SpringBootApplication
@MapperScan("com.xn.bmpos.api.bmposapi.domain.mapper")
public class BmposApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmposApiApplication.class, args);
    }

}
