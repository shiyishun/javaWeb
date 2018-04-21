package com.shi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "tb_menu")
public class Menu {

	@Id
	@Column(name="menu_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String menuId;
	
	@Column(name="parent_id")
	private String parentId;
	
	@Column(name="menu_name")
	private String menuName;
	
	@Column(name="menu_no")
	private String menuNo;
	
	@Column(name="lever")
	private int lever;
	
	@Column(name="menu_url")
	private String menuUrl;
	
	@Column(name="is_leaf")
	private int isLeaf;
	
	@Column(name="menu_icon")
	private String menuIcon;
	
	@Column(name="is_visable")
	private int isVisable;
	
	@Column(name="create_time")
	private Date createTime;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public int getLever() {
		return lever;
	}

	public void setLever(int lever) {
		this.lever = lever;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public int getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public int getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(int isVisable) {
		this.isVisable = isVisable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
