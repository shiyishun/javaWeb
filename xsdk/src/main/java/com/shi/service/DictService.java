package com.shi.service;

import java.io.Serializable;
import java.util.List;

import com.shi.common.Page;
import com.shi.entity.Dict;
import com.shi.entity.User;

public interface DictService {

	public List<Dict> findList();
	
	public void getCacheDict();
	
	public Serializable save(Dict dict);
	
	public Dict getById(String dictId);
	
	public List<Dict> findParent();
	
	public void delete(Dict dict);
	
	public List<Dict> findParentByCache();
	
	public void update(Dict dict);
	
	public int genNo(String parentId);
	
	public Page<Dict> getPage(String param, int pageNo,
			int pageSize);
	
}
