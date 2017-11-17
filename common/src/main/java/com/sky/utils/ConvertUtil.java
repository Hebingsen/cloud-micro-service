package com.sky.utils;

import java.util.Map;

import com.google.gson.Gson;

/**
 * 
 * @作者 乐此不彼
 * @时间 2017年11月16日
 * @公司 sky
 */
public class ConvertUtil {
	
	/**
	 * 字符串转为map
	 * @param userdetails
	 * @return
	 */
	public static Map<String,Object> toMap(Object obj) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		Map map = gson.fromJson(json, Map.class);
		return map;
	}
}
