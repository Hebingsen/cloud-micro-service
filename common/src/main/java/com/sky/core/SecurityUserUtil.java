package com.sky.core;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sky.base.Constant;
import com.sky.base.JwtConfig;
import com.sky.core.JwtTokenUtil;
import com.sky.redis.RedisUtil;
import com.sky.user.pojo.User;
import com.sky.utils.ConvertUtil;
import com.sky.utils.U;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @作者 乐此不彼
 * @时间 2017年10月20日
 * @公司 sky工作室
 */
@Component
@Slf4j
public class SecurityUserUtil {

	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtConfig jwt;
	
	/**
	 * store refresh token
	 * @param user
	 */
	public void storeRefreshToken(User user) {
		Map<String, Object> userDetails = ConvertUtil.toMap(user);
		String refreshToken = jwtTokenUtil.generateToken(userDetails);
		redis.set("", refreshToken);
	}
	
	/**
	 * store token
	 * @param token
	 * @param obj
	 * @param time
	 * @return
	 */
	public boolean storeToken(String token,User user,Long time) {
		try {
			redis.set(token, user, Constant.TOKEN_TIME);
			storeRefreshToken(user);
			return true;
		} catch (Exception e) {
			log.error("store token fail,token={}",token);
			return false;
		}
	}

	/**
	 * clear token
	 */
	public boolean clearToken(String token) {

		try {
			log.info(" clear token = {} ", token);

			token = dealToken(token);
			
			if (redis.exists(token))
				redis.remove(token);
			else
				log.info("token is not exists");

			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

	}

	/**
	 * 处理token
	 * 
	 * @param token
	 * @return
	 */
	public String dealToken(String token) {
		String tokenPrefix = jwt.getPrefix();
		return token.substring(tokenPrefix.length(), token.length());
	}

	/**
	 * 根据token获取当前用户信息
	 * 
	 * @param token
	 * @return
	 */
	public User getUser(String token) {

		U.assertException(StrUtil.isBlank(token), "需要解析的令牌为空");
		
		token = dealToken(token);

		U.assertException(!redis.exists(token), "用户登录信息不存在");

		User user = (User) redis.get(token);
		U.assertException(user == null, "用户登录信息为空");

		return user;
	}

	/**
	 * 获取用户id
	 * 
	 * @param token
	 * @return
	 */
	public Long getUserId(String token) {
		return getUser(token).getId();
	}

	/**
	 * 获取用户名
	 * 
	 * @param token
	 * @return
	 */
	public String getUserName(String token) {
		return getUser(token).getUserName();
	}

	/**
	 * 获取用户名
	 * 
	 * @param token
	 * @return
	 */
	public String getUserPhone(String token) {
		return getUser(token).getPhone();
	}

}
