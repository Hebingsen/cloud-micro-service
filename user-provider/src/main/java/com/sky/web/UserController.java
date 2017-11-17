package com.sky.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.sky.annotation.RestfulApi;
import com.sky.base.ResponseEntity;
import com.sky.core.SecurityUserUtil;
import com.sky.service.UserService;
import com.sky.user.pojo.User;
import com.sky.user.respone.UserResp;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户模块controller层
 * 
 * @作者 乐此不彼
 * @时间 2017年11月15日
 * @公司 sky
 */
@RestfulApi("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityUserUtil securityUserUtil;

	@GetMapping("/info/byphone")
	public Object findByPhone(String phone) {
		
		log.info("接收参数phone={}", phone);
		
		User user = userService.findAndRefreshUserInfo(phone);
		if (user == null)
			return ResponseEntity.fail("用户信息不存在");
		
		return ResponseEntity.success("用户信息查询成功", user);
	}
	
	
	@GetMapping("/login-info")
	public ResponseEntity getUserLoginInfo(String token) {
		UserResp userResp = new UserResp();
		User user = securityUserUtil.getUser(token);
		BeanUtils.copyProperties(user, userResp);
		return ResponseEntity.success("查询用户登录信息", userResp);
	}
}
