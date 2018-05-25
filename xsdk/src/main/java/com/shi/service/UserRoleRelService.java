package com.shi.service;

import java.io.Serializable;

import com.shi.entity.UserRoleRel;

public interface UserRoleRelService {

	public Serializable save(UserRoleRel userRoleRel);
	
	public void addNew(UserRoleRel userRoleRel);
	
	public void del(UserRoleRel userRoleRel);
}
