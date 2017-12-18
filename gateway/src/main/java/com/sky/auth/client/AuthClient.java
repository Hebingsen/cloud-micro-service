package com.sky.auth.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sky.base.ResponseEntity;
import com.sky.user.pojo.User;

/**
 * 用户微服务调用客户端
 * @作者 乐此不彼
 * @时间 2017年11月15日
 * @公司 sky
 */
@FeignClient(value = "user-provider", fallback = AuthClientHystrix.class)
public interface AuthClient {

	/**
	 * 调用用户微服务,根据手机号查询用户信息,并且刷新最近登录时间与封装了用户的角色信息
	 * 
	 * @param phone
	 * @return
	 */
	@GetMapping("/user/info/byphone")
	ResponseEntity<User> findAndRefreshUserInfo(@RequestParam("phone") String phone);

}
