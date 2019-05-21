package com.xn.console.bmposconsole;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //Feign
@MapperScan("com.xn.console.bmposconsole")
@SpringBootApplication
public class BmposConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmposConsoleApplication.class, args);
    }

}
