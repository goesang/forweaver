package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document
public class Project implements Serializable {

	static final long serialVersionUID = 4231232323123124234L;
	@Id
	private String name;
	private int category;
	private String description;
	private Date openingDate;
	private String creatorName;
	private String creatorEmail;
	private Data image;
	private int push;
	
	private List<String> tags = new ArrayList<String>();
	
	private List<Weaver> weavers = new ArrayList<Weaver>();
	private List<Weaver> adminWeavers = new ArrayList<Weaver>();
	private List<Weaver> joinWeavers = new ArrayList<Weaver>();
	
	public Project() {
		
	}
	
	public Project(String name, int category, String description,
			int deadLine, Weaver weaver,List<String> tagList) {
		super();
		this.name = weaver.getId()+"/"+name;
		this.category = category;
		this.description = description;
		this.openingDate = new Date();
		this.creatorName = weaver.getId();
		this.creatorEmail = weaver.getEmail();
		this.image = weaver.getImage();
		this.adminWeavers.add(new Weaver(creatorName, creatorEmail));
		this.tags = tagList;
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
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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
	
	public String getOpeningDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
		return df.format(this.openingDate); 
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
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

	public List<Weaver> getWeavers() {
		return weavers;
	}

	public void setWeavers(List<Weaver> weavers) {
		this.weavers = weavers;
	}

	public Data getImage() {
		return image;
	}

	public void setImage(Data image) {
		this.image = image;
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
}
