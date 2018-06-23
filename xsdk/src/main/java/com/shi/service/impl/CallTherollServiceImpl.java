package com.shi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.dao.CallTherollDao;
import com.shi.entity.CallTheroll;
import com.shi.service.CallTherollService;

@Transactional
@Service
public class CallTherollServiceImpl implements CallTherollService {

	@Autowired
	private CallTherollDao callTherollDao;

	public void save(CallTheroll callTheroll) {
		callTherollDao.save(callTheroll);
	}

	public void update(CallTheroll callTheroll) {
		callTherollDao.update(callTheroll);
	}

	public List<CallTheroll> findByCouserIdAndDate(String courseId,
			Date startCallDate, Date endCallDate) {

		String hql = "from CallTheroll c where c.course.courseId=:courseId "
				+ "and c.callDate >=:startCallDate and c.callDate <=:endCallDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("courseId", courseId);
		params.put("startCallDate", startCallDate);
		params.put("endCallDate", endCallDate);
		return callTherollDao.findList(hql, params);
	}

	public Page<CallTheroll> getStatisticsPage(String param, String userId,
			int pageNo, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				"SELECT "
						+ " chuqin.*, (select count(1) from tb_call_theroll tt "
						+ "where  tt.course_id = chuqin.course_id and "
						+ "tt.course_time_id = chuqin.course_time_id "
						+ "and tt.call_order = chuqin.call_order and tt.call_state !=1 ) as 缺勤数 "
						+ ",  (select count(1) from tb_call_theroll tt "
						+ "where  tt.course_id = chuqin.course_id and "
						+ "tt.course_time_id = chuqin.course_time_id "
						+ "and tt.call_order = chuqin.call_order and tt.call_state =1 ) as 出勤数 "
						+ " from "
						+ "( select t2.course_id, t3.course_time_id, "
						+ "t2.course_no, t2.course_name, t3.week ,"
						+ "t3.start_period, t3.end_period , "
						+ "t.call_order, "
						+ "count(1) 考勤数 "
						+ "from "
						+ "tb_call_theroll t  "
						+ "LEFT JOIN tb_course t2 "
						+ "on t.course_id=t2.course_id "
						+ "LEFT JOIN tb_course_time t3 "
						+ "on t.course_time_id=t3.course_time_id "
						+ "LEFT JOIN tb_user_course_rel t4 "
						+ "on t2.course_id = t4.course_id where t4.user_id = '"
						+ userId
						+ "' "
						+ "GROUP BY  t.course_id , t.course_time_id, t.call_order "
						+ ") chuqin ");

		if (param != null && !param.trim().equals("")) {
			sql.append(" where (chuqin.course_name like:courseName "
					+ "or chuqin.course_no like:courseNo)");
			params.put("courseName", "%" + param + "%");
			params.put("courseNo", "%" + param + "%");
		}
		return callTherollDao.getSqlPage(sql.toString(), params, pageNo,
				pageSize);
	}

	public Page<CallTheroll> getPage(String courseId, String courseTimeId,
			String callOrder, String callState, String param, int pageNo,
			int pageSize) {

		StringBuffer hql = new StringBuffer(
				"select ct.user.teachStu.no, ct.user.teachStu.name,"
						+ " ct.user.teachStu.gender, ct.user.teachStu.grade, ct.user.teachStu.major,"
						+ " ct.user.teachStu.classNo, ct.callDate, ct.callState "
						+ " from CallTheroll ct where ct.user.teachStu.isTeacher = 1"
						+ " and (ct.user.status=0 or ct.user.status=1) "
						+ " and ct.course.courseId='" + courseId
						+ "' and ct.courseTime.courseTimeId='" + courseTimeId
						+ "'" + " and ct.callOrder=" + callOrder + "");
		Map<String, Object> params = new HashMap<String, Object>();
		if (callState != null && !callState.trim().equals("")) {
			hql.append(" and ct.callState =:callState");
			params.put("callState", Integer.valueOf(callState));
		}
		if (param != null && !param.trim().equals("")) {
			hql.append(" and (ct.user.teachStu.name like:name or ct.user.teachStu.no like:no)");
			params.put("name", "%" + param + "%");
			params.put("no", "%" + param + "%");
		}

		return callTherollDao.getPage(hql.toString(), params, pageNo, pageSize);
	}

	public List<CallTheroll> findByCouserIdAnd2(String courseId,
			String courseTimeId, Integer callOrder) {

		String hql = "from CallTheroll c where c.course.courseId=:courseId "
				+ "and c.courseTime.courseTimeId=:courseTimeId and c.callOrder =:callOrder";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("courseId", courseId);
		params.put("courseTimeId", courseTimeId);
		params.put("callOrder", callOrder);
		return callTherollDao.findList(hql, params);

	}

	public List<Map<String, Object>> findListMap(String courseId,
			String courseTimeId, String callOrder) {

		StringBuffer hql = new StringBuffer(
				"select ct.callTherollId as callTherollId, ct.user.teachStu.no as no, ct.user.teachStu.name as name,"
						+ " ct.user.teachStu.gender as gender, ct.user.teachStu.grade as grade, "
						+ " ct.user.teachStu.major as major, ct.callPosition as callPosition, "
						+ " ct.user.teachStu.classNo as classNo, ct.callDate as callDate, "
						+ " ct.callState as callState"
						+ " from CallTheroll ct where ct.user.teachStu.isTeacher = 1"
						+ " and (ct.user.status=0 or ct.user.status=1) "
						+ " and ct.course.courseId='" + courseId
						+ "' and ct.courseTime.courseTimeId='" + courseTimeId
						+ "'" + " and ct.callOrder=" + callOrder
						+ " order by user.teachStu.no asc");
		List<Map<String, Object>> listMap = callTherollDao.findList2(
				hql.toString(), null);
		if (listMap != null) {

			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			for (Map<String, Object> map : listMap) {
				map.put("callStateStr", DictUtil.getKeyByValue("签到状态",
						map.get("callState").toString()));
				map.put("callDate", sdf.format(map.get("callDate")));   
				
				map.put("classNoStr", DictUtil.getKeyByValue("班级",
						map.get("classNo").toString()));
			}

		}
		return listMap;
	}

	
	
	public List<Map<String, Object>> getStatisticsList(String userId) {
		StringBuffer sql = new StringBuffer(
				          "SELECT "
						+ " chuqin.*, (select count(1) from tb_call_theroll tt "
						+ "where  tt.course_id = chuqin.course_id and "
						+ "tt.course_time_id = chuqin.course_time_id "
						+ "and tt.call_order = chuqin.call_order and tt.call_state =0 ) as que_qin"
						+ ",  (select count(1) from tb_call_theroll tt "
						+ "where  tt.course_id = chuqin.course_id and "
						+ "tt.course_time_id = chuqin.course_time_id "
						+ "and tt.call_order = chuqin.call_order and tt.call_state =1 ) as chu_qin "
						+ ",  (select count(1) from tb_call_theroll tt "
						+ "where  tt.course_id = chuqin.course_id and "
						+ "tt.course_time_id = chuqin.course_time_id "
						+ "and tt.call_order = chuqin.call_order and tt.call_state =2 ) as qing_jia "			
						+ " from "
						+ "( select t2.course_id as course_id, t3.course_time_id as course_time_id, "
						+ "t2.course_no as course_no, t2.course_name as course_name, t3.week as week,"
						+ "t3.start_period as start_period, t3.end_period as end_period, "
						+ "t.call_order as call_order, "
						+ "count(1) kao_qin "
						+ "from "
						+ "tb_call_theroll t  "
						+ "LEFT JOIN tb_course t2 "
						+ "on t.course_id=t2.course_id "
						+ "LEFT JOIN tb_course_time t3 "
						+ "on t.course_time_id=t3.course_time_id "
						+ "LEFT JOIN tb_user_course_rel t4 "
						+ "on t2.course_id = t4.course_id where t4.user_id = '"
						+ userId
						+ "' "
						+ "GROUP BY  t.course_id , t.course_time_id, t.call_order "
						+ ") chuqin ");
        List<Map<String, Object>> listMap = callTherollDao.findListBySql(sql.toString());
        
        if (listMap != null) {


			for (Map<String, Object> map : listMap) {
				map.put("week_str", DictUtil.getKeyByValue("星期",
						map.get("week").toString()));

			    map.put("period", map.get("start_period")+"-"
			              + map.get("end_period")+"节" );
				
			}

		}
        
        return listMap;

	}

	public CallTheroll getById(String callTherollId){
		
	  return callTherollDao.getById(callTherollId);	
	}
}
