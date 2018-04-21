package com.shi.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.Page;
import com.shi.dao.UserMngDao;
import com.shi.entity.User;
import com.shi.service.UserMngService;

@Transactional
@Service
public class UserMngServiceImpl implements UserMngService {
  
	@Autowired
	UserMngDao userMngDao;
	
    @Override
    public User find(){
    	  
    	return userMngDao.findAll().get(0);
    	
	}
    @Override
    public Serializable save(User user){
    	return userMngDao.save(user);
    }
    
    @Override
    public Page<User> getPage(String hql, Map<String, Object> params, int cunrrentPage,
			int pageSize){
    	return userMngDao.getPage(hql, params, cunrrentPage, pageSize);
    }
}
