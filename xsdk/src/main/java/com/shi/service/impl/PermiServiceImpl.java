package com.shi.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.entity.Permi;
import com.shi.entity.Role;
import com.shi.entity.RolePermiRel;
import com.shi.entity.User;
import com.shi.entity.UserRoleRel;
import com.shi.service.PermiService;

@Transactional
@Service
public class PermiServiceImpl implements PermiService {

	public List<Permi> findByUser(User user){
		List<Permi> permiList = new ArrayList<Permi>();
		Set<UserRoleRel> userRoleRelSet = user.getUserRoleRelSet();
		if (userRoleRelSet == null) {
          return permiList;
		}
		//获取用户对应的所有角色
		Set<Role> roleSet = new HashSet<Role>();
		for (UserRoleRel userRoleRel : userRoleRelSet) {  
			roleSet.add(userRoleRel.getRole());
		}  
		//获取角色对应的所有权限
		for(Role role: roleSet){
			//获取角色对应的权限关联
			Set<RolePermiRel> rolePermiRelSet = new HashSet<RolePermiRel>();
			rolePermiRelSet = role.getRolePermiRelSet();
			for(RolePermiRel rolePermiRel:rolePermiRelSet){
				permiList.add(rolePermiRel.getPermi());
			}
		}
		
		return permiList;
	}
	
}
