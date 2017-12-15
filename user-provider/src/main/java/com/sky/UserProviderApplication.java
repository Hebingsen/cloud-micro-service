package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务调用者,用于对外提供服务,与网关进行接驳
 * @作者 乐此不彼
 * @时间 2017年11月13日
 * @公司 sky
 */
@Slf4j
@SpringBootApplication
@EnableEurekaClient
public class UserProviderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UserProviderApplication.class, args);
		String port = context.getEnvironment().getProperty("server.port");
		String desc = context.getEnvironment().getProperty("spring.application.desc");
		log.info(desc + "服务启动成功,端口号为:" + port);
	}
}
