package com.sky.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.sky.auth.security.MyUserDetailsService;
import com.sky.auth.security.SecurityUser;
import com.sky.auth.service.AuthService;
import com.sky.base.Constant;
import com.sky.base.Message;
import com.sky.base.ResponseEntity;
import com.sky.core.JwtTokenUtil;
import com.sky.core.SecurityUserUtil;
import com.sky.exception.ServiceException;
import com.sky.redis.RedisUtil;
import com.sky.user.pojo.User;
import com.sky.utils.ConvertUtil;
import com.sky.utils.U;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户鉴权service
 * 
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class AuthServiceImpl implements AuthService {

	/**
	 * 认证管理器
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private RedisUtil redis;

	@Autowired
	private SecurityUserUtil securityUserUtil;

	@Override
	public ResponseEntity<String> login(String phone, String password) {

		try {
			// 根据用户名与密码生成token认证
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone,
					password);

			// 将当前认证管理设置为token认证,返回获取到一个新的认证器
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			// 设置为当前认证
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// 获取用户详情信息
			SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(phone);

			return ResponseEntity.success(Message.LOGIN_SUCCESS,
					jwtTokenUtil.generateToken(ConvertUtil.toMap(userDetails)));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.fail(Message.LOGIN_FAIL + ":" + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> refresh(String refreshToken) {

		// 1.无效refreshToken->返回错误响应结果
		U.assertException(!redis.exists(refreshToken), Message.INVALID_TOKEN);

		// 1.有效->解析refreshToken
		Claims claims = jwtTokenUtil.parser(refreshToken);
		String password = claims.get("password", String.class);
		String phone = claims.get("phone", String.class);

		// 3.调用登录的方法重新生成新的token
		ResponseEntity<String> loginResult = login(phone, password);
		if (loginResult.getCode() != 1)
			return ResponseEntity.fail(Message.REFRESH_FAIL);

		String newToken = loginResult.getData();
		User user = jwtTokenUtil.parser2User(newToken);
		securityUserUtil.storeToken(newToken, user, Constant.TOKEN_TIME);
		log.info("刷新后的token = {}", newToken);

		return ResponseEntity.success(Message.REFRESH_SUCCESS, newToken);
	}

	@Override
	public Claims parser(String token) {
		return jwtTokenUtil.parser(token);
	}

}
