package com.shi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.dao.CourseDao;
import com.shi.entity.Course;
import com.shi.entity.CourseTimeRel;
import com.shi.entity.UserCourseRel;
import com.shi.service.CourseService;


@Transactional
@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;
	
	public void addClassInfo(Course course){
		 
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Set<CourseTimeRel> ctrSet = course.getCourseTimeRelSet();
		Iterator<CourseTimeRel> it = ctrSet.iterator(); 
		while (it.hasNext()) {  
			Map<String, Object> map = new HashMap<String, Object>();
			CourseTimeRel ucr = it.next();  
		    map.put("week", DictUtil.getKeyByValue("星期", String.valueOf(ucr.getCourseTime().getWeek())));
		    map.put("startPeriod", ucr.getCourseTime().getStartPeriod());
		    map.put("endPeriod", ucr.getCourseTime().getEndPeriod());
		    map.put("classLocation", ucr.getCourseTime().getClassLocation());
		    map.put("period", ucr.getCourseTime().getStartPeriod()+"-"
		              +ucr.getCourseTime().getEndPeriod()+"节" );
		    list.add(map);
		    
		} 
		sort(list);
		course.setClassInfoMapList(list);
	}
	
	public static void sort(List<Map<String, Object>> list) {
		if (null != list && list.size() > 0) {
			Collections.sort(list, new Comparator<Map>() {
				@Override
				public int compare(Map o1, Map o2) {
					int ret = o1.get("week").toString().compareTo(o2.get("week").toString());
					if (ret==0) {
                        int one = Integer.valueOf(o1.get("startPeriod").toString());  
                        int two = Integer.valueOf(o2.get("startPeriod").toString());  			
						return (int)(one-two);
					}else{
						return ret;
					}
				
				}
			});
		}
	}
	
	
	public Course getById(String courseId){
		return courseDao.getById(courseId);
	}

	
	public Page<Course> getPage(String userId, String param, int pageNo, int pageSize){
		
		StringBuffer hql = new StringBuffer("select c from Course c , UserCourseRel ucr " +
				"where c.courseId=ucr.course.courseId " +
				"and ucr.user.userId='"+userId+"'");
		Map<String, Object> params = new HashMap<String, Object>();
		if(param!=null&&!param.trim().equals("")){
			hql.append(" and c.courseName like:courseName");
			params.put("courseName", "%"+param+"%");
		}
		hql.append(" order by c.courseNo asc");
		return courseDao.getPage(hql.toString(), params, pageNo, pageSize);
		
	}
	

	public void saveOrUpdate(Course course){
		if(course.getCourseId()!=null&&"".equals(course.getCourseId().trim())){
			course.setCourseId(null);
		}
		courseDao.saveOrUpdate(course);
	}
	
	
}
