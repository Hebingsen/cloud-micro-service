package com.sky.auth.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sky.utils.MD5;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义md5加密
 * 
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Slf4j
public class Md5PasswordEncoder implements PasswordEncoder {

	/**
	 * 如何加密
	 * 
	 * @param rawPassword
	 * @return
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		String encodePassword = MD5.encode(rawPassword.toString());
		log.info("md5加密前的密码 = {},加密后 = {}", rawPassword, encodePassword);
		return encodePassword;
	}

	/**
	 * 如何匹配
	 * 
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		log.info("当前Spring security 加密的密码 = {}", encodedPassword);
		log.info("请求参数密码,加密前={},加密后={}", rawPassword, MD5.encode(rawPassword.toString()));
		return MD5.encode(rawPassword.toString()).equals(encodedPassword);
	}

}
