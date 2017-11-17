package com.sky.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sky.user.pojo.Role;

import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
