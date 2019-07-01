package com.xn.find_xn_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.xn.find_xn_user.dao")
@SpringBootApplication
public class FindXnUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindXnUserApplication.class, args);
	}
}
