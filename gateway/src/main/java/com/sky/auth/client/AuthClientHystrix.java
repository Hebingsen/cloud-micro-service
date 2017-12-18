package com.sky.auth.client;

import org.springframework.stereotype.Component;

import com.sky.base.Code;
import com.sky.base.ResponseEntity;
import com.sky.user.pojo.User;

/**
 * 用户服务熔断处理类
 * @作者 乐此不彼
 * @时间 2017年11月15日
 * @公司 sky
 */
@Component
@SuppressWarnings("unchecked")
public class AuthClientHystrix implements AuthClient{

	@Override
	public ResponseEntity<User> findAndRefreshUserInfo(String phone) {
		return ResponseEntity.fail(Code.USER_SERVICE_FAIL,"用户微服务调用失败");
	}

}
