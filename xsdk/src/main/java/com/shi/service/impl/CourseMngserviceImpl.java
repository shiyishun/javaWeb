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

import com.shi.common.DictUtil;
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
			
		StringBuffer sql = new StringBuffer("select ct.class_shape, ct.start_period, ct.end_period, ct.class_location, c.course_name ,ct.course_time_id" +
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
	public Map<String, Object> signIn(String cid, String uid,String callPosition,String callState,String courseTimeId) {
		// TODO Auto-generated method stub
		SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).toString();
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		StringBuffer sql = new StringBuffer("select call_theroll_id from tb_call_theroll where course_id="+cid+" and user_id="+uid + " and call_date like"+"'%"+date+"%'");
		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		reslist = courseDao.findListBySql(sql.toString());
		String callDate=ddf.format(new Date()).toString();
		StringBuffer sql1 = new StringBuffer("UPDATE tb_call_theroll SET call_state='1', call_date="+"'"+callDate+"'"+" , call_position="+"'"+callPosition+"'"+" WHERE call_theroll_id="+"'"+reslist.get(0).get("call_theroll_id").toString()+"'");
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
	@Override
	public List<Map<String, Object>> getCourseList(String sid) {
		// TODO Auto-generated method stub
//		Date date = new Date();
//	 	Calendar cal = Calendar.getInstance();  
//        cal.setTime(date);  
//        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; 
//        System.out.println("xingqi #####"+w);
//		StringBuffer sql = new StringBuffer("select uc.course_id, c.course_name, ct.class_location,ct.start_period,ct.end_period,ct.class_shape,ct.course_time_id" +
//				" from tb_user_course_rel uc left join tb_course c on uc.course_id=c.course_id left join tb_course_time ct on uc.course_time_id=ct.course_time_id left join tb_call_theroll ctroll on");
//		
//			Map<String, Object> params = new HashMap<String, Object>();
//				if(sid!=null){
//					sql.append(" where uc.user_id =:sid");
//					sql.append(" and ct.week =:wid");
//					sql.append(" and ctroll.call_state = '0'");
//					params.put("sid", sid);
//					params.put("wid", w);
//				}
//				sql.append(" order by c.course_no asc");
//				List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
//				maplist = courseDao.findListBySql(sql.toString(), params);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).toString();
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		StringBuffer sql = new StringBuffer("select c.course_id, c.course_name, ct.class_location,ct.start_period,ct.end_period,ct.class_shape,ct.course_time_id "+
        ",ctroll.call_theroll_id from tb_call_theroll ctroll left join tb_course c on ctroll.course_id = c.course_id left join tb_course_time ct on ctroll.course_time_id=ct.course_time_id where user_id="+sid + " and call_date like"+"'%"+date+"%' and call_state = '0'");
		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		reslist = courseDao.findListBySql(sql.toString());
		return reslist;
	}
	@Override
	public List<Map<String, Object>> getAllCourseList(String sid) {
		// TODO Auto-generated method stubDate date = new Date();
        System.out.println("suoyoukecheng #####");
		StringBuffer sql = new StringBuffer("select uc.course_id, c.course_name, c.class_date, ct.class_location,ct.start_period as startPeriod,ct.end_period as endPeriod,ct.week " +
				" from tb_user_course_rel uc left join tb_course c on uc.course_id=c.course_id left join tb_course_time ct on uc.course_time_id=ct.course_time_id ");
		
			Map<String, Object> params = new HashMap<String, Object>();
				if(sid!=null){
					sql.append(" where uc.user_id =:sid");
					params.put("sid", sid);
				}
				sql.append(" order by ct.week asc");
				List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
				maplist = courseDao.findListBySql(sql.toString(), params);
				if(maplist!=null){
					for(Map<String,Object> map:maplist){
					    String weekStr = DictUtil.getKeyByValue("星期", map.get("week").toString());				
						String period = map.get("startPeriod").toString() +"-"+map.get("endPeriod").toString()
								+"节";
						String timeInfo = weekStr + " " + period;
					    map.put("timeInfo", timeInfo);
						
					}
				
				}
		return maplist;
	}
	@Override
	public List<Map<String, Object>> getScoreList(String sid) {
		// TODO Auto-generated method stub
		System.out.println("huoqu fenshu");
		StringBuffer sql = new StringBuffer("select c.course_name, m.daily_score,m.final_score,m.exam_score " +
				" from tb_mark m left join tb_course c on m.course_id=c.course_id");
		
			Map<String, Object> params = new HashMap<String, Object>();
				if(sid!=null){
					sql.append(" where m.user_id =:sid");
					params.put("sid", sid);
				}
//				sql.append(" order by ct.week asc");
				List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
				maplist = courseDao.findListBySql(sql.toString(), params);
				
		return maplist;
	}
	@Override
	public List<Map<String, Object>> getRecordList(String sid,String cs) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("SELECT c.course_name, ct.course_id, count(*) as count FROM tb_call_theroll ct left join tb_course c on c.course_id=ct.course_id");
		
			Map<String, Object> params = new HashMap<String, Object>();
				if(sid!=null){
					sql.append(" where ct.user_id =:sid and ct.call_state=:cs");
					params.put("sid", sid);
					params.put("cs", cs);
				}
				sql.append(" group by course_id");
				List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
				maplist = courseDao.findListBySql(sql.toString(), params);
				
		return maplist;
	}

}
