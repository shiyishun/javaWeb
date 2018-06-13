package com.shi.service;

import java.util.List;

import com.shi.entity.UserCourseRel;

public interface UserCourseRelService {

	
	public boolean isExist(String userId, String courseId);
	
	public void add(UserCourseRel userCourseRel);
	
	public List<UserCourseRel> findByTwoId(String courseId, String courseTimeId);; 
	
}
