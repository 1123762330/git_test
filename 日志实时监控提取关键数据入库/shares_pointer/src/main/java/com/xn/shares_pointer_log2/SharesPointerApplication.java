package com.xn.shares_pointer_log2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SharesPointerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharesPointerApplication.class, args);
	}

}
