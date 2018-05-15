package com.shi.service;

import java.io.Serializable;
import java.util.Map;

import com.shi.common.Page;
import com.shi.entity.User;

public interface UserMngService {
 
	public User find();
	
	public Serializable save(User user);
	
	public Page<User> getPage(String param, int pageNo,
			int pageSize);
	
	public Page<User> getSqlPage(String param, int pageNo,
			int pageSize);
	
	public User findByLoginName(String loginName);
	
	public void update(User user);
	
	public Map<String, Object> getUserInfo(String userId);
}
