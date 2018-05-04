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
@Table(name = "tb_user_course_rel")
public class UserCourseRel {

	
	@Id
	@Column(name = "user_course_rel_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String userCourseRelId;
	
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "user_id")  
    private User user; 
	
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "course_id")  
    private Course course;

	public String getUserCourseRelId() {
		return userCourseRelId;
	}

	public void setUserCourseRelId(String userCourseRelId) {
		this.userCourseRelId = userCourseRelId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
    
    
    
}
