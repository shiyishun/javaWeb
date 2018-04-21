package com.shi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.entity.Dict;
import com.shi.entity.User;
import com.shi.service.UserMngService;
@Controller
@RequestMapping(value = "stu_mng/")
public class UserMngController {
	@Autowired
	private UserMngService userService;

	@ResponseBody
	@RequestMapping(value = "save")
	public List<Dict>  save() {
      
//		User user = new User();
//		user.setUserNo("0");
//		user.setUserName("张三");
//		user.setPhone("18060905555");
//		userService.save(user);
		
//		Page<User> page = userService.getPage("", null, 1, 5);
		
		
		List<Dict> dictList = DictUtil.dictCategoryList.get("性别"); 

		return dictList;
	}
	
	@ResponseBody
	@RequestMapping(value = "page")
	public Page<User> page() {
      
		
		Page<User> page = userService.getPage("", null, 1, 5);
	
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value = "json")
	public JSONObject json() {
      
		
		Page<User> page = userService.getPage("", null, 1, 5);
	    JSONObject json = new JSONObject();
	    json.put("code", "0");
	    json.put("data", page);
		return json;
	}
	
}
