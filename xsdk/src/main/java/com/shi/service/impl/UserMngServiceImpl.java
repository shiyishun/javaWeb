package com.shi.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.UserMngDao;
import com.shi.entity.User;
import com.shi.service.UserMngService;

@Transactional
@Service
public class UserMngServiceImpl implements UserMngService {
  
	@Autowired
	UserMngDao useMngDao;
	
    @Override
    public User find(){
    	 
    
    	return useMngDao.findAll().get(0);
    	
	}
    @Override
    public Serializable save(User user){
    	return useMngDao.save(user);
    }
    
}
