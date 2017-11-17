package com.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 注册中心
 * @作者 乐此不彼
 * @时间 2017年11月13日
 * @公司 sky
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EurekaServerApplication.class, args);
		String port = context.getEnvironment().getProperty("server.port");
		String desc = context.getEnvironment().getProperty("spring.application.desc");
		System.err.println(desc + "服务启动成功,端口号为:" + port);
	}
}
