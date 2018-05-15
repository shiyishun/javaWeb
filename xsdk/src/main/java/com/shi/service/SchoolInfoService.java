package com.shi.service;

import java.util.List;

import com.shi.entity.SchoolInfo;

public interface SchoolInfoService {

	
    public void save(SchoolInfo schoolInfo);

    public List<SchoolInfo> findAll();
}
