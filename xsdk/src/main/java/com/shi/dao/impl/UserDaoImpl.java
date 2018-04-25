package com.shi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.shi.dao.UserMngDao;
import com.shi.entity.User;


@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserMngDao{

	public User findByLoginName(String loginName){
		String hql = " from User u where u.userName = :userName or u.email = :email " +
				"or u.phone = :phone";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userName", loginName);
		paramsMap.put("email", loginName);
		paramsMap.put("phone", loginName);
		return this.get(hql, paramsMap);
	}
}
