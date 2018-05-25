package com.shi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.Page;
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
	
	@Override
	public SchoolInfo getById(String id){
		
		return schoolInfoDao.getById(id);
	}
	
	@Override
	public Page<SchoolInfo> getPage(String param, int pageNo,
			int pageSize){
		
		StringBuffer hql = new StringBuffer("from SchoolInfo s ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(param!=null&&!param.trim().equals("")){
			hql.append(" where s.school like:school");
			params.put("school", "%"+param+"%");
		}
		hql.append(" order by s.schoolNo asc");
		return schoolInfoDao.getPage(hql.toString(), params, pageNo, pageSize);
		
	}
	
	@Override
    public void saveOrUpdate(SchoolInfo schoolInfo){
    	
    	schoolInfoDao.saveOrUpdate(schoolInfo);
    }
}
