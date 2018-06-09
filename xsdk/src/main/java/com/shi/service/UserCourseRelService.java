package com.shi.service;

import com.shi.entity.UserCourseRel;

public interface UserCourseRelService {

	
	public boolean isExist(String userId, String courseId);
	
	public void add(UserCourseRel userCourseRel);
}
