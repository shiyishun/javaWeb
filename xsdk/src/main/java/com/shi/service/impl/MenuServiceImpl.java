package com.shi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shi.dao.MenuDao;
import com.shi.dao.PermiDao;
import com.shi.entity.Menu;
import com.shi.entity.Permi;
import com.shi.entity.User;
import com.shi.service.MenuService;
import com.shi.service.PermiService;

@Transactional
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private PermiService permiService;

	public List<Menu> findAll() {

		return menuDao.findAll();

	}

	/**
	 * 查询用户所拥有的权限的菜单...
	 */
	public List<Menu> findByUser_(User user) {

		List<Permi> permiList = permiService.findByUser(user);
		List<Menu> menuList = new ArrayList<Menu>();
		if (permiList != null && permiList.size() > 0) {
			StringBuffer strb = new StringBuffer();
			for (Permi permi : permiList) {
				if (permi.getIsMenu() == 0 && permi.getIsAccess() == 0) {
					strb.append(permi.getReqUrl()).append(",");
				}
			}
			
			if (strb.length() > 0) {
				strb.delete(strb.length() - 1, strb.length());
				menuList = menuDao.findByUrls_(strb.toString());
			}
		}

		return menuList;
	}
	
	/**
	 * 查询用户所拥有的权限的菜单
	 */
	public List<Map<String, Object>> findByUser(User user){
		
		List<Permi> permiList = permiService.findByUser(user); 
		List<Map<String, Object>> menuMapList = new ArrayList<Map<String, Object>>();
		if (permiList != null && permiList.size() > 0) {
			StringBuffer strb = new StringBuffer();
			for (Permi permi : permiList) {
				if (permi.getIsMenu() == 0 && permi.getIsAccess() == 0) {
					strb.append(permi.getReqUrl()).append(",");
				}
			}
			
			if (strb.length() > 0) {
				strb.delete(strb.length() - 1, strb.length());
				menuMapList = menuDao.findByUrls(strb.toString());
			}
		}
		return menuMapList;
	}

}
