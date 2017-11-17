package com.sky.hystrix;

import org.springframework.stereotype.Component;

import com.sky.base.Code;
import com.sky.base.ResponseEntity;
import com.sky.client.UserClient;
import com.sky.user.pojo.User;

/**
 * 用户服务熔断处理类
 * @作者 乐此不彼
 * @时间 2017年11月16日
 * @公司 sky
 */
@Component
@SuppressWarnings("unchecked")
public class UserClientHystrix implements UserClient{

	@Override
	public ResponseEntity<User> getUserLoginInfo(String token) {
		return ResponseEntity.fail(Code.USER_SERVICE_FAIL,"用户登录信息获取失败");
	}

}
