package com.sky.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sky.base.ResponseEntity;
import com.sky.hystrix.UserClientHystrix;
import com.sky.user.pojo.User;

/**
 * 用户服务的fegin客户端
 * 
 * @作者 乐此不彼
 * @时间 2017年11月14日
 * @公司 sky
 */
@FeignClient(value = "user-provider", fallback = UserClientHystrix.class)
public interface UserClient {

	@GetMapping("/user/login-info")
	ResponseEntity getUserLoginInfo(@RequestParam("token") String token);

}
