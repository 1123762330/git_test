package com.spring.xnpool.bmpos.tasks.bmpostasks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
@EnableAsync//多线程
@MapperScan({"com.spring.xnpool.bmpos.tasks.bmpostasks.mapper","com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper"})
@SpringBootApplication
@EnableScheduling//定时任务开关
@EnableWebSocketMessageBroker//日志
@EnableFeignClients //Feign
public class BmposTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmposTasksApplication.class, args);
	}

}
