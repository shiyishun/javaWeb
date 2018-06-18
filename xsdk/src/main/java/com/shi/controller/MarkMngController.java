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
import com.shi.entity.Dict;
import com.shi.entity.Mark;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.service.MarkService;



@Controller
@RequestMapping(value = "mark_mng/")
public class MarkMngController {

	private static Logger logger = LogManager
			.getLogger(MarkMngController.class);
	
	@Autowired
	private MarkService markService;
	
	
	
	@ResponseBody
	@RequestMapping(value = "gen_mark")
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
	@RequestMapping(value = "page")
	public JSONObject getPage(String param, String classNo, String grade, String no, String size,
			String courseId, HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
        
		if(user.getTeachStu().getIsTeacher()!=0){
			json.put("code", "2011");
			json.put("errmsg", "当前用户没有权限");
			return json;
		}
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
    	String schoolInfoId = user.getTeachStu().getSchoolInfo().getSchoolInfoId();
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<Mark> page  = markService.getPage(schoolInfoId, classNo, grade, param, 
				courseId, pageNo, pageSize);
		JSONObject dictJson = new JSONObject();
		List<Dict> dictList = DictUtil.dictCategoryList.get("班级");
		JSONObject dictMapJson = new JSONObject();
		Map<String, String> dictMap1 = DictUtil.dictKeyValue("班级");
		Map<String, String> dictMap2 = DictUtil.dictKeyValue("性别");
		dictJson.put("班级", dictList);
		dictMapJson.put("班级", dictMap1);
		dictMapJson.put("性别", dictMap2);
		json.put("code", "0");
		json.put("data", page);
		json.put("dict", dictJson);
		json.put("dictMap", dictMapJson);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "set_mark")
	public JSONObject setMark(String markId, String dailyScore, String finalScore, String examScore,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		
		Mark mark = markService.getById(markId);
		
		if(mark==null){
			json.put("code", "1221");
			json.put("errmsg", "获取成绩失败");	
			return json;
		}
		mark.setDailyScore(Float.valueOf(dailyScore));
		mark.setFinalScore(Float.valueOf(finalScore));
		mark.setExamScore(Float.valueOf(examScore));
	    markService.update(mark);

		json.put("code", "0");
		json.put("data", "");
		return json;
	}
	


}
