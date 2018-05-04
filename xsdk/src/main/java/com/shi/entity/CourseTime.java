package com.shi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_course_time")
public class CourseTime {

	@Id
	@Column(name = "course_time_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String courseTimeId;


	@Column(name="course_time_no")
	private String coursetimeNo;
	
	@Column(name="week")
	private int week;
	
	@Column(name="start_time")
	private Date startTime;
	
	@Column(name="end_time")
	private Date endTime;

	public String getCourseTimeId() {
		return courseTimeId;
	}

	public void setCourseTimeId(String courseTimeId) {
		this.courseTimeId = courseTimeId;
	}

	public String getCoursetimeNo() {
		return coursetimeNo;
	}

	public void setCoursetimeNo(String coursetimeNo) {
		this.coursetimeNo = coursetimeNo;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
