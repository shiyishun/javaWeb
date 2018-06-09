package com.shi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void save(CallTheroll callTheroll){
		callTherollDao.save(callTheroll);
	}
	
	public void update(CallTheroll callTheroll){
		callTherollDao.update(callTheroll);
	}
	
	public List<CallTheroll> findByCouserIdAndDate(String courseId, Date startCallDate, Date endCallDate){
		
		String hql = "from CallTheroll c where c.course.courseId=:courseId " +
				"and c.callDate >=:startCallDate and c.callDate <=:endCallDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("courseId", courseId);
		params.put("startCallDate", startCallDate);	
		params.put("endCallDate",endCallDate);	
		return callTherollDao.findList(hql, params);
	}
}
