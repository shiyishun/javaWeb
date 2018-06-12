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

import com.fasterxml.jackson.annotation.JsonIgnore;


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

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "course_time_id")  
    private CourseTime courseTime;
    
    
	public String getUserCourseRelId() {
		return userCourseRelId;
	}

	public void setUserCourseRelId(String userCourseRelId) {
		this.userCourseRelId = userCourseRelId;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@JsonIgnore
	public CourseTime getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(CourseTime courseTime) {
		this.courseTime = courseTime;
	}
    
    
    
}
