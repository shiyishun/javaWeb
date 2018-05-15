package com.shi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shi.entity.SchoolInfo;
import com.shi.service.SchoolInfoService;



@Controller
@RequestMapping(value = "school_mng/")
public class SchoolInfoController {

	@Autowired
	private SchoolInfoService schoolInfoService;
	
	@ResponseBody
	@RequestMapping(value = "finda")
	public JSONObject findAll() {
		List<SchoolInfo> list = schoolInfoService.findAll();
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", list);
		return json;
	}
}
