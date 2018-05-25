package com.shi.service;

import java.io.Serializable;
import java.util.List;

import com.shi.common.Page;
import com.shi.entity.Role;

public interface RoleMngService {

	public Serializable save(Role role);
	
	public List<Role> findExceptAdm();
	
	public List<Role> findAll();
	
	public Role getById(String id);
	
	public Page<Role> getPage(String param, int pageNo,
			int pageSize);
}
