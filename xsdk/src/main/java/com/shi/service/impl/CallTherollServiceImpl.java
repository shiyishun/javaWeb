package com.shi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.CallTherollDao;
import com.shi.entity.CallTheroll;
import com.shi.service.CallTherollService;


@Transactional
@Service
public class CallTherollServiceImpl implements CallTherollService {
 
	@Autowired
	private CallTherollDao callTherollDao;
	
	public void save(CallTheroll callTheRoll){
		callTherollDao.save(callTheRoll);
	}
	
	public void update(CallTheroll callTheRoll){
		callTherollDao.update(callTheRoll);
	}
}
