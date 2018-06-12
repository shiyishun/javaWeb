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
@Table(name = "tb_mark")
public class Mark {

	
	@Id
	@Column(name = "mark_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String markId;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name="daily_score")
	private Float dailScore;
	
	@Column(name="exam_score")
	private Float examScore;
	
	@Column(name="final_score")
	private Float finalScore;

	public String getMarkId() {
		return markId;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Float getDailScore() {
		return dailScore;
	}

	public void setDailScore(Float dailScore) {
		this.dailScore = dailScore;
	}

	public Float getExamScore() {
		return examScore;
	}

	public void setExamScore(Float examScore) {
		this.examScore = examScore;
	}

	public Float getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Float finalScore) {
		this.finalScore = finalScore;
	}
	
	
	
}
