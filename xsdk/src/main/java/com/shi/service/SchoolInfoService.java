package com.shi.service;

import java.util.List;

import com.shi.common.Page;
import com.shi.entity.SchoolInfo;


public interface SchoolInfoService {

	
    public void save(SchoolInfo schoolInfo);

    public List<SchoolInfo> findAll();
    
	public SchoolInfo getById(String id);
	
	public Page<SchoolInfo> getPage(String param, int pageNo,
			int pageSize);
	
    public void saveOrUpdate(SchoolInfo schoolInfo);
}
