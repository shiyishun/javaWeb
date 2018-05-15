package com.shi.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.dao.UserMngDao;
import com.shi.entity.SchoolInfo;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.entity.UserRoleRel;
import com.shi.service.UserMngService;

@Transactional
@Service
public class UserMngServiceImpl implements UserMngService {

	@Autowired
	UserMngDao userMngDao;

	@Override
	public User find() {

		return userMngDao.findAll().get(0);

	}

	@Override
	public Serializable save(User user) {
		return userMngDao.save(user);
	}

	@Override
	public Page<User> getSqlPage(String param,
			int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer("select u.user_no, u.user_name, u.phone, r.role_name, u.user_id " +
				" from tb_user u left join tb_user_role_rel urr on u.user_id=urr.user_id " +
				" left join tb_role r on urr.role_id=r.role_id ");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(param!=null&&!param.trim().equals("")){
			sql.append(" where u.phone like:phone or u.user_name like:userName");
			params.put("phone", "%"+param+"%");
			params.put("userName", "%"+param+"%");	
		}
		sql.append(" order by u.user_no asc");
		return userMngDao.getSqlPage(sql.toString(), params, pageNo, pageSize);
	}

	@Override
	public Page<User> getPage(String param,
			int pageNo, int pageSize) {
		StringBuffer hql = new StringBuffer("select u.userId, u.userNo, u.phone, u.userName " +
				"from User u");
		Map<String, Object> params = new HashMap<String, Object>();
		if(param!=null&&!param.trim().equals("")){
			hql.append(" where u.phone like:phone or u.userName like:userName");
			params.put("phone", "%"+param+"%");
			params.put("userName", "%"+param+"%");	
		}
		hql.append(" order by u.userNo asc");
		return userMngDao.getPage(hql.toString(), params, pageNo, pageSize);
	}
	
	
	@Override
	public User findByLoginName(String loginName) {

		return userMngDao.findByLoginName(loginName);
	}

	@Override
	public void update(User user) {

		userMngDao.update(user);
	}

	@Override
	public Map<String, Object> getUserInfo(String userId) {

		User user = userMngDao.getById(userId);
		Set<UserRoleRel> urrs = user.getUserRoleRelSet();
		String userName = user.getUserName();
		String phone = user.getPhone()!=null?user.getPhone():"";
		String email = user.getEmail()!=null?user.getEmail():"";
		String gender = "";
		String name = "";
		String no = "";
		String school = "";
		String colleage = "";
		String department = "";
		TeachStu teachStu = user.getTeachStu();
		if (teachStu != null) {
			gender = DictUtil.getKeyByValue(DictUtil.dictCategoryMap.get("性别"),
					teachStu.getGender());
			name = teachStu.getName();
			no = teachStu.getNo();
			SchoolInfo schoolInfo = teachStu.getSchoolInfo();
			if (schoolInfo != null) {
				school = schoolInfo.getSchool();
				colleage = schoolInfo.getCollege();
				department = schoolInfo.getDepartment();
			}
		}

		StringBuffer roles = new StringBuffer();
		for (UserRoleRel urr : urrs) {
			roles.append(urr.getRole().getRoleName() + ",");
		}
		roles.delete(roles.length() - 1, roles.length());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("phone", phone);
		map.put("email", email);
		map.put("roles", roles.toString());
		map.put("gender", gender);
		map.put("name", name);
		map.put("no", no);
		map.put("school", school);
		map.put("colleage", colleage);
		map.put("department", department);

		return map;
	}

}
