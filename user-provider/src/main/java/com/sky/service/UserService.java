package com.sky.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.user.mapper.RoleMapper;
import com.sky.user.mapper.UserMapper;
import com.sky.user.mapper.UserRoleMapper;
import com.sky.user.pojo.Role;
import com.sky.user.pojo.User;
import com.sky.user.pojo.UserRole;
import com.xiaoleilu.hutool.date.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户模块service层
 * 
 * @作者 乐此不彼
 * @时间 2017年11月15日
 * @公司 sky
 */
@Service
@Slf4j
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 1.查询用户信息 2.刷新登录时间 3.封装用户角色信息
	 * 
	 * @param phone
	 * @return
	 */
	@Transactional
	public User findAndRefreshUserInfo(String phone) {

		//1.查询用户信息
		User user = userMapper.selectOne(new User().setPhone(phone));
		
		if (user == null) 
			return null;
			
		//2.刷新登录时间
		user.setLastLoginTime(DateUtil.date());
		int updateResult = userMapper.updateByPrimaryKey(user);
		log.info("更新用户登录时间结果返回:"+updateResult);
		
		//3.封装用户角色信息
		List<UserRole> userRoles = userRoleMapper.select(new UserRole().setUserId(user.getId()));
		if(!userRoles.isEmpty()) {
			List<Role> roles = new ArrayList<Role>();	
			for (UserRole userRole : userRoles) {
				Long roleId = userRole.getRoleId();
				Role role = roleMapper.selectOne(new Role().setId(roleId));
				roles.add(role);
			}
			user.setRoles(roles);
		}
		
		log.info("返回用户信息 = {}",user.toString());
		
		return user;
	}
}
