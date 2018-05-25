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
import com.shi.common.Page;
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
			DictUtil.dictMap.put(dict.getDictName(), dict);
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

	public List<Dict> findParentByCache(){
		List<Dict> DictList = new ArrayList<Dict>();
		for (Dict dict : DictUtil.dictMap.values()) { 
		  if(dict.getParentId()==null){
			  DictList.add(dict);  
		  }
	    }
		return DictList;
	}
	
	public void delete(Dict dict){
		
		dictDao.delete(dict);
	}
	
	public Dict getById(String dictId){
		
		return dictDao.getById(dictId);
	}
	
	
	public void update(Dict dict){
		
		dictDao.update(dict);
	}
	
	/**
	 * 生成字典编号规则
	 */
	public int genNo(String parentId){
		StringBuffer sql = new StringBuffer("select max(dict_no) as no from tb_dict");
		int result = 0;
		List<Map<String, Object>> reslutList = new ArrayList<Map<String, Object>>();
		// 父类
		if(parentId==null){
			sql.append(" where parent_id is null");
			reslutList = dictDao.findListBySql(sql.toString());
			if(reslutList.get(0)!=null){
				result = (Integer)reslutList.get(0).get("no")+100;
			}else{// 数据表为空
				result = 100;				
			}
	    // 父类及子类
		}else{
			sql.append(" where dict_id='"+ parentId+"' or parent_id = '"+ parentId+"'");
			reslutList = dictDao.findListBySql(sql.toString());
			if(reslutList.get(0)!=null){
			  result = (Integer)reslutList.get(0).get("no")+1;
			}else{ // parentId值错误
				result = 0;
			}
		}
		return result;

	}
	
	public Page<Dict> getPage(String param, int pageNo,
			int pageSize){
		
		StringBuffer hql = new StringBuffer("from Dict d");
		Map<String, Object> params = new HashMap<String, Object>();
		if(param!=null&&!param.trim().equals("")){
			hql.append(" where d.dictName like:dictName or d.dictCategory like:dictCategory");
			params.put("dictName", "%"+param+"%");
			params.put("dictCategory", "%"+param+"%");	
		}
		hql.append(" order by d.dictNo asc");
		return dictDao.getPage(hql.toString(), params, pageNo, pageSize);
		
	}

	
	
}
