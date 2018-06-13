package com.shi.service;

import java.util.List;

import com.shi.entity.CourseTimeRel;

public interface CourseTimeRelService {

	public void del(CourseTimeRel courseTimeRel);
	
	public CourseTimeRel findByCourseTimeId(String courseTimeId);
	
	public void add(CourseTimeRel courseTimeRel);
}
