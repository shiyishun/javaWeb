package com.shi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.common.Page;
import com.shi.dao.PermiDao;
import com.shi.entity.Permi;
import com.shi.entity.Role;
import com.shi.entity.RolePermiRel;
import com.shi.entity.User;
import com.shi.entity.UserRoleRel;
import com.shi.service.PermiService;

@Transactional
@Service
public class PermiServiceImpl implements PermiService {

	@Autowired
	private PermiDao permiDao;

	/**
	 * 获取用户的拥有的权限
	 * 
	 * @param user
	 * @return
	 */
	public List<Permi> findByUser(User user) {
		List<Permi> permiList = new ArrayList<Permi>();
		Set<UserRoleRel> userRoleRelSet = user.getUserRoleRelSet();
		if (userRoleRelSet == null) {
			return permiList;
		}
		// 获取用户对应的所有角色
		Set<Role> roleSet = new HashSet<Role>();
		for (UserRoleRel userRoleRel : userRoleRelSet) {
			roleSet.add(userRoleRel.getRole());

		}
		// 获取角色对应的所有权限
		for (Role role : roleSet) {
			// 获取角色对应的权限关联
			Set<RolePermiRel> rolePermiRelSet = new HashSet<RolePermiRel>();
			rolePermiRelSet = role.getRolePermiRelSet();
			for (RolePermiRel rolePermiRel : rolePermiRelSet) {
				permiList.add(rolePermiRel.getPermi());
				// System.out.println(rolePermiRel.getPermi().getPermiName());
			}
		}
		return permiList;
	}

	public List<Permi> findAll() {

		return permiDao.findAll();
	}

	/**
	 * 查询未拥有的权限
	 * 
	 * @param
	 * @return
	 */
	public List<Permi> HasNoPermis(List<Permi> hasPermiList) {

		List<Permi> allPermiList = this.findAll();
		allPermiList.removeAll(hasPermiList);
		for (Permi permi : allPermiList) {
			// 去掉不可访问的权限
			if (permi.getIsAccess() != 0) {
				allPermiList.remove(permi);
			}
		}
		return allPermiList;
	}

	public Page<Permi> getPage(String param, String roleId, int pageNo,
			int pageSize) {

		Map<String, Object> params = new HashMap<String, Object>();

		StringBuffer hql = new StringBuffer();

		if (roleId != null && !"".equals(roleId)) {

			hql = new StringBuffer("select p from RolePermiRel rpr, "
					+ " Permi p where rpr.permi.permiId = p.permiId "
					+ " and rpr.role.roleId =:roleId");
			params.put("roleId", roleId);
		} else {
			hql = new StringBuffer("from Permi p where 1=1 ");

		}

		if (param != null && !param.trim().equals("")) {
			hql.append(" and p.permiName like:permiName");
			params.put("permiName", "%" + param + "%");
		}
		return permiDao.getPage(hql.toString(), params, pageNo, pageSize);
	}

}
