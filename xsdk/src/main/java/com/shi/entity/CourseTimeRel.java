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
@Table(name = "tb_course_time_rel")
public class CourseTimeRel {

	@Id
	@Column(name = "course_time_rel_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String courseTimeRelId;

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "course_id")  
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "course_time_id")  
    private CourseTime courseTime;

	public String getCourseTimeRelId() {
		return courseTimeRelId;
	}

	public void setCourseTimeRelId(String courseTimeRelId) {
		this.courseTimeRelId = courseTimeRelId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseTime getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(CourseTime courseTime) {
		this.courseTime = courseTime;
	}
	
    
}
