package com.shi.app;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.entity.Course;
import com.shi.entity.Dict;
import com.shi.entity.User;
import com.shi.service.CourseMngservice;


@Controller
@RequestMapping(value = "app/student/")
public class StudentApp {

	@Autowired
	private CourseMngservice courseMngservice;
	
	@ResponseBody
	@RequestMapping(value = "getc")
	public JSONObject getDictCateByName(String cateName) {

		List<Dict> dictList = DictUtil.dictCategoryList.get(cateName);
		String jsonText = JSON.toJSONString(dictList, false);
		JSONArray jsonArray= JSONArray.parseArray(jsonText);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", jsonArray);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "getPersonCourse")
	public JSONObject getPersonCourse(String param, String no, String size) {
		
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<Course> page = courseMngservice.getCoursePage(param, pageNo, pageSize);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", page);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "getCourseDetail")
	public JSONObject getCourseDetail(String param, String no, String size) {

		Map<String, Object> course = courseMngservice.getCourseDetail(param);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", course);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "signIn")
	public JSONObject signIn(String cid,String uid) {
//,String callOrder,String callPosition,String callDate,String callState,String courseTimeId
		Map<String, Object> res = courseMngservice.signIn(cid,uid,"1","3*4","2018-09-16", "1", "1");
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", res);
		return json;
	}
}
