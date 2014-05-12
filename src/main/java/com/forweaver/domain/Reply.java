package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reply implements Serializable {

	static final long serialVersionUID = 121134L;

	private String writerName;
	private String writerEmail;
	private Date created;
	private String content;
	private int number;
	
	public Reply() {
	}
	
	public Reply(String writerName, String writerEmail, String content) {
		super();
		this.writerName = writerName;
		this.writerEmail = writerEmail;
		this.created = new Date();
		this.content = content;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getWriterEmail() {
		return writerEmail;
	}
	public void setWriterEmail(String writerEmail) {
		this.writerEmail = writerEmail;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getFormatCreated() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		return df.format(created); 
	}
	
}
