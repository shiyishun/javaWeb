package com.shi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.MarkDao;
import com.shi.entity.Mark;
import com.shi.service.MarkService;


@Transactional
@Service
public class MarkServiceImpl implements MarkService {
 
	@Autowired
	private MarkDao markDao;
	
	public void save(Mark mark){
		markDao.save(mark);
	}
	
	public void update(Mark mark){
		markDao.update(mark);
	}
	
	public void genMarkByCourseId(String courseId) throws Exception{
		
		String sql = "INSERT tb_mark(mark_id, course_id, course_name, user_id, stu_name)"+ 
		" select a.id, a.course_id, a.course_name , a.user_id, a.name from"+
		" (select REPLACE(UUID(), '-', '') id ,c.course_id , c.course_name, u.user_id , ts.name}"+ 
		" from tb_user_course_rel ucr"+ 
		" LEFT JOIN tb_course c on ucr.course_id = c.course_id"+ 
		" LEFT JOIN tb_user u on u.user_id=ucr.user_id ," +
		" tb_teach_stu ts where u.user_id=ts.user_id and ts.is_teacher=1"+ 
		" and ucr.course_id='"+ courseId+ "' ) a"+
		" where not EXISTS( select 1 from tb_mark m " +
		" where m.user_id=a.user_id and m.course_id = a.course_id)";

	    markDao.executeBySql(sql);
		
		
		
	}
}
