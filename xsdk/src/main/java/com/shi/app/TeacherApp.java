package com.shi.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.common.ComUtil;
import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.controller.CallTherollController;
import com.shi.entity.CallTheroll;
import com.shi.entity.Course;
import com.shi.entity.CourseTime;
import com.shi.entity.Dict;
import com.shi.entity.Mark;
import com.shi.entity.Permi;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.entity.UserCourseRel;
import com.shi.service.CallTherollService;
import com.shi.service.CourseService;
import com.shi.service.CourseTimeService;
import com.shi.service.MarkService;
import com.shi.service.TeachStuService;
import com.shi.service.UserCourseRelService;
import com.shi.service.UserMngService;

@Controller
@RequestMapping(value = "app/teacher/")
public class TeacherApp {

	private static Logger logger = LogManager.getLogger(TeacherApp.class);

	@Autowired
	private UserMngService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CallTherollService callTherollService;
	@Autowired
	private CourseTimeService courseTimeService;
	@Autowired
	private UserCourseRelService userCourseRelService;
	@Autowired
	private MarkService markService;
	@Autowired
	private TeachStuService teachStuSerice;
	
	@ResponseBody
	@RequestMapping(value = "login")
	public JSONObject login(HttpServletRequest request,
			HttpServletResponse response, String loginName, String password) {
		JSONObject json = new JSONObject();
		try {
			User user = userService.findByLoginName(loginName);
			if (user == null || user.getStatus() == 2) {
				json.put("code", "1");
				json.put("errmsg", "账号不存在");
				return json;
			}
			if (user.getStatus() == 1) {
				json.put("code", "1");
				json.put("errmsg", "账号已被锁定，请联系管理员");
				return json;
			}

			String pwd = ComUtil.toMd5Str(password);

			if (!user.getPwd().equals(pwd)) {
				json.put("code", "2");
				json.put("errmsg", "密码错误");
				return json;
			}
			if (user.getTeachStu().getIsTeacher() != 0) {
				json.put("code", "3");
				json.put("errmsg", "请输入教师账号");
				return json;
			}

			String token = ComUtil.GetGUID();
			HashMap<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("user", user);
			userMap.put("timestamp", new Date());
			ComUtil.loginMap.put(token, userMap);
			JSONObject sJson = new JSONObject();
			sJson.put("userId", user.getUserId());
			sJson.put("token", token);
			sJson.put("userName", user.getUserName());
			sJson.put("name", user.getTeachStu().getName());
			json.put("code", "0");
			json.put("data", sJson);
			logger.info("手机用户：" + user.getUserName() + "-登陆成功!");
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("登陆异常", e);
			json.put("code", "101");
			json.put("errmsg", "服务异常");
			return json;
		}

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logout")
	public JSONObject logout(HttpServletRequest request, String token) {

		JSONObject json = new JSONObject();
		HashMap<String, Object> userMap = ComUtil.loginMap.get(token);
		User user = (User) userMap.get("user");
		ComUtil.loginMap.remove(token);
		json.put("code", "0");
		json.put("data", "");
		logger.info("手机用户：" + user.getUserName() + "-退出登陆!");
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "course_list")
	public JSONObject getCourseList(HttpServletRequest request, String token) {

		JSONObject json = new JSONObject();
		HashMap<String, Object> userMap = ComUtil.loginMap.get(token);
		User user = (User) userMap.get("user");
		List<Course> list = courseService.findByUserId(user.getUserId());
		json.put("code", "0");
		json.put("data", list);

		return json;
	}

	@ResponseBody
	@RequestMapping(value = "mark_list")
	public JSONObject getMarkList(String courseId, String token,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		HashMap<String, Object> userMap = ComUtil.loginMap.get(token);
		User user = (User) userMap.get("user");

		if (user.getTeachStu().getIsTeacher() != 0) {
			json.put("code", "2011");
			json.put("errmsg", "当前用户没有权限");
			return json;
		}

		List<Map<String, Object>> list = markService.findByCourseId2(courseId);
		json.put("code", "0");
		json.put("data", list);

		return json;
	}

	@ResponseBody
	@RequestMapping(value = "get_mark")
	public JSONObject getMark(String markId, HttpServletRequest request) {
		JSONObject json = new JSONObject();

		Map<String, Object> mark = markService.getByIdHql(markId);

		json.put("code", "0");
		json.put("data", mark);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "edit_mark")
	public JSONObject setMark(String markId, String dailyScore,
			String finalScore, HttpServletRequest request) {
		JSONObject json = new JSONObject();

		Mark mark = markService.getById(markId);

		if (mark == null) {
			json.put("code", "1221");
			json.put("errmsg", "获取成绩失败");
			return json;
		}
		
		mark.setDailyScore(Float.valueOf(dailyScore));
		mark.setFinalScore(Float.valueOf(finalScore));
        Course course = mark.getCourse();
		Float examScore = course.getDailyWeight()*mark.getDailyScore()
		+ course.getFinalWeight()+mark.getFinalScore();
        mark.setExamScore(examScore);
        
		markService.update(mark);

		json.put("code", "0");
		json.put("data", "");
		return json;
	}

	
	@ResponseBody
	@RequestMapping(value = "fresh_mark_list")
	public JSONObject genMark(String courseId, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		
		if(courseId==null){
			json.put("code", "1121");
			json.put("errmsg", "获取课程失败");	
			return json;
		}
		
		try {
			markService.genMarkByCourseId(courseId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("生成成绩表异常");
			json.put("code", "1122");
			json.put("errmsg", "生成成绩表异常");	
			return json;
			
		}
		json.put("code", "0");
		json.put("data", "");
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "get_course")
	public JSONObject getCourse(String courseId) {
		Course course = courseService.getById(courseId);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", course);
	
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "edit_course")
	public JSONObject editCourse(String courseId,
		      String dailyWeight, String finalWeight,String picketLine,
		      String classDate, String classOrder, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		
		Course course = courseService.getById(courseId);
        course.setClassDate(classDate);
        course.setDailyWeight(Float.valueOf(dailyWeight));
        course.setFinalWeight(Float.valueOf(finalWeight));
        course.setClassDate(classDate);
        course.setClassOrder(classOrder);
		courseService.update(course);

		json.put("code", "0");
		json.put("data", course);
		
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "course_time_list")
	public JSONObject getCouserTimeList(String courseId, HttpServletRequest request){
		
		JSONObject json = new JSONObject();
		List<Map<String, Object>> list = courseTimeService.findByCourseId2(courseId);
		json.put("code", "0");
		json.put("data", list);
		
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "call_theroll_list")
	public JSONObject getCallTherollList(String courseId,
			String courseTimeId, String callOrder){
		
		JSONObject json = new JSONObject();
		List<Map<String, Object>> list = callTherollService.
				findListMap(courseId, courseTimeId, callOrder);
		json.put("code", "0");
		json.put("data", list);
		return json;
	}
	
	/**
	 * 根据课程ID,课程时间ID生成签到表
	 * 
	 * @param request
	 * @param response
	 * @param courseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_signin")
	public JSONObject createSignin(HttpServletRequest request,
			HttpServletResponse response, String courseId, String courseTimeId,
			String callOrder) {

		JSONObject json = new JSONObject();
		Course course = courseService.getById(courseId);
		CourseTime courseTime = courseTimeService.getById(courseTimeId);
		List<UserCourseRel> ucrSet = userCourseRelService.findByTwoId(courseId,
				courseTimeId);

		Iterator<UserCourseRel> it = ucrSet.iterator();

		while (it.hasNext()) {
			UserCourseRel ucr = it.next();
			TeachStu ts = ucr.getUser().getTeachStu();
			if (1 == ts.getIsTeacher()) {
				CallTheroll callTheroll = new CallTheroll();
				callTheroll.setCallDate(new Date());
				callTheroll.setCallPosition("0*0");
				callTheroll.setCallState(0);
				callTheroll.setCourse(course);
				callTheroll.setCourseTime(courseTime);
				callTheroll.setCallOrder(Integer.valueOf(callOrder));
				callTheroll.setUser(ucr.getUser());
				callTherollService.save(callTheroll);
			}

		}

		json.put("code", "0");
		json.put("data", "");
		return json;
	}

	
	
	
	/**
	 * 通过用户ID获取所有课程
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "personal_course_list")
	public JSONObject getPersonalCourse(HttpServletRequest request,
			HttpServletResponse response, String token) {

		JSONObject json = new JSONObject();
		HashMap<String, Object> userMap = ComUtil.loginMap.get(token);
		User user = (User) userMap.get("user");
		Set<UserCourseRel> ucrSet = user.getUserCourseRelSet();
		Iterator<UserCourseRel> it = ucrSet.iterator();
		List<Course> courseList = new ArrayList<Course>();
		while (it.hasNext()) {
			UserCourseRel ucr = it.next();
			courseService.addClassInfo(ucr.getCourse());
			courseList.add(ucr.getCourse());
		}
		String jsonText = JSON.toJSONString(courseList, false);
		JSONArray jsonArray = JSONArray.parseArray(jsonText);
		json.put("code", "0");
		json.put("data", jsonArray);
		return json;
	}


	
	@ResponseBody
	@RequestMapping(value = "student_list")
	public JSONObject getStudentList(HttpServletRequest request,
			String courseId, String courseClassNo) {

		JSONObject json = new JSONObject();
		List<Map<String, Object>> list = teachStuSerice.getStudentByCourse(courseId, courseClassNo);
		json.put("code", "0");
		json.put("data", list);
		return json;
	}

	
	
	
	
	
	/**
	 * 通过用户ID获取所有课程
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "call_theroll_count")
	public JSONObject callTherollCount(HttpServletRequest request,
			HttpServletResponse response, String token) {

		JSONObject json = new JSONObject();
		HashMap<String, Object> userMap = ComUtil.loginMap.get(token);
		User user = (User) userMap.get("user");
		String userId = user.getUserId();
        List<Map<String, Object>> listMap = callTherollService.getStatisticsList(userId);

		json.put("code", "0");
		json.put("data", listMap);
		return json;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 通过课程ID与时间获取签到表
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_call_theroll")
	public JSONObject findCallTheroll(HttpServletRequest request,
			HttpServletResponse response, String courseId, String date) {

		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startCallDate = null;
		Date endCallDate = null;
		try {
			endCallDate = sdf.parse(date + " 23:59:59");
			startCallDate = sdf.parse(date + " 00:00:00");
		} catch (ParseException e) {
			json.put("code", "123");
			json.put("errmsg", "时间格式错误");
			e.printStackTrace();
			return json;

		}

		List<CallTheroll> callTheroll = callTherollService
				.findByCouserIdAndDate(courseId, startCallDate, endCallDate);
		String jsonText = JSON.toJSONString(callTheroll, false);
		JSONArray jsonArray = JSONArray.parseArray(jsonText);
		json.put("code", "0");
		json.put("data", jsonArray);
		return json;
	}

	
	
	
	/**
	 * 通过课程ID,课程时间ID，周序获取签到表
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get_call_theroll")
	public JSONObject getCallTheroll(HttpServletRequest request,
			HttpServletResponse response, String courseId, String courserTime,
			String callOrder) {

		JSONObject json = new JSONObject();
		Integer callOrderI = null;
		try {
			callOrderI = Integer.valueOf(callOrder);
		} catch (Exception e) {
			logger.error("周序输入错误", e);
			json.put("code", "22202");
			json.put("errmsg", "请输入正确的周序");
			return json;
		}
		List<CallTheroll> callTheroll = callTherollService.findByCouserIdAnd2(
				courseId, courserTime, callOrderI);

		json.put("code", "0");
		json.put("data", callTheroll);
		return json;
	}

	public static void main(String[] args) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("week", "星期一");
		map1.put("period", "5-6节");
		map1.put("periodVal", "56");
		list.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("week", "星期一");
		map2.put("period", "1-2节");
		map2.put("periodVal", "12");
		list.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("week", "星期一");
		map3.put("period", "3-4节");
		map3.put("periodVal", "34");
		list.add(map3);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("week", "星期二");
		map4.put("period", "1-6节");
		map4.put("periodVal", "16");
		list.add(map4);
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("week", "星期二");
		map5.put("period", "7-8节");
		map5.put("periodVal", "78");
		list.add(map5);
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put("week", "星期一");
		map6.put("period", "11-12节");
		map6.put("periodVal", "1112");
		list.add(map6);
		sort(list);
		for (Map m : list) {
			System.out.println(m.toString());
		}
	}

	public static void sort(List<Map<String, Object>> list) {

		if (null != list && list.size() > 0) {
			Collections.sort(list, new Comparator<Map>() {
				@Override
				public int compare(Map o1, Map o2) {
					int ret = o1.get("week").toString()
							.compareTo(o2.get("week").toString());
					if (ret == 0) {
						int one = Integer.valueOf(o1.get("periodVal")
								.toString());
						int two = Integer.valueOf(o2.get("periodVal")
								.toString());
						return (int) (one - two);
					} else {
						return ret;
					}

				}
			});
		}
	}

}
