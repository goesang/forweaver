package com.forweaver.domain.git.statistics;

public class GitTotalStatistics {
	private int totalAdd;
	private int totalDelete;
	private int totalCommit;
	
	public GitTotalStatistics(int totalAdd, int totalDelete, int totalCommit) {
		super();
		this.totalAdd = totalAdd;
		this.totalDelete = totalDelete;
		this.totalCommit = totalCommit;
	}
	public int getTotalAdd() {
		return totalAdd;
	}
	public void setTotalAdd(int totalAdd) {
		this.totalAdd = totalAdd;
	}
	public int getTotalDelete() {
		return totalDelete;
	}
	public void setTotalDelete(int totalDelete) {
		this.totalDelete = totalDelete;
	}
	public int getTotalCommit() {
		return totalCommit;
	}
	public void setTotalCommit(int totalCommit) {
		this.totalCommit = totalCommit;
	}
	
}
