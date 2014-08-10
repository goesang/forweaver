package com.forweaver.domain.git.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GitParentStatistics {
	private List<GitChildStatistics> gitChildStatistics; 
	private HashMap<String, GitTotalStatistics> userHashMap; 

	public GitParentStatistics() {
		super();
		this.gitChildStatistics = new ArrayList<GitChildStatistics>();
		this.userHashMap = new HashMap<String, GitTotalStatistics>();
	}

	public void addGitChildStatistics(GitChildStatistics gcs){
		if(gitChildStatistics.size() > 0){
			GitChildStatistics lastGCS = gitChildStatistics.get(gitChildStatistics.size()-1);
			
			if(lastGCS.getDate().equals(gcs.getDate()) 
					&& lastGCS.getUserEmail().equals(gcs.getUserEmail())){
				gcs.setAddLine(gcs.getAddLine()+lastGCS.getAddLine());
				gcs.setDeleteLine(gcs.getDeleteLine()+lastGCS.getDeleteLine());
				gitChildStatistics.remove(gitChildStatistics.size()-1);
			}
		}
		gitChildStatistics.add(gcs);
		GitTotalStatistics gts = this.userHashMap.get(gcs.getUserEmail());
		if(gts == null)
			this.userHashMap.put(gcs.getUserEmail(), 
					new GitTotalStatistics(gcs.getAddLine(), gcs.getDeleteLine(), 1));
		else{
			gts.setTotalAdd(gts.getTotalAdd()+gcs.getAddLine());
			gts.setTotalDelete(gts.getTotalDelete()+gcs.getDeleteLine());
			gts.setTotalCommit(gts.getTotalCommit()+1);
		}
	}

	public List<GitChildStatistics> getGitChildStatistics() {
		return gitChildStatistics;
	}

	public void setGitChildStatistics(
			List<GitChildStatistics> gitChildrenStatistics) {
		this.gitChildStatistics = gitChildrenStatistics;
	}

	public HashMap<String, GitTotalStatistics> getUserHashMap() {
		return userHashMap;
	}

	public void setUserHashMap(HashMap<String, GitTotalStatistics> userHashMap) {
		this.userHashMap = userHashMap;
	}




}
