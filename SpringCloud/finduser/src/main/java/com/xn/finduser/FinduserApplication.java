package com.xn.finduser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xn.finduser.dao")
public class FinduserApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinduserApplication.class, args);
	}

}
