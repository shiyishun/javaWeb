package com.shi.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.common.DictUtil;
import com.shi.entity.Dict;
import com.shi.entity.Role;
import com.shi.entity.User;
import com.shi.entity.UserRoleRel;
import com.shi.service.DictService;




@Controller
@RequestMapping(value = "dict_mng/")
public class DictMngController {
    
	@Autowired
	private DictService dictService;
	
	private static Logger logger = LogManager.getLogger(DictMngController.class);
	
	/**
	 * 根据字典类别名获取所属字典
	 * @param cateName
	 * @return
	 */
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
	@RequestMapping(value = "add")
	public JSONObject addDict(Dict dict) {
		Serializable id = dictService.save(dict);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", id.toString());
		return json;
	}
	
	/**
	 * 根据字典名获取所属字典值
	 * @param cateName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get")
	public JSONObject getDictValueByName(String name) {
		String value= DictUtil.dictMap.get(name);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", value);
		return json;
	}
	
	/**
	 * 查询父字典
	 * @param cateName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findp")
	public JSONObject findParent() {
		List<Dict> dictList = dictService.findParent();
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", dictList);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "delete")
	public JSONObject delete() {
		List<Dict> dictList = dictService.findParent();
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", dictList);
		return json;
	}
	
	
	
}
