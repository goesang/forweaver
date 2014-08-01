package com.forweaver.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.apache.commons.codec.binary.Base64;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document
public class Data implements Serializable {
	
	static final long serialVersionUID = 342343L;
	@Id
	private String id;
	private String weaverID;
	private byte[] content;
	private String name;
	private String type;
	private Date date;
	
	public Data(){
		
	}
	
	public Data(String id ,MultipartFile data,String weaverID){
		this.date = new Date();
		this.id = id;
		this.weaverID = weaverID;
		this.name= "";
		try{
			this.content= data.getBytes();
			this.name = data.getOriginalFilename();
			this.type = data.getContentType();
		}catch(Exception e){
			
		}
	}
	
	public Data(MultipartFile data,String weaverID){
		this.date = new Date();
		this.id = new ObjectId(this.date).toString();
		this.weaverID = weaverID;
		this.name= "";
		try{
			this.content= data.getBytes();
			this.name = data.getOriginalFilename();
			this.type = data.getContentType();
		}catch(Exception e){
			
		}
	}
	
	
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getOutPutStream(){
		return new Base64().encodeToString(this.content);
	}

	public String getWeaverID() {
		return weaverID;
	}

	public void setWeaverID(String weaverID) {
		this.weaverID = weaverID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
