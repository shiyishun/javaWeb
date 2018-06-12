package com.shi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.shi.entity.User;
import com.shi.entity.UserCourseRel;
import com.shi.service.CourseService;
import com.shi.service.CourseTimeRelService;
import com.shi.service.CourseTimeService;

@Controller
@RequestMapping(value = "course_time_mng/")
public class CourseTimeController {

	private static Logger logger = LogManager
			.getLogger(CourseTimeController.class);
	
	@Autowired
	private CourseTimeService courseTimeService;

	@Autowired
	private CourseTimeRelService courseTimeRelService;

	@Autowired
	private CourseService courseService;

	@ResponseBody
	@RequestMapping(value = "page")
	public JSONObject pageCourse(String courseId, String no, String size,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (no == null || no.equals("")) {
			no = "1";
		}
		if (size == null || size.equals("")) {
			size = "10";
		}
 
		int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);

		Page<CourseTime> page = courseTimeService.getPage(courseId, pageNo,
				pageSize);
		Map<String, String> dictMap = DictUtil.dictKeyValue("星期");
		JSONObject dictJson = new JSONObject();
		dictJson.put("星期", dictMap);
		json.put("code", "0");
		json.put("data", page);
		json.put("dict", dictJson);
	
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "del")
	public JSONObject delete(String courseTimeId) {
		JSONObject json = new JSONObject();
		CourseTime courseTime = courseTimeService.getById(courseTimeId);
		//CourseTimeRel courseTimeRel = courseTime.getCourseTimeRel();
		courseTimeService.delete(courseTime);
		json.put("code", "0");
		json.put("data", "");

		return json;
	}

	@ResponseBody
	@RequestMapping(value = "get")
	public JSONObject get(String courseTimeId) {
		JSONObject json = new JSONObject();
		CourseTime courseTime = courseTimeService.getById(courseTimeId);
		json.put("code", "0");
		json.put("data", courseTime);

		return json;
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public JSONObject saveOrUpdate(String courseId, String courseTimeId,
			String week, String startPeriod, String endPeriod,
			String classLocation, String classShape) {
		JSONObject json = new JSONObject();

		Course course = courseService.getById(courseId);
		if (course == null) {
			json.put("code", "201");
			json.put("errmsg", "获取课程标识失败");
			return json;
		}
		
		CourseTime courseTime = courseTimeService.getById(courseTimeId);
		
		if(courseTime == null){
			//新增
			courseTime = new CourseTime();
			CourseTimeRel courseTimeRel = new CourseTimeRel();
			courseTimeRel.setCourse(course);
			courseTimeRel.setCourseTime(courseTime);
			courseTime.setCourseTimeRel(courseTimeRel);
		}
		courseTime.setClassShape(classShape);
		courseTime.setClassLocation(classLocation);
		courseTime.setWeek(Integer.parseInt(week));
		courseTime.setStartPeriod(Integer.parseInt(startPeriod));
		courseTime.setEndPeriod(Integer.parseInt(endPeriod));
			
		courseTimeService.saveOrUpdate(courseTime);
		json.put("code", "0");
		json.put("data", "");

		return json;
	}

}
