package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 用户服务调用者,用于对外提供服务,与网关进行接驳
 * @作者 乐此不彼
 * @时间 2017年11月13日
 * @公司 sky
 */
@Slf4j
@SpringCloudApplication
@EnableFeignClients
public class UserConsumerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UserConsumerApplication.class, args);
		String port = context.getEnvironment().getProperty("server.port");
		String desc = context.getEnvironment().getProperty("spring.application.desc");
		log.info(desc + "服务启动成功,端口号为:" + port);
	}
}
