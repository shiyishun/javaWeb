package com.shi.service;

import java.util.Map;

import com.shi.common.Page;
import com.shi.entity.Course;

public interface CourseMngservice {
	public Page<Course> getCoursePage(String sid, int pageNo,
			int pageSize);
	public Map<String, Object> getCourseDetail(String cid);
	public Map<String, Object> signIn(String cid, String uid, String callOrder,String callPosition,String callDate,String callState,String courseTimeId);
}
