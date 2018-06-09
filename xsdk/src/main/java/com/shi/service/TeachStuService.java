package com.shi.service;

import java.util.List;

import com.shi.common.Page;
import com.shi.entity.TeachStu;

public interface TeachStuService {
 
	
	public Page<TeachStu> studentPage(String schoolInfoId, String classNo, String grade, String param,
			  int pageNo, int pageSize);
}
