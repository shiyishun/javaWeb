package com.shi.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_course")
public class Course {
	
	@Id
	@Column(name = "course_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String courseId;

	@Column(name="course_no")
	private String courseNo;
	
	@Column(name="course_name")
	private String courseName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="term")
	private String term;
	
	@Column(name="class_location")
	private String classLocation;
	
	@Column(name="class_date")
	private String classDate;
	
	@Column(name="class_order")
	private String classOrder;
		
	@Column(name="daily_weight")
	private Double dailyWeight;
	
	@Column(name="final_weight")
	private Double finalWeight;
	
	@Column(name="picket_line")
	private Integer picketLine;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
	private Set<UserCourseRel> userCourseRelSet = new HashSet<UserCourseRel>();  
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")  
	private Set<CourseTimeRel> courseTimeRelSet = new HashSet<CourseTimeRel>();  
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")  
	private Set<CallTheroll> callTheroll = new HashSet<CallTheroll>();  
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")  
	private Set<Mark> mark = new HashSet<Mark>();  
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getClassLocation() {
		return classLocation;
	}

	public void setClassLocation(String classLocation) {
		this.classLocation = classLocation;
	}

	public String getClassDate() {
		return classDate;
	}

	public void setClassDate(String classDate) {
		this.classDate = classDate;
	}

	public String getClassOrder() {
		return classOrder;
	}

	public void setClassOrder(String classOrder) {
		this.classOrder = classOrder;
	}

	public Double getDailyWeight() {
		return dailyWeight;
	}

	public void setDailyWeight(Double dailyWeight) {
		this.dailyWeight = dailyWeight;
	}

	public Double getFinalWeight() {
		return finalWeight;
	}

	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}

	public Integer getPicketLine() {
		return picketLine;
	}

	public void setPicketLine(Integer picketLine) {
		this.picketLine = picketLine;
	}

    @JsonIgnore
	public Set<UserCourseRel> getUserCourseRelSet() {
		return userCourseRelSet;
	}

	public void setUserCourseRelSet(Set<UserCourseRel> userCourseRelSet) {
		this.userCourseRelSet = userCourseRelSet;
	}

	@JsonIgnore
	public Set<CourseTimeRel> getCourseTimeRelSet() {
		return courseTimeRelSet;
	}

	public void setCourseTimeRelSet(Set<CourseTimeRel> courseTimeRelSet) {
		this.courseTimeRelSet = courseTimeRelSet;
	}

	@JsonIgnore
	public Set<CallTheroll> getCallTheroll() {
		return callTheroll;
	}

	public void setCallTheroll(Set<CallTheroll> callTheroll) {
		this.callTheroll = callTheroll;
	}

	@JsonIgnore
	public Set<Mark> getMark() {
		return mark;
	}

	public void setMark(Set<Mark> mark) {
		this.mark = mark;
	}
	
}
