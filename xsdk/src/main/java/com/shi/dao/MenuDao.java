package com.shi.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.shi.entity.Menu;


public interface MenuDao extends BaseDao<Menu, String> {
   
	public List<Menu> findByUrls_(String urls);
	
	public List<Map<String, Object>> findByUrls(String urls);
}
