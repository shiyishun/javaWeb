package com.shi.service;

import java.util.Date;
import java.util.List;

import com.shi.common.Page;
import com.shi.entity.CallTheroll;

public interface CallTherollService {

	public void save(CallTheroll callTheroll);

	public void update(CallTheroll callTheroll);

	public List<CallTheroll> findByCouserIdAndDate(String courseId,
			Date startCallDate, Date endCallDate);

	public Page<CallTheroll> getStaticsPage(String param, String userId, int pageNo, int pageSize);
	
	
	public Page<CallTheroll> getPage(String courseId, String courseTimeId, String  callOrder,
			String callState,String param, int pageNo, int pageSize);
}
