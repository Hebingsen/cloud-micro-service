package com.sky.base;

/**
 * 响应信息常量定义
 * @作者 乐此不彼
 * @时间 2017年11月16日
 * @公司 sky
 */
public interface Message {

	/**
	 * 登录成功
	 */
	public final static String LOGIN_SUCCESS = "用户登录成功";
	
	/**
	 * 登录失败
	 */
	public final static String LOGIN_FAIL = "用户登录失败";
	
	/**
	 * 空信息
	 */
	public final static String EMPTY = "";
	
	/**
	 * 退出成功
	 */
	public final static String LOGOUT_SUCCESS = "用户退出成功";
	
	/**
	 * 退出失败
	 */
	public final static String LOGOUT_FAIL = "用户退出失败";
	
	/**
	 * token 解析成功
	 */
	public final static String PARSE_SUCCESS = "解析成功";
	
	/**
	 * token 解析失败
	 */
	public final static String PARSE_FAIL = "解析失败";
	
	/**
	 * token 刷新成功
	 */
	public final static String REFRESH_SUCCESS = "刷新成功";
	
	/**
	 * token 刷新失败
	 */
	public final static String REFRESH_FAIL = "刷新失败";
	
	/**
	 * 无效 token
	 */
	public final static String INVALID_TOKEN = "无效token";
}
