package com.shi.dao;

import com.shi.entity.User;

public interface UserMngDao extends BaseDao<User, String>{

	public User findByLoginName(String loginName);
}
