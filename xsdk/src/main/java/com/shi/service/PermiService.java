package com.shi.service;

import java.util.List;

import com.shi.entity.Permi;
import com.shi.entity.User;

public interface PermiService {
	public List<Permi> findByUser(User user);
	
	public List<Permi> findAll();
	
	public List<Permi> HasNoPermis(List<Permi> hasPermiList);
}
