package com.shi.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.Page;
import com.shi.dao.RoleMngDao;
import com.shi.entity.Dict;
import com.shi.entity.Role;
import com.shi.service.RoleMngService;

@Transactional
@Service
public class RoleMngServiceImpl implements RoleMngService {

	@Autowired
	private RoleMngDao roleMngDao;
	
	@Override
	public Serializable save(Role role){
		
		return roleMngDao.save(role);
	}
	
	@Override
	public List<Role> findExceptAdm(){
		String hql = " from Role where roleId!='0'";
		return roleMngDao.findList(hql);
	}
	
	@Override
	public List<Role> findAll(){
		
		return roleMngDao.findAll();
	}
	
	@Override
	public Role getById(String id){
		
		return roleMngDao.getById(id);
	}
	
	@Override
	public Page<Role> getPage(String param, int pageNo,
			int pageSize){
		
		StringBuffer hql = new StringBuffer("from Role u");
		Map<String, Object> params = new HashMap<String, Object>();
		if(param!=null&&!param.trim().equals("")){
			hql.append(" where u.roleName like:roleName");
			params.put("roleName", "%"+param+"%");
		}
		return roleMngDao.getPage(hql.toString(), params, pageNo, pageSize);
		
	}

}
