package com.xn.bpmuserlistapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xn.bpmuserlistapi.mapper.TUserMapper")
public class BpmUserlistApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpmUserlistApiApplication.class, args);
	}

}
