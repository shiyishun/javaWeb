package com.shi.service;

import java.io.Serializable;
import java.util.List;

import com.shi.entity.Role;

public interface RoleMngService {

	public Serializable save(Role role);
	
	public List<Role> findAll();
}
