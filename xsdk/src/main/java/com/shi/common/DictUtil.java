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
	 * 根据字典分类存储所在的数据字典Map
	 */
	public static Map<String, HashMap<String, Object>> dictCategoryMap = new HashMap<String, HashMap<String, Object>>();

	/**
	 * 通过value查找key
	 * @param map
	 * @param value
	 * @return
	 */
	public static String getKeyByValue(HashMap<String, Object> map, Object value) {

		String key = "";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object obj = entry.getValue();
			if (obj != null && obj.equals(value)) {
				key = (String) entry.getKey();
			}
		}
		return key;
	}

}