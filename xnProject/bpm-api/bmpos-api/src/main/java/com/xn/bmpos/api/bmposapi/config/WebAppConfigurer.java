package com.xn.bmpos.api.bmposapi.config;


import com.xn.bmpos.api.bmposapi.interceptor.LogCostInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
	@Bean
	public static HandlerInterceptor getHandlerInterceptor() {
		return new LogCostInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 黑名单
		List<String> pathPatterns
				= new ArrayList<>();
		pathPatterns.add("/user/**");
		// 白名单
		List<String> excludePathPatterns
				= new ArrayList<>();
		excludePathPatterns.add("login");
		// 注册
		registry
				.addInterceptor(getHandlerInterceptor())
				.addPathPatterns(pathPatterns)
				.excludePathPatterns(excludePathPatterns);
	}
}
