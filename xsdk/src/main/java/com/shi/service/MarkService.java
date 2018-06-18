package com.shi.service;



import java.util.List;
import java.util.Map;

import com.shi.common.Page;
import com.shi.entity.Mark;
import com.shi.entity.TeachStu;

public interface MarkService {
  
	
	public void save(Mark mark);
	
	public void update(Mark mark);
	
	public void genMarkByCourseId(String courseId) throws Exception;
	
	public Mark getById(String markId);
	
	public Page<Mark> getPage(String schoolInfoId, String classNo, String grade, String param,
			 String courseId, int pageNo, int pageSize);
	
	public List<Map<String, Object>> findByCourseId2(String courseId);
	
	
	public Map<String, Object> getByIdHql(String markId);
}
