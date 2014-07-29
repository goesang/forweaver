package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ReProject implements Serializable {

	static final long serialVersionUID = 4233234L;
	@Id
	private String name; //파생 프로젝트 이름 이게 기본 키
	private String description; // 파생 프로젝트 소개
	private Date openingDate; // 파생 프로젝트 시작일
	@DBRef
	private Weaver creator;
	
	@Transient
	private boolean isJoin;
	
	@DBRef
	private Project origianlProject;
	
	@DBRef
	private List<Weaver> adminWeavers = new ArrayList<Weaver>(); // 관리자들
	@DBRef
	private List<Weaver> joinWeavers = new ArrayList<Weaver>(); // 비 관리자 회원들
	
	public ReProject() {
		
	}
	
	public ReProject(String name, String description,
			Weaver weaver,Project origianlProject) {
		super();
		this.name = weaver.getId()+"/"+name;
		this.description = description;
		this.openingDate = new Date();
		this.creator = weaver;
		this.adminWeavers.add(weaver);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public String getCreatorName() {
		return this.creator.getId();
	}

	public String getCreatorEmail() {
		return this.creator.getEmail();
	}
	
	public Weaver getCreator() {
		return creator;
	}

	public void setCreator(Weaver creator) {
		this.creator = creator;
	}

	public String getOpeningDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
		return df.format(this.openingDate); 
	}



	public void addAdminWeaver(Weaver weaver){
		this.adminWeavers.add(weaver);
	}
	
	public void addJoinWeaver(Weaver weaver){
		this.joinWeavers.add(weaver);
	}

	public List<Weaver> getAdminWeavers() {
		return adminWeavers;
	}

	public void setAdminWeavers(List<Weaver> adminWeavers) {
		this.adminWeavers = adminWeavers;
	}

	public List<Weaver> getJoinWeavers() {
		return joinWeavers;
	}

	public void setJoinWeavers(List<Weaver> joinWeavers) {
		this.joinWeavers = joinWeavers;
	}
	
	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}
	public String getImgSrc(){
		return this.creator.getImgSrc();
	}
	
	public void removeJoinWeaver(Weaver weaver){
		this.joinWeavers.remove(weaver);
	}
	
	public String getChatRoomName(){
		return this.name.replace("/", "@");
	}

	public Project getOrigianlProject() {
		return origianlProject;
	}

	public void setOrigianlProject(Project origianlProject) {
		this.origianlProject = origianlProject;
	}
	
	
	
}
