package com.shi.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.DictUtil;
import com.shi.dao.DictDao;
import com.shi.entity.Dict;
import com.shi.service.DictService;

@Transactional
@Service("dictService")
public class DictServiceImpl implements DictService {

	@Autowired
	private DictDao dictDao;

	public List<Dict> findList() {
		return dictDao.findAll();
	}

	public void getCacheDict() {
		List<Dict> DictList = new ArrayList<Dict>();
		DictList = dictDao.findList(" from Dict d order by d.dictCategory asc, d.dictNo asc");
	
		for(Dict dict:DictList){
			// 字典 名称-值对应map形式
			DictUtil.dictMap.put(dict.getDictName(), dict.getDictValue());
			// 字典类别
			if(dict.getDictCategory()!=null){  				
				List<Dict> DictList_ = new ArrayList<Dict>();
				if(DictUtil.dictCategoryList.get(dict.getDictCategory())!= null){
					DictList_ =  DictUtil.dictCategoryList.get(dict.getDictCategory());
				}
				DictList_.add(dict);
				DictUtil.dictCategoryList.put(dict.getDictCategory(), DictList_);
				
			}
		}
	}
	
	
	
	public Serializable save(Dict dict){
		
		Serializable id = dictDao.save(dict);    
		return id;
	}
	
	public List<Dict> findParent(){
		List<Dict> DictList = new ArrayList<Dict>();
		DictList = dictDao.findList(" from Dict d where d.parentId = null order by d.dictNo asc");
		return DictList;
		
	}

	public void delete(Dict dict){
		
		dictDao.delete(dict);
	}
	
}
