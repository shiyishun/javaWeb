package com.shi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.dao.CourseTimeDao;
import com.shi.entity.CourseTime;
import com.shi.entity.SchoolInfo;
import com.shi.service.CourseTimeService;


@Transactional
@Service
public class CourseTimeServiceImpl implements CourseTimeService {

	@Autowired
	private CourseTimeDao courseTimeDao;
	
	
	@Override
	public Page<CourseTime> getPage(String courseId, int pageNo,
			int pageSize){
		
		StringBuffer hql = new StringBuffer("select ct from CourseTime ct, CourseTimeRel ctr" +
				" where ct.courseTimeId = ctr.courseTime.courseTimeId " +
				" and ctr.course.courseId = '"+courseId+"'");

		hql.append(" order by ct.week,ct.startPeriod asc");
		return courseTimeDao.getPage(hql.toString(), null, pageNo, pageSize);
		
	}
	
	public void delete(CourseTime courseTime){
		
		courseTimeDao.delete(courseTime);
	
	}
	
	public CourseTime getById(String courseTimeId){
			
		return courseTimeDao.getById(courseTimeId);
	}
	
	
	public void saveOrUpdate(CourseTime courseTime){
		
		if(courseTime.getCourseTimeId()!=null&&"".equals(courseTime.getCourseTimeId().trim())){
			courseTime.setCourseTimeId(null);
		}
		
		courseTimeDao.saveOrUpdate(courseTime);
	}
	
	public List<CourseTime> findByCourseId(String courseId){
		
		StringBuffer hql = new StringBuffer("select ct from CourseTime ct, CourseTimeRel ctr" +
				" where ct.courseTimeId = ctr.courseTime.courseTimeId " +
				" and ctr.course.courseId = '"+courseId+"'");
		
		return courseTimeDao.findList(hql.toString());
	}
	
	public List<Map<String, Object>> findByCourseId2(String courseId){
		
		StringBuffer hql = new StringBuffer("select ct.courseTimeId as courseTimeId," +
				" ct.week as week, ct.startPeriod as startPeriod, ct.endPeriod as endPeriod, " +
				" ct.classLocation as classLocation, ct.classShape as classShape " +
				" from CourseTime ct, CourseTimeRel ctr" +
				" where ct.courseTimeId = ctr.courseTime.courseTimeId " +
				" and ctr.course.courseId = '"+courseId+"'");
		
		List<Map<String, Object>> listMap = courseTimeDao.findList2(hql.toString(), null);
		if(listMap!=null){
			for(Map<String,Object> map:listMap){
			    String weekStr = DictUtil.getKeyByValue("星期", map.get("week").toString());				
				String period = map.get("startPeriod").toString() +"-"+map.get("endPeriod").toString()
						+"节";
				String timeInfo = weekStr + " " + period;
			    map.put("timeInfo", timeInfo);
				
			}
			return listMap;
		}else{
			return null;
		}
		
		
	}
}
