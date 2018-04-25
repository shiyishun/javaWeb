package com.shi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_dict")
public class Dict {

	@Id
	@Column(name = "dict_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String dictId;

	@Column(name = "dict_no")
	private String dictNo;

	@Column(name = "dict_name")
	private String dictName;
	
	@Column(name = "dict_value")
	private String dictValue;
	
	@Column(name = "dict_category")
	private String dictCategory;
	
	@Column(name = "parent_id")
	private String parentId;
	
	@Column(name = "description")
	private String description;

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictNo() {
		return dictNo;
	}

	public void setDictNo(String dictNo) {
		this.dictNo = dictNo;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictCategory() {
		return dictCategory;
	}

	public void setDictCategory(String dictCategory) {
		this.dictCategory = dictCategory;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDictDescription(String description) {
		this.description = description;
	}

}
