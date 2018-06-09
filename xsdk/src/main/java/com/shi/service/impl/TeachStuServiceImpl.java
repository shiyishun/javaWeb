package com.shi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.dao.TeachStuDao;
import com.shi.entity.TeachStu;
import com.shi.service.TeachStuService;


@Transactional
@Service
public class TeachStuServiceImpl implements TeachStuService {

	
	
	@Autowired
	private TeachStuDao teachStuDao;
	
	public Page<TeachStu> studentPage(String schoolInfoId, String classNo, String grade, String param,
			  int pageNo, int pageSize){
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("from TeachStu t where t.isTecacher=1 and t.user.status=0 ");
		if(schoolInfoId!=null&&schoolInfoId.trim().equals("")){
			hql.append(" and t.schoolInfo.schoolInfoId =:schoolInfoId");
			params.put("schoolInfoId", schoolInfoId);
		}
		if(classNo!=null&&!classNo.trim().equals("")){
			hql.append(" and t.classNo =:classNo");
			params.put("classNo", classNo);
		}
		if(grade!=null&&!grade.trim().equals("")){
			hql.append(" and t.grade =:grade");
			params.put("grade", grade);
		}
		
		if(param!=null&&!param.trim().equals("")){
			hql.append(" and (t.no like:no or t.name like:name)");
			params.put("no", "%"+param+"%");
			params.put("name", "%"+param+"%");
		}
		
		Page<TeachStu> page = teachStuDao.getPage(hql.toString(), params, pageNo, pageSize);
		List<TeachStu> list =  page.getList();
		for(int i=0; i<list.size(); i++){
			
			 list.get(i).setGenderStr(DictUtil.getKeyByValue("性别",
                     list.get(i).getGender().toString()));
			 list.get(i).setClassNoStr(DictUtil.getKeyByValue("班级", 
					 list.get(i).getClassNo().toString()));
			 
		}
		return teachStuDao.getPage(hql.toString(), params, pageNo, pageSize);
	}
}
