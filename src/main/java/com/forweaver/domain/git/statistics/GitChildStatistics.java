package com.forweaver.domain.git.statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GitChildStatistics {
	private String userEmail;
	private int addLine;
	private int deleteLine;
	private Date commitDate;
	
	public GitChildStatistics(String userEmail, int addLine,
			int deleteLine, Date commitDate) {
		super();
		this.userEmail = userEmail;
		this.addLine = addLine;
		this.deleteLine = deleteLine;
		this.commitDate = commitDate;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public Date getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	
	public String getDate(){
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy/MM/dd");
		return formatter.format ( this.commitDate );
	}
	
	
}
