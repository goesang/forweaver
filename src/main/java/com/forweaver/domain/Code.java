package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Code implements Serializable  {
	
	static final long serialVersionUID = 3845364L;
	
	@Id
	private int codeID;
	@DBRef
	private Weaver writer;
	private int downCount;
	private Date openingDate;
	private String readme;
	private String name;
	private String content;
	private int rePostCount;
	private Date recentRePostDate;
	
	private List<SimpleCode> codes = new ArrayList<SimpleCode>();
	private List<String> tags = new ArrayList<String>();
	
	public Code(){}
	
	public Code(Weaver weaver, String name,
			String content, List<String> tags) {
		super();
		this.writer = weaver;
		this.name = name;
		this.content = content;
		this.tags = tags;
		this.openingDate = new Date();
	}

	public int getCodeID() {
		return codeID;
	}

	public void setCodeID(int codeID) {
		this.codeID = codeID;
	}

	
	public Weaver getWriter() {
		return writer;
	}

	public void setWriter(Weaver writer) {
		this.writer = writer;
	}

	public String getWriterName() {
		return this.writer.getId();
	}


	public String getWriterEmail() {
		return this.writer.getEmail();
	}


	public int getDownCount() {
		return downCount;
	}

	public void setDownCount(int downCount) {
		this.downCount = downCount;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public String getReadme() {
		return readme;
	}

	public void setReadme(String readme) {
		this.readme = readme;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<SimpleCode> getCodes() {
		return codes;
	}

	public void setCodes(List<SimpleCode> codes) {
		this.codes = codes;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getRePostCount() {
		return rePostCount;
	}

	public void setRePostCount(int rePostCount) {
		this.rePostCount = rePostCount;
	}

	public Date getRecentRePostDate() {
		return recentRePostDate;
	}

	public void setRecentRePostDate(Date recentRePostDate) {
		this.recentRePostDate = recentRePostDate;
	}
	
	public void addSimpleCode(SimpleCode simpleCode){
		this.codes.add(simpleCode);
	}
	
	public void addFirstSimpleCode(SimpleCode simpleCode){
		this.codes.add(0, simpleCode);
	}
	
	public String getImgSrc(){
		return this.writer.getImgSrc();
	}
	
	public void download(){
		this.downCount +=1;
	}
	
	public String getFormatCreated() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		return df.format(this.openingDate); 
	}
}
