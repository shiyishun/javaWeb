package com.shi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.shi.entity.Dict;

public class DictUtil {

	/**
	 * 根据字典名称存储字典类型
	 */
	public static Map<String, Dict> dictMap = new HashMap<String, Dict>();
	
	/**
	 * 根据字典分类存储所在的数据字典
	 */
	public static Map<String, List<Dict>> dictCategoryList = new HashMap<String, List<Dict>>();


	/**
	 * 根据类别名称查询子字典，并返回以dictValue为key，dictName为value形式
	 * @param category
	 * @return
	 */
	public static Map<String, String> dictKeyValue(String category){
		
		Map<String, String> map = new HashMap<String, String>();
		List<Dict> dictList = dictCategoryList.get(category);
		for(Dict dict: dictList){
			map.put(dict.getDictValue(), dict.getDictName());
		}
		return map;
	}
	
	
	/**
	 * 根据value获取key
	 * @param category
	 * @param value
	 * @return
	 */
	public static String getKeyByValue(String category, String value) {
         
		String key = "";
		List<Dict> dictList = dictCategoryList.get(category);
		for(Dict dict: dictList){
			if(dict.getDictValue()!=null&&dict.getDictValue().equals(value)){
			key = dict.getDictName();
			break;
			}
		}
		return key;
	}

}