package com.shi.entity;

import java.util.Date;

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
@Table(name = "tb_call_theroll")
public class CallTheroll {

	@Id
	@Column(name = "call_theroll_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String callTherollId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@Column(name="course_name")
	private String courseName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name="stu_no")
	private String stuNo;
	
	@Column(name="stu_name")
	private String stuName;
	
	@Column(name="call_state")
	private int callState;
	
	@Column(name="call_date")
	private Date callDate;
	
	@Column(name="call_position")
	private String callPosition;

	public String getCallTherollId() {
		return callTherollId;
	}

	public void setCallTherollId(String callTherollId) {
		this.callTherollId = callTherollId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public int getCallState() {
		return callState;
	}

	public void setCallState(int callState) {
		this.callState = callState;
	}
	
	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getCallPosition() {
		return callPosition;
	}

	public void setCallPosition(String callPosition) {
		this.callPosition = callPosition;
	}
	
	
}
