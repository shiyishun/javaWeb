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

import com.shi.common.DictUtil;
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
		List<Dict> dictList = DictUtil.dictCategoryList.get("性别"); 

		return dictList;
	}
}
