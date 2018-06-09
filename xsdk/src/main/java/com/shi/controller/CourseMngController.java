package com.shi.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shi.common.ComUtil;
import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.entity.Course;
import com.shi.entity.Dict;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.entity.UserCourseRel;
import com.shi.service.CourseService;
import com.shi.service.UserCourseRelService;




@Controller
@RequestMapping(value = "course_mng/")
public class CourseMngController {

	
	@Autowired
	private CourseService courseService;
	
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
	
}
