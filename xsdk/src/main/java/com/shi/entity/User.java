package com.shi.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")  
    @GenericGenerator(name = "uuid", strategy = "uuid")  
    private String id;
    
    @Column(name = "user_no")
    private String userNo;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "pwd")
    private String pwd;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "login_time")
    private Date loginTime;
    
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    
    @Column(name = "count")
    private Integer count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
    
	

    
}