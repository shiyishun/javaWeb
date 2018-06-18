package com.shi.service;

import java.util.List;

import com.shi.common.Page;
import com.shi.entity.Course;

public interface CourseService {
   
	public void addClassInfo(Course course);
	
	public Course getById(String courseId);
	
	public Page<Course> getPage(String userId, String param, int pageNo, int pageSize);
	
	public void saveOrUpdate(Course course);
	
	public void update(Course course);
	
	public List<Course> findByUserId(String userId);
}
