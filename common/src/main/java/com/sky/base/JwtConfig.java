package com.sky.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * jwt配置类
 * 
 * @作者 乐此不彼
 * @时间 2017年11月17日
 * @公司 sky
 */
@Data
@ToString
@Accessors(chain = true)
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

	// 有效期(单位:秒) 30*60=1800
	private Long expiration;

	// #秘钥bingsen.he的base64编码
	private String secret;

	// jwt token 前缀:持票人,可以自定义
	private String prefix;

	// jwt请求头
	private String header;

}
