package com.shi.service;

import java.io.Serializable;
import java.util.Map;

import com.shi.common.Page;
import com.shi.entity.User;

public interface UserMngService {
 
	public User find();
	
	public Serializable save(User user);
	
	public Page<User> getPage(String hql, Map<String, Object> params, int cunrrentPage,
			int pageSize);
	
	public User findByLoginName(String loginName);
}
