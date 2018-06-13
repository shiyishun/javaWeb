package com.shi.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.shi.common.ComUtil;
import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.entity.Course;
import com.shi.entity.CourseTime;
import com.shi.entity.CourseTimeRel;
import com.shi.entity.Dict;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.entity.UserCourseRel;
import com.shi.service.CourseService;
import com.shi.service.CourseTimeRelService;
import com.shi.service.CourseTimeService;
import com.shi.service.UserCourseRelService;




@Controller
@RequestMapping(value = "course_mng/")
public class CourseMngController {

	private static Logger logger = LogManager
			.getLogger(CourseMngController.class);
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseTimeService courseTimeService;
	@Autowired
	private UserCourseRelService userCourseRelService;
	
	
	@ResponseBody
	@RequestMapping(value = "page")
	public JSONObject pageCourse(String param, String classNo, String grade, String no, String size,
			HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
    	String userId = user.getUserId();
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<Course> page  = courseService.getPage(userId, param, pageNo, pageSize);

		json.put("code", "0");
		json.put("data", page);

		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "get")
	public JSONObject getById(String courseId) {
		Course course = courseService.getById(courseId);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", course);
	
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "save")
	public JSONObject saveOrUpdate(Course course, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		
		
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		courseService.saveOrUpdate(course);
		UserCourseRel ucr = new UserCourseRel();
		ucr.setCourse(course);
		ucr.setUser(user);
		userCourseRelService.add(ucr);
		json.put("code", "0");
		json.put("data", course);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public JSONObject getList(HttpServletRequest request){
		
		JSONObject json = new JSONObject();
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		List<Course> list = courseService.findByUserId(user.getUserId());
		json.put("code", "0");
		json.put("data", list);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "t_list")
	public JSONObject getTimeList(String courseId, HttpServletRequest request){
		
		JSONObject json = new JSONObject();
		List<CourseTime> list = courseTimeService.findByCourseId(courseId);

		Map<String, String> dictMap = DictUtil.dictKeyValue("星期");
		JSONObject dictJson = new JSONObject();
		dictJson.put("星期", dictMap);
		json.put("code", "0");
		json.put("data", list);
		json.put("dict", dictJson);
		
		return json;
	}
}
