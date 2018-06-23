package com.shi.service;

import java.util.List;
import java.util.Map;

import com.shi.common.Page;
import com.shi.entity.Course;

public interface CourseMngservice {
	public Page<Course> getCoursePage(String sid, int pageNo,
			int pageSize);
	public Map<String, Object> getCourseDetail(String cid);
	public List<Map<String, Object>> getScoreList(String sid);
	public List<Map<String, Object>> getRecordList(String sid,String cs);
	public List<Map<String, Object>> getCourseList(String sid);
	public List<Map<String, Object>> getAllCourseList(String sid);
	public Map<String, Object> signIn(String cid, String uid,String callPosition,String callState,String courseTimeId);
}
