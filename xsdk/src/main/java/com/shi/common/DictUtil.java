package com.shi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.shi.entity.Dict;



public class DictUtil{
	
	
    /**
     * 根据字典名称存储字典值
     */
    public static Map<String, String> dictMap = new HashMap<String, String>();

    /**
     * 根据字典分类存储所在的数据字典  
     */
    public static Map<String, List<Dict>> dictCategoryList = new HashMap<String, List<Dict>>();
    
    
    /**
     * 根据字典分类存储所在的数据字典Map   
     */
    public static Map<String, HashMap<String, Object>> dictCategoryMap = new HashMap<String, HashMap<String, Object>>();
    
}