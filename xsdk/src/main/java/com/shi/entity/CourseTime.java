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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "tb_course_time")
public class CourseTime {

	@Id
	@Column(name = "course_time_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String courseTimeId;

	@Column(name="week")
	private int week;
	
	@Column(name="start_period")
	private int startPeriod;
	
	@Column(name="end_period")
	private int endPeriod;

	@Column(name = "class_location")
	private String classLocation;
	
	@Column(name = "class_shape")
	private String classShape;
	
	@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "courseTime")  
    private CourseTimeRel courseTimeRel = new CourseTimeRel();  
	
	
	public String getCourseTimeId() {
		return courseTimeId;
	}

	public void setCourseTimeId(String courseTimeId) {
		this.courseTimeId = courseTimeId;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(int startPeriod) {
		this.startPeriod = startPeriod;
	}

	public int getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(int endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getClassLocation() {
		return classLocation;
	}

	public void setClassLocation(String classLocation) {
		this.classLocation = classLocation;
	}

	public String getClassShape() {
		return classShape;
	}

	public void setClassShape(String classShape) {
		this.classShape = classShape;
	}

	public CourseTimeRel getCourseTimeRel() {
		return courseTimeRel;
	}

	public void setCourseTimeRel(CourseTimeRel courseTimeRel) {
		this.courseTimeRel = courseTimeRel;
	}

}
