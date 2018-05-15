package com.shi.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "uuid")  
    @GenericGenerator(name = "uuid", strategy = "uuid")  
    private String userId;
    
    @Column(name = "user_no")
    private Long userNo;

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
    private Long count;

    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")  
    private Set<UserRoleRel> userRoleRelSet = new HashSet<UserRoleRel>();  
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")  
    private Set<UserCourseRel> userCourseRelSet = new HashSet<UserCourseRel>();  
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")  
    private Set<Mark> markSet = new HashSet<Mark>();
    
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")  
    private TeachStu teachStu = new TeachStu();
    
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
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


    public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@JsonIgnore
	public Set<UserRoleRel> getUserRoleRelSet() {
		return userRoleRelSet;
	}

	public void setUserRoleRelSet(Set<UserRoleRel> userRoleRelSet) {
		this.userRoleRelSet = userRoleRelSet;
	}

	@JsonIgnore
	public Set<UserCourseRel> getUserCourseRelSet() {
		return userCourseRelSet;
	}

	public void setUserCourseRelSet(Set<UserCourseRel> userCourseRelSet) {
		this.userCourseRelSet = userCourseRelSet;
	}

	public Set<Mark> getMarkSet() {
		return markSet;
	}

	public void setMarkSet(Set<Mark> markSet) {
		this.markSet = markSet;
	}

	public TeachStu getTeachStu() {
		return teachStu;
	}

	public void setTeachStu(TeachStu teachStu) {
		this.teachStu = teachStu;
	}

}