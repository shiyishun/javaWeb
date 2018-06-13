package com.shi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.CourseTimeRelDao;
import com.shi.entity.CourseTimeRel;
import com.shi.entity.UserCourseRel;
import com.shi.service.CourseTimeRelService;


@Transactional
@Service
public class CourseTimeRelServiceImpl implements CourseTimeRelService {

	
	@Autowired
	private CourseTimeRelDao courseTimeRelDao;
	
	public void del(CourseTimeRel courseTimeRel){
		courseTimeRelDao.delete(courseTimeRel);
	}
	
	public CourseTimeRel findByCourseTimeId(String courseTimeId){
		String hql = " from CourseTimeRel ctr where ctr.courseTime.courseTimeId ='"+ courseTimeId +"'";
		return courseTimeRelDao.get(hql);
	}
	
	
	public boolean isExist(String courseId, String courseTimeId) {

		String hql = "from CourseTimeRel ctr where ctr.course.courseId='" + courseId
				+ "'" + " and ctr.courseTime.courseTimeId='" + courseTimeId + "'";

		List<CourseTimeRel> list = courseTimeRelDao.findList(hql);
		if (list != null&&list.size()>0) {
			return true;
		} else {
			return false;
		}
	}

	public void add(CourseTimeRel courseTimeRel) {

		if (!isExist(courseTimeRel.getCourse().getCourseId(), courseTimeRel
				.getCourseTime().getCourseTimeId())) {
			
			courseTimeRelDao.save(courseTimeRel);
		}
	}
	
}
