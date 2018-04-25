package com.shi.service.impl;

import java.io.Serializable;

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
}
