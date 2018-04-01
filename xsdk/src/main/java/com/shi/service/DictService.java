package com.shi.service;

import java.util.List;

import com.shi.entity.Dict;

public interface DictService {

	public List<Dict> findList();
	
	public void getCacheDict();
}
