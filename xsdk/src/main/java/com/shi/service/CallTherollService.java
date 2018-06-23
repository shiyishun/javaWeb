package com.shi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shi.common.Page;
import com.shi.entity.CallTheroll;

public interface CallTherollService {

	public void save(CallTheroll callTheroll);

	public void update(CallTheroll callTheroll);

	public List<CallTheroll> findByCouserIdAndDate(String courseId,
			Date startCallDate, Date endCallDate);

	public Page<CallTheroll> getStatisticsPage(String param, String userId, int pageNo, int pageSize);
	
	
	public Page<CallTheroll> getPage(String courseId, String courseTimeId, String  callOrder,
			String callState,String param, int pageNo, int pageSize);
	
	public List<CallTheroll> findByCouserIdAnd2(String courseId,
			String courseTimeId, Integer callOrder);
	
	public List<Map<String, Object>> findListMap(String courseId,
			String courseTimeId, String callOrder);
	
	public List<Map<String, Object>> getStatisticsList(String userId);
	
	public CallTheroll getById(String callTherollId);
	
}
