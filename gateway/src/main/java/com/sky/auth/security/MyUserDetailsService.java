package com.sky.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sky.auth.client.AuthClient;
import com.sky.base.Code;
import com.sky.base.ResponseEntity;
import com.sky.exception.ServiceException;
import com.sky.user.pojo.User;
import com.xiaoleilu.hutool.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private AuthClient authClient;

	/**
	 * 获取用户详情信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		// 1.调用用户微服务,根据username查询数据库中的用户信息
		ResponseEntity<User> result = authClient.findAndRefreshUserInfo(username);
		log.info("调用用户微服务接口,返回结果 : " + result);
		
		if (result.getCode() != Code.SUCCESS) {
			switch (result.getCode()) {
			case Code.USER_SERVICE_FAIL:
				log.info(result.getMsg());
				throw new ServiceException(result.getCode(), result.getMsg());
			case HttpStatus.HTTP_INTERNAL_ERROR:
				log.info("服务出现故障,请稍后重试");
				throw new ServiceException(HttpStatus.HTTP_INTERNAL_ERROR, "服务出现故障,请稍后重试");
			default:
				log.info(String.format("根据用户名 %s 找不到对应的用户信息", username));
				throw new UsernameNotFoundException(String.format("根据用户名 %s 找不到对应的用户信息", username));
			}
		}

		User user = result.getData();
		SecurityUser securityUser = SecurityUserFactory.create(user);
		log.info(String.format("将由数据库查询出来的用户信息转化为securityUser = %s", securityUser.toString()));
		return securityUser;

	}

}
