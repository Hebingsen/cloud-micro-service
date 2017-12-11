package com.sky.base;

import org.springframework.http.HttpMethod;

/**
 * 公共常量
 * @作者 乐此不彼
 * @时间 2017年11月16日
 * @公司 sky
 */
public interface Constant {
	
	/**
	 * token过期时间(单位:秒)
	 */
	public final static long TOKEN_TIME = 3600L;

	/** cors 支持的所有方法. 原则上来说, 国内复杂的浏览器环境, 开发时只支持 get 和 post 就好了, 其他忽略 */
	public static final String[] SUPPORT_METHODS = new String[] {
			HttpMethod.HEAD.name(),

			HttpMethod.GET.name(),
			HttpMethod.POST.name(),

			HttpMethod.PUT.name(),
			HttpMethod.DELETE.name(),
			HttpMethod.OPTIONS.name(),
			HttpMethod.PATCH.name(),
			HttpMethod.TRACE.name()
	};

}
