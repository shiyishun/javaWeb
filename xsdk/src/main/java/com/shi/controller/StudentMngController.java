package com.shi.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.shi.entity.Dict;
import com.shi.entity.SchoolInfo;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.service.TeachStuService;



@Controller
@RequestMapping(value = "student_mng/")
public class StudentMngController {
 
	
	@Autowired
	private TeachStuService teachStuService;
	
	@ResponseBody
	@RequestMapping(value = "page")
	public JSONObject pageStudent(String param, String classNo, String grade, String no, String size,
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
    	String schoolInfoId = user.getTeachStu().getSchoolInfo().getSchoolInfoId();
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<TeachStu> page  = teachStuService.studentPage(schoolInfoId, classNo, grade, param, pageNo, pageSize);
		JSONObject dictJson = new JSONObject();
		List<Dict> dictList = DictUtil.dictCategoryList.get("班级");
		dictJson.put("班级", dictList);
		json.put("code", "0");
		json.put("data", page);
		json.put("dict", dictJson);
		return json;
	}
}
