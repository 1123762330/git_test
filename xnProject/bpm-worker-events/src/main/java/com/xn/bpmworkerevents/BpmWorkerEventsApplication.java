package com.xn.bpmworkerevents;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.xn.bpmworkerevents.dao")
public class BpmWorkerEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpmWorkerEventsApplication.class, args);
	}

}
