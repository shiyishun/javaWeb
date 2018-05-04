package com.shi.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.shi.dao.MenuDao;
import com.shi.entity.Menu;

@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu, String> implements MenuDao {

	public List<Menu> findByUrls_(String urls){
		
		if(urls!=null){
			urls = urls.replace(",", "\",\"");
			urls = "(\"" +urls + "\")";
		}
		String sql = "select * from tb_menu where menu_url in" + urls;
		List<Map<String, Object>> queryList = this.findListBySql(sql);
		List<Menu> ListMenu = new ArrayList<Menu>();
		if(queryList!=null&&queryList.size()>0){
		   for(Map<String, Object> MenuMap: queryList){
			  System.out.println(MenuMap.toString());

		   }
		}
		
		return ListMenu;
	}
	
	public List<Map<String, Object>> findByUrls(String urls){
		
		if(urls!=null){
			urls = urls.replace(",", "\",\"");
			urls = "(\"" +urls + "\")";
		}
		String sql = "select * from tb_menu where menu_url in" + urls +" order by menu_no asc" ;
		List<Map<String, Object>> queryList = this.findListBySql(sql);
			
		return queryList;
	}
	
}
