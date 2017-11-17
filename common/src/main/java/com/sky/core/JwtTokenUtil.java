package com.sky.core;

import java.util.ArrayList;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.sky.exception.ServiceException;
import com.sky.user.pojo.User;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.MapUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * jwt 工具类
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Component
public class JwtTokenUtil {
	
	@Autowired
	private Environment env;
	
	/**
	 * 根据token获取登录名
	 * @param token
	 * @return
	 */
	public String getUsernameFormToken(String token) {
		if(StrUtil.isBlank(token)) return null;
		String username = parser(token).get("phone", String.class);
		return username;
	}
	
	/**
	 * 根据token解析jwt内容
	 * @param token
	 * @return
	 */
	public Claims parser(String token) {
		Claims claims = Jwts.parser().setSigningKey(env.getProperty("jwt.secret")).parseClaimsJws(token).getBody();
		if(claims == null)
			throw new ServiceException("token解析异常");
		return claims;
	}
	
	/**
	 * 解析token返回用户信息 
	 * @param token
	 * @return
	 */
	public User parser2User(String token) {
		Claims claims = parser(token);
		User user = new User()
		.setId(claims.get("id", Long.class))
		.setRoles(claims.get("authorities", ArrayList.class))
		.setUserName(claims.get("username", String.class))
		.setPhone(claims.get("phone", String.class));
		return user;
	}

	/**
	 * 根据用户详情生成token
	 * @param userDetails
	 * @return
	 */
	public String generateToken(Map<String,Object> userDetails) {
		Map claims = MapUtil.builder().put("username", userDetails.get("userName"))
		.put("authorities", userDetails.get("roles"))
		.put("phone", userDetails.get("phone"))
		.put("id", userDetails.get("id"))
		.put("type", "access_token").map();
		String compact = Jwts.builder().signWith(SignatureAlgorithm.HS512, env.getProperty("jwt.secret"))
				.setClaims(claims)
				.compact();
		return compact;
	}
	
	/**
	 * 根据用户详情生成refreshToken
	 * @param userDetails
	 * @return
	 */
	public String generateRefreshToken(Map<String,Object> userDetails) {
		Map claims = MapUtil.builder().put("username", userDetails.get("userName"))
				.put("authorities", userDetails.get("roles"))
				.put("phone", userDetails.get("phone"))
				.put("id", userDetails.get("id"))
				.put("type", "refresh_token").map();
		String compact = Jwts.builder().signWith(SignatureAlgorithm.HS512, env.getProperty("jwt.secret"))
				.setClaims(userDetails)
				.compact();
		return compact;
	}
	
	/**
	 * 校验token
	 * 1.校验用户详情的用户名是否与token解析的用户名相等
	 * 2.校验token是否已经过期
	 * @param userDetails 	用户详情信息
	 * @param token			用户token
	 * @return
	 */
	public boolean validateToken(Map<String,Object> userDetails, String token) {
		String phone = (String)userDetails.get("phone");
		return phone.equals(getUsernameFormToken(token));
	}
	
	public static void main(String[] args) {
		SecretKey key = MacProvider.generateKey();
		
		String compact = Jwts.builder().signWith(SignatureAlgorithm.HS512, Base64.encode("bingsen.he")).claim("username", "何炳森").compact();
		
		String username = Jwts.parser().setSigningKey(Base64.encode("bingsen.he")).parseClaimsJws(compact).getBody().get("username", String.class);
		
		System.out.println("username="+username);
		
		System.out.println(Base64.encode("bingsen.he"));
		
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJ1c2VybmFtZSI6IuS5kOatpOS4jeW9vCJ9.7WmjCxh46p7Mh-WeguoqB_tvbRdVLv8Q-LLzf3EfZQ_zclZp"
		+ "c_yNXkl5WIebMD8TuBwAAxJIH8ZG_Lw_5CNVdg";
		Claims parser = new JwtTokenUtil().parser(token);
		System.err.println(parser);
	}
	
}
