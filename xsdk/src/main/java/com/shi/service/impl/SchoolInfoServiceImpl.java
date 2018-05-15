package com.shi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.SchoolInfoDao;
import com.shi.entity.SchoolInfo;
import com.shi.service.SchoolInfoService;


@Transactional
@Service
public class SchoolInfoServiceImpl implements SchoolInfoService {
    
	@Autowired
	private SchoolInfoDao schoolInfoDao;
	
	@Override
    public void save(SchoolInfo schoolInfo){
    	
    	schoolInfoDao.save(schoolInfo);
    }
	
	@Override
    public List<SchoolInfo> findAll(){
    	
    	List<SchoolInfo> list = schoolInfoDao.findAll();
    	return list;
    }
}
