package com.forweaver.domain.git;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GitSimpleFileInfo implements Serializable {

	static final long serialVersionUID = 3333333333L;
	private String name;
	private String path;
	private int depth;
	private boolean isDirectory;
	private String commitID;
	private String simpleCommitLog;
	private Date commitDate;
	private int commitDateInt;
		
	public GitSimpleFileInfo(String name, String path, int depth,
			boolean isDirectory, String commitID, String simpleCommitLog,
			int commitDateInt) {
		this.name = name;
		this.path = "/"+path;
		this.depth = depth;
		this.isDirectory = isDirectory;
		this.commitID = commitID;
		this.simpleCommitLog = simpleCommitLog;
		this.commitDateInt = commitDateInt;
		this.commitDate = new Date(commitDateInt*1000L);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public boolean getIsDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public String getCommitID() {
		return commitID;
	}
	public void setCommitID(String commitID) {
		this.commitID = commitID;
	}
	public String getSimpleCommitLog() {
		return simpleCommitLog;
	}
	public void setSimpleCommitLog(String simpleCommitLog) {
		this.simpleCommitLog = simpleCommitLog;
	}
	public String getCommitDate() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
	    return sdf.format(this.commitDate);
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public int getCommitDateInt() {
		return commitDateInt;
	}
	public void setCommitDateInt(int commitDateInt) {
		this.commitDateInt = commitDateInt;
	}
	
	
}
