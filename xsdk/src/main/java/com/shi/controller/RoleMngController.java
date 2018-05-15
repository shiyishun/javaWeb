package com.shi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shi.entity.Dict;
import com.shi.entity.Role;
import com.shi.service.RoleMngService;



@Controller
@RequestMapping(value = "role_mng/")
public class RoleMngController {
  
	@Autowired
	private RoleMngService roleMngService;
	
	@ResponseBody
	@RequestMapping(value = "finda")
	public JSONObject findAll() {
		List<Role> roleList = roleMngService.findExceptAdm();
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", roleList);
		return json;
	}
	
	
	
}
