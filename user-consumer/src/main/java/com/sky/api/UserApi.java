package com.sky.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.sky.annotation.RestfulApi;
import com.sky.base.ResponseEntity;
import com.sky.client.UserClient;
import com.sky.user.pojo.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户对外服务控制层
 * 
 * @作者 乐此不彼
 * @时间 2017年11月13日
 * @公司 sky
 */
@RestfulApi("/api/user")
@Slf4j
public class UserApi {

	@Autowired
	private UserClient userClient;

	/**
	 * 获取当前登录信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/login-info")
	public ResponseEntity<User> loginInfo(@RequestHeader("token") String token) {
		return userClient.getUserLoginInfo(token);
	}

	@GetMapping("/needAuth")
	// @PreAuthorize("hasRole('ADMIN')")
	public Object needAuth() {
		log.info("访问需要拥有角色ADMIN的对外服务");
		return "该请求需要鉴权";
	}

	@GetMapping("/notNeedAuth")
	// @PreAuthorize("hasRole('USER')")
	public Object notNeedAuth() {
		log.info("访问需要拥有角色USER的对外服务");
		return "该请求不需要鉴权";
	}

	@GetMapping("/hello")
	public Object hello() {
		log.info("访问用户不需要鉴权的对外服务");
		return "hello world";
	}
}
