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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_role")
public class Role {

	@Id
	@Column(name="role_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String roleId;

	@Column(name="parent_id")
	private String parentId;
	
	@Column(name="role_name")
	private String roleName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="create_time")
	private Date createTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")  
    private Set<UserRoleRel> userRoleRelSet = new HashSet<UserRoleRel>();  
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")  
    private Set<RolePermiRel> rolePermiRelSet = new HashSet<RolePermiRel>();  
   
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<UserRoleRel> getUserRoleRelSet() {
		return userRoleRelSet;
	}

	public void setUserRoleRelSet(Set<UserRoleRel> userRoleRelSet) {
		this.userRoleRelSet = userRoleRelSet;
	}

	public Set<RolePermiRel> getRolePermiRelSet() {
		return rolePermiRelSet;
	}

	public void setRolePermiRelSet(Set<RolePermiRel> rolePermiRelSet) {
		this.rolePermiRelSet = rolePermiRelSet;
	}


}
