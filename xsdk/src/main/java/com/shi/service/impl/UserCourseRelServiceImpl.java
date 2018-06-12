package com.shi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.UserCourseRelDao;
import com.shi.entity.UserCourseRel;
import com.shi.service.UserCourseRelService;

@Transactional
@Service
public class UserCourseRelServiceImpl implements UserCourseRelService {

	@Autowired
	private UserCourseRelDao userCourseRelDao;

	public boolean isExist(String userId, String courseId) {

		String hql = "from UserCourseRel ucr where ucr.user.userId='" + userId
				+ "'" + " and ucr.course.courseId='" + courseId + "'";

		List<UserCourseRel> list = userCourseRelDao.findList(hql);
		if (list != null&&list.size()>0) {
			return true;
		} else {
			return false;
		}
	}

	public void add(UserCourseRel userCourseRel) {

		if (!isExist(userCourseRel.getUser().getUserId(), userCourseRel
				.getCourse().getCourseId())) {
			
			userCourseRelDao.save(userCourseRel);
		}
	}
	public List<UserCourseRel> findByTwoId(String courseId, String courseTimeId){
		
		String hql = "from UserCourseRel ucr where ucr.courseTime.courseTimeId='" + courseTimeId
				+ "'" + " and ucr.course.courseId='" + courseId + "'";

		List<UserCourseRel> list = userCourseRelDao.findList(hql);
		
		return list;
	}
}
