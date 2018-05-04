package com.shi.service;

import java.util.List;
import java.util.Map;

import com.shi.entity.Menu;
import com.shi.entity.User;

public interface MenuService {

	public List<Menu> findAll();
	
	public List<Menu> findByUser_(User user);
	
	public List<Map<String, Object>> findByUser(User user);
	
}
