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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_permi")
public class Permi {

	
	@Id
	@Column(name="permi_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String permiId;
	
	@Column(name="permi_no")
	private String parentNo;
	
	@Column(name="permi_name")
	private String parentName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="req_url")
	private String reqUrl;
	
	@Column(name="is_page")
	private int isPage;
	
	@Column(name="is_menu")
	private int isMenu;
	
	@Column(name="is_action")
	private int isAction;
	
	@Column(name="create_time")
	private Date createTime;
	
	@ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "user_id")  
    private User user; 
	
	@Column(name="is_access")
	private int isAccess;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permi")  
    private Set<RolePermiRel> rolePermiRelSet = new HashSet<RolePermiRel>();  
	
	public String getPermiId() {
		return permiId;
	}

	public void setPermiId(String permiId) {
		this.permiId = permiId;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public int getIsPage() {
		return isPage;
	}

	public void setIsPage(int isPage) {
		this.isPage = isPage;
	}

	public int getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
	}

	public int getIsAction() {
		return isAction;
	}

	public void setIsAction(int isAction) {
		this.isAction = isAction;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getIsAccess() {
		return isAccess;
	}

	public void setIsAccess(int isAccess) {
		this.isAccess = isAccess;
	}
	
	
	
}
