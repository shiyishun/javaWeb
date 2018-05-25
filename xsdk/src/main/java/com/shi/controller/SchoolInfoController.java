package com.shi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shi.common.Page;
import com.shi.entity.Dict;
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
	
	@ResponseBody
	@RequestMapping(value = "page")
	public JSONObject page(String param, String no, String size) {
        
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
		
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<SchoolInfo> page = schoolInfoService.getPage(param, pageNo, pageSize);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", page);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "save")
	public JSONObject save(SchoolInfo schoolInfo) {
		
		JSONObject json = new JSONObject();
		
		if(schoolInfo.getSchoolNo()==null||schoolInfo.getSchoolNo().equals("")){
			json.put("code", "2000");
			json.put("errmsg", "学校编号不能为空");
			return json;
		}
		
		if(schoolInfo.getSchool()==null||schoolInfo.getSchool().equals("")){
			json.put("code", "2001");
			json.put("errmsg", "学校名称不能为空");
			return json;
		}
		
		if(schoolInfo.getCollege()==null||schoolInfo.getCollege().equals("")){
			json.put("code", "2002");
			json.put("errmsg", "学院不能为空");
			return json;
		}
		if("".equals(schoolInfo.getSchoolInfoId().trim())){
			schoolInfo.setSchoolInfoId(null);
		}
		schoolInfoService.saveOrUpdate(schoolInfo);
      
		json.put("code", "0");
		json.put("data", null);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "get")
	public JSONObject get(String schoolInfoId) {

		JSONObject json = new JSONObject();
		SchoolInfo schoolInfo = schoolInfoService.getById(schoolInfoId);
		json.put("code", "0");
		json.put("data", schoolInfo);
		return json;
		
	}
	
}
