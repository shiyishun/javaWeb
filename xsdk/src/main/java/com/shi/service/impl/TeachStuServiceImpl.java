package com.shi.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.dao.TeachStuDao;
import com.shi.entity.TeachStu;
import com.shi.service.TeachStuService;

@Transactional
@Service
public class TeachStuServiceImpl implements TeachStuService {

	@Autowired
	private TeachStuDao teachStuDao;

	public Page<TeachStu> studentPage(String schoolInfoId, String classNo,
			String grade, String param, int pageNo, int pageSize) {

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(
				"from TeachStu t where t.isTeacher=1 "
						+ "and (t.user.status=0 or t.user.status=1)");
		if (schoolInfoId != null && schoolInfoId.trim().equals("")) {
			hql.append(" and t.schoolInfo.schoolInfoId =:schoolInfoId");
			params.put("schoolInfoId", schoolInfoId);
		}
		if (classNo != null && !classNo.trim().equals("")) {
			hql.append(" and t.classNo =:classNo");
			params.put("classNo", classNo);
		}
		if (grade != null && !grade.trim().equals("")) {
			hql.append(" and t.grade =:grade");
			params.put("grade", grade);
		}

		if (param != null && !param.trim().equals("")) {
			hql.append(" and (t.no like:no or t.name like:name)");
			params.put("no", "%" + param + "%");
			params.put("name", "%" + param + "%");
		}

		Page<TeachStu> page = teachStuDao.getPage(hql.toString(), params,
				pageNo, pageSize);
		List<TeachStu> list = page.getList();
		for (int i = 0; i < list.size(); i++) {

			list.get(i).setGenderStr(
					DictUtil.getKeyByValue("性别", list.get(i).getGender()
							.toString()));
			list.get(i).setClassNoStr(
					DictUtil.getKeyByValue("班级", list.get(i).getClassNo()
							.toString()));

		}
		return teachStuDao.getPage(hql.toString(), params, pageNo, pageSize);
	}

	public List<Map<String, Object>> getStudentByCourse(String courseId,
			String courseClassNo) {

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				" select distinct ts.teach_stu_id as stu_id, " +
				" ts.no as no, ts.name as name, ts.gender as gender, "
						+ " ts.major as major, ts.grade as grade, ts.class_no as class_no, "
				        + " (select count(1) from tb_call_theroll ct where ct.user_id=ts.user_id " 
						+ " and ct.call_state=0) as que_qin,"
				        + " (select count(1) from tb_call_theroll ct where ct.user_id=ts.user_id " 
						+ " and ct.call_state=0) as qing_jia "
						+ " from tb_teach_stu ts, tb_user_course_rel ucr "
						+ " where ts.is_teacher=1 and ucr.user_id=ts.user_id "
						+ " and ucr.course_id = '" + courseId + "' "
		                + " order by ts.no asc");
//		if (courseClassNo != null && !courseClassNo.trim().equals("")) {
//			sql.append(" and ucr.course_time_id=:courseTimeId ");
//			params.put("courseTimeId", courseClassNo);
//		}

		List<Map<String, Object>> listMap = teachStuDao.findListBySql(
				sql.toString(), params);

		if (listMap != null) {

			for (Map<String, Object> map : listMap) {
				map.put("gender_str", DictUtil.getKeyByValue("性别",
						map.get("gender").toString()));

				map.put("class_no_str", DictUtil.getKeyByValue("班级",
						map.get("class_no").toString()));
			}

		}

		return listMap;
	}

}
