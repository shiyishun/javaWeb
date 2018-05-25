package com.shi.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.UserRoleRelDao;
import com.shi.entity.Role;
import com.shi.entity.UserRoleRel;
import com.shi.service.UserRoleRelService;

@Transactional
@Service
public class UserRoleRelServiceImpl implements UserRoleRelService {

	@Autowired
	private UserRoleRelDao userRoleRelDao;

	@Override
	public Serializable save(UserRoleRel userRoleRel) {

		return userRoleRelDao.save(userRoleRel);
	}
	
	@Override
	public void addNew(UserRoleRel userRoleRel){
		String hql = "from UserRoleRel u" +
				" where u.user =:user and u.role =:role";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", userRoleRel.getUser());
		params.put("role", userRoleRel.getRole());
		UserRoleRel urr = userRoleRelDao.get(hql, params);
		if(urr==null){			
			userRoleRelDao.save(userRoleRel);
		}
	}
	
	@Override
	public void del(UserRoleRel userRoleRel){
		userRoleRel.getUser().getUserRoleRelSet().remove(userRoleRel);
		userRoleRel.getRole().getUserRoleRelSet().remove(userRoleRel);
		userRoleRelDao.delete(userRoleRel);
	}
}
