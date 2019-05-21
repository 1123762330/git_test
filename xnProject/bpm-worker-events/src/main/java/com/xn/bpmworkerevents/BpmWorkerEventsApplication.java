package com.xn.bpmworkerevents;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xn.bpmworkerevents.dao.PoolWorkerMapper")
public class BpmWorkerEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpmWorkerEventsApplication.class, args);
	}

}
