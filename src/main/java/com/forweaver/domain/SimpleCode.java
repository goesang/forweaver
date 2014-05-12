package com.forweaver.domain;

import java.io.Serializable;

import com.forweaver.util.WebUtil;

public class SimpleCode  implements Serializable {

	static final long serialVersionUID = 529134L;
	private String fileName;
	private String content;
	
	public SimpleCode(String fileName, String content) {
		super();
		this.fileName = fileName;
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContent() {
		return WebUtil.removeHtml(content);
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
