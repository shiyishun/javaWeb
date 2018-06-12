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
import com.shi.entity.CallTheroll;
import com.shi.entity.Dict;
import com.shi.entity.SchoolInfo;
import com.shi.entity.User;
import com.shi.service.CallTherollService;


@Controller
@RequestMapping(value = "call_theroll/")
public class CallTherollController {

	private static Logger logger = LogManager
			.getLogger(CallTherollController.class);
	
	@Autowired
	private CallTherollService callTherollService; 
	
	@ResponseBody
	@RequestMapping(value = "statics_page")
	public JSONObject getStaticPage(String param, String no, String size,	
			HttpServletRequest request, HttpServletResponse response){
		
		JSONObject json = new JSONObject();
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
		
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		String userId = user.getUserId();
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<CallTheroll> page = callTherollService.getStaticsPage(param, userId,pageNo, pageSize);

		Map<String, String> dictMap = DictUtil.dictKeyValue("星期");
		JSONObject dictJson = new JSONObject();
		dictJson.put("星期", dictMap);
			
		json.put("code", "0");
		json.put("data", page);
		json.put("dict", dictJson);

		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "page")
	public JSONObject getPage(String courseId, String courseTimeId, String  callOrder,
			String callState, String param, String no, String size,	
			HttpServletRequest request){
		
		JSONObject json = new JSONObject();
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<CallTheroll> page = null;
		if(!"".equals(courseId)&&!"".equals(courseTimeId)&&!"".equals(courseId)){
		  page = callTherollService.getPage(courseId,courseTimeId, callOrder,
				callState,param,pageNo, pageSize);
		}
		JSONObject dictJson = new JSONObject();
		Map<String, String> dictMap1 = DictUtil.dictKeyValue("班级");
		Map<String, String> dictMap2 = DictUtil.dictKeyValue("性别");
		Map<String, String> dictMap3 = DictUtil.dictKeyValue("签到状态");
		dictJson.put("班级", dictMap1);
		dictJson.put("性别", dictMap2);
		dictJson.put("签到状态", dictMap3);
		JSONObject dictListJson = new JSONObject();
		List<Dict> dictList = DictUtil.dictCategoryList.get("签到状态");
		dictListJson.put("签到状态", dictList);
		json.put("code", "0");
		json.put("data", page);
		json.put("dict", dictJson);
		json.put("dictList", dictListJson);

		return json;
	}
	
	
	
}
