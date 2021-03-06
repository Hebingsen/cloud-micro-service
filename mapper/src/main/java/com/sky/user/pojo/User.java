package com.sky.user.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户表POJO
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@Data
@Accessors(chain = true)
@ToString
public class User implements Serializable{

	private static final long serialVersionUID = 4035622613913351358L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	protected String userName;
	
	protected String password;
	
	protected String phone;
	
	protected Date createTime;
	
	// 最近一次登录时间
	protected Date lastLoginTime;
	
	// 用户权限
	@Transient
	protected List<Role> roles = new ArrayList<Role>();

}
