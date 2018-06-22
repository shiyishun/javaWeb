package com.shi.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
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
		StringBuffer sql = new StringBuffer("select u.user_no, u.user_name, u.phone, r.role_name, u.user_id, u.status " +
				" from tb_user u left join tb_user_role_rel urr on u.user_id=urr.user_id " +
				" left join tb_role r on urr.role_id=r.role_id ");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(param!=null&&!param.trim().equals("")){
			sql.append(" where u.phone like:phone or u.user_name like:userName");
			params.put("phone", "%"+param+"%");
			params.put("userName", "%"+param+"%");	
		}
		sql.append(" order by u.user_no asc");
		Page<User> p =  userMngDao.getSqlPage(sql.toString(), params, pageNo, pageSize);
		List l=  p.getList();
		for(int i=0; i<l.size(); i++){
			Object[] obj = (Object[]) l.get(i);	
			 obj[5]= DictUtil.getKeyByValue("用户状态", obj[5].toString());
		}
		return p;
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
		if(user==null){
			return null;
		}
		
		Set<UserRoleRel> urrs = user.getUserRoleRelSet();
		String userName = user.getUserName();
		String userNo = user.getUserNo().toString();
		String phone = user.getPhone()!=null?user.getPhone():"";
		String email = user.getEmail()!=null?user.getEmail():"";
		String status = String.valueOf(user.getStatus());
		String gender = "";
		String genderName = "";
		String name = "";
		String no = "";
		String school = "";
		String colleage = "";
		String department = "";
		String birthday = "";
		String major = "";
		String grade = "";
		String classNo = "";
		String schoolInfoId = "";
	
		TeachStu teachStu = user.getTeachStu();
		if (teachStu != null) {
			genderName = DictUtil.getKeyByValue("性别",
					teachStu.getGender().toString());
			gender = teachStu.getGender().toString();
			name = teachStu.getName();
			no = teachStu.getNo();
			if(teachStu.getBirthday()!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				birthday = sdf.format(teachStu.getBirthday()); 	
			}
			major = teachStu.getMajor();
			classNo = DictUtil.getKeyByValue("班级",
						teachStu.getClassNo());
			grade = teachStu.getGrade();		
			SchoolInfo schoolInfo = teachStu.getSchoolInfo();
			if (schoolInfo != null) {
				schoolInfoId = teachStu.getSchoolInfo().getSchoolInfoId();
				school = schoolInfo.getSchool();
				colleage = schoolInfo.getCollege();
				department = schoolInfo.getDepartment();
			}
		}

		StringBuffer roleNames = new StringBuffer();
		StringBuffer roleIds = new StringBuffer();
		if(urrs.size()>0){
		  for (UserRoleRel urr : urrs) {
			 roleNames.append(urr.getRole().getRoleName() + ",");
			 roleIds.append(urr.getRole().getRoleId() + ",");
		   }
		   roleNames.delete(roleNames.length() - 1, roleNames.length());
		   roleIds.delete(roleIds.length() - 1, roleIds.length());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userId", userId);
		map.put("userNo", userNo);
		map.put("userName", userName);
		map.put("phone", phone);
		map.put("email", email);
		map.put("status", status);
		map.put("roleNames", roleNames.toString());
		map.put("roleIds", roleIds.toString());
		map.put("gender", gender);
		map.put("genderName", genderName);
		map.put("name", name);
		map.put("no", no);
		map.put("school", school);
		map.put("colleage", colleage);
		map.put("department", department);
		map.put("birthday", birthday);
		map.put("major", major);
		map.put("grade", grade);
		map.put("schoolInfoId", schoolInfoId);
		map.put("classNo", classNo);

		return map;
	}

	
	public User getById(String id) {
		return userMngDao.getById(id);
	}
	
	public List<User> findStuUser(){
		StringBuffer hql = new StringBuffer(
				"from User u where u.teachStu.isTeacher=1 "
						+ "and (u.status=0 or u.status=1)");
		return userMngDao.findList(hql.toString());
	}
	
}
