package com.xn.bmpos.manager.bmposmanager.common;

import java.util.ArrayList;
import java.util.List;

import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置黑白名单
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer{

	public static String StaticPath;

	@Value("${config.extPath}")
	public void setExtPath(String extPath) {
		//static是我文件下创建的一个文件夹
		StaticPath = extPath + System.getProperty("file.separator") + "static/";
	}

	@Autowired
	AccountAPI accountAPI;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 黑名单
		List<String> pathPatterns = new ArrayList<>();
		pathPatterns.add("/api/**");
		//pathPatterns.add("/web/**");
		//pathPatterns.add("/address/**");
		//pathPatterns.add("/cart/**");
		//pathPatterns.add("/order/**");
		// 白名单
		List<String> excludePathPatterns = new ArrayList<>();
		/*excludePathPatterns.add("/api/**");*/
		excludePathPatterns.add("/api/collection");
		excludePathPatterns.add("/api/banlanceshareByOne");
		excludePathPatterns.add("/api/pay");
		excludePathPatterns.add("/api/curve");
		excludePathPatterns.add("/api/get_info");
		//excludePathPatterns.add("/api/pool");
		excludePathPatterns.add("/api/get_one");
		excludePathPatterns.add("/api/getAllCoin");
		excludePathPatterns.add("/api/index");
		excludePathPatterns.add("/api/price");
		excludePathPatterns.add("/api/poolStart");
		excludePathPatterns.add("/api/get_pool_hashrate");
		excludePathPatterns.add("/api/get_now");
		excludePathPatterns.add("/api/getAllCoin");
		excludePathPatterns.add("/api/login");
		excludePathPatterns.add("/api/tokenVerify");
		excludePathPatterns.add("/api/subscribe");


		// 将黑白名单注册
		registry
			.addInterceptor(new LoginInterceptor(accountAPI))
			.addPathPatterns(pathPatterns)
			.excludePathPatterns(excludePathPatterns);
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//创建 显示
		registry.addResourceHandler("/extStatic/**").addResourceLocations("file:" + StaticPath);
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

}
