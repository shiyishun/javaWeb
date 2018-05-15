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
	


	@ResponseBody
	@RequestMapping(value = "save")
	public JSONObject addDict(Dict dict) {
		
		
		if(dict.getDictId()!=null&&!dict.getDictId().equals(""))
		{  
			// 更新
			Dict dict_ = dictService.getById(dict.getDictId());
			dict_.setDescription(dict.getDescription());
			dict_.setDictValue(dict.getDictValue());
			dictService.update(dict_);	
		}else{
			// 添加
			if(dict.getParentId().trim().equals("")){
				dict.setParentId(null);
			}
			if(dict.getDictCategory().equals("无")){
				dict.setDictCategory(null);
			}
			dict.setDictNo(dictService.genNo(dict.getParentId()));
			
					
			dictService.save(dict);
			
		}
	
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", null);
		dictService.getCacheDict();
		return json;
	}
	
	/**
	 * 根据字典名获取所属字典值
	 * @param cateName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get")
	public JSONObject getById(String dictId) {
		Dict dict = dictService.getById(dictId);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", dict);
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
	
	
//	@ResponseBody
//	@RequestMapping(value = "delete")
//	public JSONObject delete() {
//		List<Dict> dictList = dictService.delete(dict);
//		JSONObject json = new JSONObject();
//		json.put("code", "0");
//		json.put("data", dictList);
//		return json;
//	}
	/**
	 * 查询缓存里的父字典
	 * @param cateName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findpc")
	public JSONObject findParentByCathe() {
		List<Dict> dictList = dictService.findParentByCache();
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", dictList);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "findl")
	public JSONObject findCategoryListByCathe(String params) {
		JSONObject dataJson = new JSONObject();
		if(params!=null){
		String[] param = params.split(",");
	
		for(String str: param){
			List<Dict> dictList = DictUtil.dictCategoryList.get(str);
			dataJson.put(str, dictList);
			}
		}
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", dataJson);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "test")
	public JSONObject test() {


	    int i = dictService.genNo(null);

		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", i);
		return json;
	}
}
