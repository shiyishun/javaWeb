package com.shi.service;

import java.util.Date;
import java.util.List;

import com.shi.entity.CallTheroll;

public interface CallTherollService {

	public void save(CallTheroll callTheroll);
	
	public void update(CallTheroll callTheroll);
	
	public List<CallTheroll> findByCouserIdAndDate(String courseId, Date startCallDate, Date endCallDate);
}
