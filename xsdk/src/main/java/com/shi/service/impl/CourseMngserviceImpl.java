package com.shi.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.Page;
import com.shi.dao.CourseDao;
import com.shi.entity.Course;
import com.shi.service.CourseMngservice;

import java.util.UUID;

@Transactional
@Service
public class CourseMngserviceImpl implements CourseMngservice {

	@Autowired
	CourseDao courseDao;
	@Override
	public Page<Course> getCoursePage(String sid, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Date date = new Date();
	 	Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; 
        System.out.println("xingqi "+w);
		StringBuffer sql = new StringBuffer("select uc.course_id, c.course_name, c.class_date, c.class_order " +
				" from tb_user_course_rel uc left join tb_course c on uc.course_id=c.course_id left join tb_course_time_rel ctr on c.course_id=ctr.course_id left join tb_course_time ct on ctr.course_time_id=ct.course_time_id ");
		
			Map<String, Object> params = new HashMap<String, Object>();
				if(sid!=null){
					sql.append(" where uc.user_id =:sid");
					sql.append(" and ct.week =:wid");
					params.put("sid", sid);
					params.put("wid", w);
				}
				sql.append(" order by c.course_no asc");
				return courseDao.getSqlPage(sql.toString(), params, pageNo, pageSize);
	}
	@Override
	public Map<String, Object> getCourseDetail(String cid) {
		// TODO Auto-generated method stub
			
		StringBuffer sql = new StringBuffer("select ct.class_shape, ct.start_period, ct.end_period, ct.class_location, c.course_name " +
				" from tb_course_time ct left join tb_course_time_rel ctr on ct.course_time_id=ctr.course_time_id left join tb_course c on ctr.course_id=c.course_id  ");
		
			Map<String, Object> params = new HashMap<String, Object>();
				if(cid!=null){
					sql.append(" where c.course_id =:cid");
					params.put("cid", cid);
				}
				sql.append(" order by c.course_no asc");
				
				List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
				maplist = courseDao.findListBySql(sql.toString(), params);
				return maplist.get(0);
	}
	@Override
	public Map<String, Object> signIn(String cid, String uid, String callOrder,String callPosition,String callDate,String callState,String courseTimeId) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select call_theroll_id from tb_call_theroll where course_id="+cid+" and user_id="+uid+" and call_order="+callOrder);
		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		reslist = courseDao.findListBySql(sql.toString());
		StringBuffer sql1 = new StringBuffer("UPDATE tb_call_theroll SET call_state='0', call_date="+"'"+callDate+"'"+" , call_position="+"'"+callPosition+"'"+" WHERE call_theroll_id="+"'"+reslist.get(0).get("call_theroll_id").toString()+"'");
		int kk=0;
		try {
			kk = courseDao.executeBySql(sql1.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> mm = new HashMap<String,Object>();
		System.out.print(kk);
		
			int state = 1;
			if(kk==1)
				state=0;
			mm.put("state", state);
			mm.put("message", "签到成功");
		return mm;
	}

}
