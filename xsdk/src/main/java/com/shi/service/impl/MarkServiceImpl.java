package com.shi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.Page;
import com.shi.dao.MarkDao;
import com.shi.entity.Mark;
import com.shi.entity.TeachStu;
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
	
	public Mark getById(String markId){
		return markDao.getById(markId);
	}
	
	public Map<String, Object> getByIdHql(String markId){
		String hql = "select m.markId as markId, m.user.teachStu.name as name, " +
				" m.user.teachStu.no as no, m.course.courseName as courseName, " +
				" m.dailyScore as dailyScore, m.finalScore as finalScore " +
				" from Mark m where m.markId='"+markId+"'";
		
		return markDao.getMap2(hql, null);
	}
	
	
	
	public void genMarkByCourseId(String courseId) throws Exception{
		
		String sql = "INSERT tb_mark(mark_id, course_id, user_id)"+ 
		" select REPLACE(UUID(), '-', ''), a.course_id, a.user_id from"+
		" (select distinct c.course_id , u.user_id"+ 
		" from tb_user_course_rel ucr"+ 
		" LEFT JOIN tb_course c on ucr.course_id = c.course_id"+ 
		" LEFT JOIN tb_user u on u.user_id=ucr.user_id ," +
		" tb_teach_stu ts where u.user_id=ts.user_id and ts.is_teacher=1"+ 
		" and ucr.course_id='"+ courseId+ "' ) a"+
		" where not EXISTS( select 1 from tb_mark m " +
		" where m.user_id=a.user_id and m.course_id = a.course_id)";

	    markDao.executeBySql(sql);
	
	}
	
	
	public Page<Mark> getPage(String schoolInfoId, String classNo, String grade, String param,
			 String courseId, int pageNo, int pageSize){
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("select t.markId, " +
				" t.user.teachStu.no, t.user.teachStu.name, t.user.teachStu.gender, " +
				" t.user.teachStu.grade, t.user.teachStu.major, t.user.teachStu.classNo, " +
		        " t.dailyScore, t.examScore, t.finalScore "+
				" from Mark t where  t.user.teachStu.isTeacher=1 and " +
				"(t.user.status=0 or t.user.status=1) and  t.course.courseId ='"+courseId+"'");
		if(schoolInfoId!=null&&schoolInfoId.trim().equals("")){
			hql.append(" and t.user.teachStu.schoolInfo.schoolInfoId =:schoolInfoId");
			params.put("schoolInfoId", schoolInfoId);
		}
		if(classNo!=null&&!classNo.trim().equals("")){
			hql.append(" and t.user.teachStu.classNo =:classNo");
			params.put("classNo", classNo);
		}
		if(grade!=null&&!grade.trim().equals("")){
			hql.append(" and t.user.teachStu.grade =:grade");
			params.put("grade", grade);
		}
		
		if(param!=null&&!param.trim().equals("")){
			hql.append(" and (t.user.teachStu.no like:no or t.user.teachStu.name like:name)");
			params.put("no", "%"+param+"%");
			params.put("name", "%"+param+"%");
		}

		
		
		Page<Mark> page = markDao.getPage(hql.toString(), params, pageNo, pageSize);
        return page;
		
	}
	
	
	public List<Map<String, Object>> findByCourseId2(String courseId){
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("select  m.markId as markId, " +
				"m.user.teachStu.no as no," +
				" m.user.teachStu.name as name," +
				" m.dailyScore as dailyScore, m.finalScore as finalScore " +
				" from Mark m where m.course.courseId=:courseId");
		params.put("courseId", courseId);
		
		return markDao.findList2(hql.toString(), params);
	}
	
	
}
