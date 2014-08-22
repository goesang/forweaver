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
public class Project implements Serializable {

	static final long serialVersionUID = 423123232123124234L;
	@Id
	private String name; //프로젝트 이름 이게 기본 키
	private int category; // 프로젝트 종류 값이 0이면 공개 프로젝트, 1이면 비공개 프로젝트, 2이면 팀프로젝트 포크.
	private String description; // 프로젝트 소개
	private Date openingDate; // 프로젝트 시작일
	private Date endDate; // 프로젝트 종료일
	private String originalProject;// 원본 프로젝트 이름
	@DBRef
	private Weaver creator;
	private int push; // 프로젝트 추천수
	
	@DBRef
	private List<Project> childProjects = new ArrayList<Project>(); // 파생 프로젝트 모음
	
	@Transient
	private boolean isJoin;
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private List<String> tags = new ArrayList<String>(); // 프로젝트의 태그 모음
	
	@DBRef
	private List<Weaver> adminWeavers = new ArrayList<Weaver>(); // 관리자들
	@DBRef
	private List<Weaver> joinWeavers = new ArrayList<Weaver>(); // 비 관리자 회원들
	
	public Project() {
		
	}
	
	public Project(String name, int category, String description, // 기본 생성자
			Weaver weaver,List<String> tagList) {
		super();
		this.name = weaver.getId()+"/"+name;
		this.category = category;
		this.description = description;
		this.openingDate = new Date();
		this.creator = weaver;
		this.adminWeavers.add(weaver);
		this.tags = tagList;
	}
	
	public Project(String name,Weaver weaver,Project originalProject) { //포크할 때 생성자
		super();
		this.name = weaver.getId()+"/"+name;
		this.category = 0;
		this.description = originalProject.getDescription();
		this.openingDate = new Date();
		this.creator = weaver;
		this.adminWeavers.add(weaver);
		if(originalProject.getOriginalProject() != null 
				&& originalProject.getOriginalProject().length() >0){
			this.tags.addAll(originalProject.getTags());
		}else{
			this.tags.add("@"+originalProject.getName());
			this.tags.addAll(originalProject.getTags());
		}
		originalProject.getChildProjects().add(this);
		this.originalProject = originalProject.getName();
		
	}
	
	public Project(String name,Weaver weaver,Repo repo,List<String> tags) { //팀 프로젝트 포크할 때 생성자
		super();
		this.name = weaver.getId()+"/"+name;
		this.category = 2;
		this.description = repo.getDescription();
		this.openingDate = new Date();
		this.endDate = repo.getDeadLine();
		this.creator = weaver;
		this.adminWeavers.add(weaver);
		this.tags.add("@"+repo.getLectureName()+"/"+repo.getName());
		this.tags.addAll(tags);
		repo.getChildProjects().add(this);
		this.originalProject = repo.getLectureName()+"/"+repo.getName();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}


	public int getPush() {
		return push;
	}

	public void setPush(int push) {
		this.push = push;
	}
	
	public void push(){
		this.push +=1;
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

	public String getOriginalProject() {
		return originalProject;
	}

	public void setOriginalProject(String originalProject) {
		this.originalProject = originalProject;
	}

	public List<Project> getChildProjects() {
		return childProjects;
	}

	public void setChildProjects(List<Project> childProjects) {
		this.childProjects = childProjects;
	}


	public int getDDay() {
		if(this.endDate == null)
			return -2;
		 long left =  this.endDate.getTime() - System.currentTimeMillis();
		 int leftDay = (int)Math.floor(left/(1000*60*60*24)+1);
		 if(leftDay < 0)
			 return -1;
		 
		 return leftDay;
	}
	
	public boolean isForkProject(){
		if(this.originalProject != null && this.originalProject.length() > 0)
			return true;
		return false;
	}
	
	
}
