package com.shi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_role_permi_rel")
public class RolePermiRel {
  
	
    @Id
    @Column(name = "role_permi_rel_id")
    @GeneratedValue(generator = "uuid")  
    @GenericGenerator(name = "uuid", strategy = "uuid")  
    private String rolePermiRelId;
    
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "role_id")  
    private Role role;
    
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "permi_id")  
    private Permi permi;

	public String getRolePermiRelId() {
		return rolePermiRelId;
	}

	public void setRolePermiRelId(String rolePermiRelId) {
		this.rolePermiRelId = rolePermiRelId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permi getPermi() {
		return permi;
	}

	public void setPermi(Permi permi) {
		this.permi = permi;
	}
    
    
    
}
