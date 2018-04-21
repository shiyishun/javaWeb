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
@Table(name = "tb_user_role_rel")
public class UserRoleRel {

    @Id
    @Column(name = "user_role_rel_id")
    @GeneratedValue(generator = "uuid")  
    @GenericGenerator(name = "uuid", strategy = "uuid")  
    private String userRoleRelId;
    
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "user_id")  
    private User user; 
    
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "role_id")  
    private Role role;

	public String getUserRoleRelId() {
		return userRoleRelId;
	}

	public void setUserRoleRelId(String userRoleRelId) {
		this.userRoleRelId = userRoleRelId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	} 
    
    
    
}
