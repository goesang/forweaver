package com.forweaver.domain.git;

public class GitSimpleStatistics {
	private String username;
	private int addLine;
	private int deleteLine;
	private int commitCount;
	public GitSimpleStatistics(String username, int addLine, int deleteLine,
			int commitCount) {
		super();
		this.username = username;
		this.addLine = addLine;
		this.deleteLine = deleteLine;
		this.commitCount = commitCount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAddLine() {
		return addLine;
	}
	public void setAddLine(int addLine) {
		this.addLine = addLine;
	}
	public int getDeleteLine() {
		return deleteLine;
	}
	public void setDeleteLine(int deleteLine) {
		this.deleteLine = deleteLine;
	}
	public int getCommitCount() {
		return commitCount;
	}
	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}
	
	
}
