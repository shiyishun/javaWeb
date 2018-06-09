package com.shi.service;

import com.shi.entity.Mark;

public interface MarkService {
  
	
	public void save(Mark mark);
	
	public void update(Mark mark);
	
	public void genMarkByCourseId(String courseId) throws Exception;
}
