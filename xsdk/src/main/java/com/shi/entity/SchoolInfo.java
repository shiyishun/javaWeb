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
@Table(name = "tb_school_info")
public class SchoolInfo {

	
	@Id
	@Column(name="school_info_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String schoolInfoId;
	
	@Column(name="school_no")
	private String schoolNo;
	
	@Column(name="school")
	private String school;
	
	@Column(name="college")
	private String college;
	
	@Column(name="department")
	private String department;
	
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "schoolInfo")  
    private Set<TeachStu> teachStu = new HashSet<TeachStu>();

	public String getSchoolInfoId() {
		return schoolInfoId;
	}

	public void setSchoolInfoId(String schoolInfoId) {
		this.schoolInfoId = schoolInfoId;
	}

	public String getSchoolNo() {
		return schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
    
	@JsonIgnore
	public Set<TeachStu> getTeachStu() {
		return teachStu;
	}

	public void setTeachStu(Set<TeachStu> teachStu) {
		this.teachStu = teachStu;
	}
	

}


