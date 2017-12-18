package com.sky.auth.api;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import com.sky.annotation.RestfulApi;
import com.sky.auth.client.AuthClient;
import com.sky.auth.service.AuthService;
import com.sky.base.Code;
import com.sky.base.Constant;
import com.sky.base.Message;
import com.sky.base.ResponseEntity;
import com.sky.core.SecurityUserUtil;
import com.sky.user.pojo.User;
import com.sky.utils.MD5;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * @作者 乐此不彼
 * @时间 2017年10月15日
 * @公司 sky工作室
 */
@Slf4j
@RestfulApi("/auth")
@SuppressWarnings("all")
public class AuthApi {

	@Autowired
	private AuthClient userClient;

	@Autowired
	private AuthService authService;

	@Autowired
	private SecurityUserUtil securityUserUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@PostMapping("/login")
	public ResponseEntity login(@RequestParam String phone, @RequestParam String password) {

		// 1.生成token
		ResponseEntity<String> loginResult = authService.login(phone, MD5.encode(password));
		if (loginResult.getCode() != Code.SUCCESS) {
			return loginResult;
		}

		String token = loginResult.getData();
		log.info("用户登录成功,生成token={}", token);

		// 2.根据手机号获取用户信息--->使用feign调用用户微服务
		ResponseEntity<User> userServiceResult = userClient.findAndRefreshUserInfo(phone);
		if (userServiceResult.getCode() != Code.SUCCESS) {
			return userServiceResult;
		}

		// 3.将成功生成后的token存储进去redis中进行管理
		User user = userServiceResult.getData();
		securityUserUtil.storeToken(token, user, Constant.TOKEN_TIME);

		return ResponseEntity.success(Message.LOGIN_SUCCESS, token);
	}

	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		boolean result = securityUserUtil.clearToken(request.getHeader(tokenHeader));
		if (result)
			return ResponseEntity.success(Message.LOGOUT_SUCCESS);
		else
			return ResponseEntity.fail(Message.LOGOUT_FAIL);
	}

	@PostMapping("/refresh")
	public ResponseEntity refresh(@RequestHeader(name = "refreshToken") String refreshToken) {
		return authService.refresh(refreshToken);
	}

	@PostMapping("/parse")
	public ResponseEntity parseToken(@RequestParam String token) {
		return ResponseEntity.success(Message.PARSE_SUCCESS, authService.parser(token));
	}

}
