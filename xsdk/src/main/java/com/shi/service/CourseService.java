package com.shi.service;

import com.shi.entity.Course;

public interface CourseService {
   
	public void addClassInfo(Course course);
	
	public Course getById(String courseId);
}
