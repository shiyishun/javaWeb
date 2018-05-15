package com.shi.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.RoleMngDao;
import com.shi.entity.Role;
import com.shi.service.RoleMngService;

@Transactional
@Service
public class RoleMngServiceImpl implements RoleMngService {

	@Autowired
	private RoleMngDao roleMngDao;
	
	@Override
	public Serializable save(Role role){
		
		return roleMngDao.save(role);
	}
	
	@Override
	public List<Role> findExceptAdm(){
		String hql = " from Role where roleId!='0'";
		return roleMngDao.findList(hql);
	}
	
	@Override
	public List<Role> findAll(){
		
		return roleMngDao.findAll();
	}
}
