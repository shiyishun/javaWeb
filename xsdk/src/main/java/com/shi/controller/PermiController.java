package com.shi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shi.common.Page;
import com.shi.entity.Permi;
import com.shi.entity.Role;
import com.shi.service.PermiService;




@Controller
@RequestMapping(value = "permi/")
public class PermiController {

	@Autowired
	private PermiService permiService;
	
	@ResponseBody
	@RequestMapping(value = "page")
	public JSONObject pageByRoleId(String param, String roleId, String no, String size) {
		JSONObject json = new JSONObject();
	
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
		if(roleId==null||roleId.equals("")){
			json.put("code", "10023");
			json.put("errmsg", "获取参数失败");
		}
		
		
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<Permi> page = permiService.getPage(param, roleId, pageNo, pageSize);

		json.put("code", "0");
		json.put("data", page);
		return json;
	}
	
}
