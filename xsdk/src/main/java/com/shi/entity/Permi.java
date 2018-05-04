package com.shi.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_permi")
public class Permi {

	@Id
	@Column(name = "permi_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String permiId;

	@Column(name = "permi_no")
	private String permiNo;

	@Column(name = "permi_name")
	private String permiName;

	@Column(name = "description")
	private String description;

	@Column(name = "req_url")
	private String reqUrl;

	@Column(name = "is_page")
	private Integer isPage;

	@Column(name = "is_menu")
	private Integer isMenu;

	@Column(name = "is_action")
	private Integer isAction;

	@Column(name = "create_time")
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "is_access")
	private Integer isAccess;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permi")
	private Set<RolePermiRel> rolePermiRelSet = new HashSet<RolePermiRel>();

	public String getPermiId() {
		return permiId;
	}

	public void setPermiId(String permiId) {
		this.permiId = permiId;
	}

	public String getPermiNo() {
		return permiNo;
	}

	public void setPermiNo(String permiNo) {
		this.permiNo = permiNo;
	}

	public String getPermiName() {
		return permiName;
	}

	public void setPermiName(String permiName) {
		this.permiName = permiName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public Integer getIsPage() {
		return isPage;
	}

	public void setIsPage(Integer isPage) {
		this.isPage = isPage;
	}

	public Integer getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}

	public Integer getIsAction() {
		return isAction;
	}

	public void setIsAction(Integer isAction) {
		this.isAction = isAction;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getIsAccess() {
		return isAccess;
	}

	public void setIsAccess(Integer isAccess) {
		this.isAccess = isAccess;
	}

	@JSONField(serialize = false)
	@JsonIgnore
	public Set<RolePermiRel> getRolePermiRelSet() {
		return rolePermiRelSet;
	}

	public void setRolePermiRelSet(Set<RolePermiRel> rolePermiRelSet) {
		this.rolePermiRelSet = rolePermiRelSet;
	}

}
