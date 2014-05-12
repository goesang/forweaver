package com.forweaver.domain.git;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateCommitCount {
		private Date commitDate;
		private List<UserCommitCount> userCommitList;
		
		public DateCommitCount(String stringDate){
			try{
			this.commitDate = new SimpleDateFormat("yyyy-mm-dd").parse(stringDate);
			}catch(Exception e){
				this.commitDate = new Date();
			}
			this.userCommitList = new ArrayList<UserCommitCount>();
		}
		public void addUserCommitCount(UserCommitCount userCommitCount){
			boolean exist = false;
					
			for(UserCommitCount tmpUserCommit:userCommitList){
				if(tmpUserCommit.getUserName().equals(userCommitCount.getUserName())){
					
					tmpUserCommit.plusCommitCount();
					tmpUserCommit.plusInsertCount(userCommitCount.getInsertCount());
					tmpUserCommit.plusDeleteCount(userCommitCount.getDeleteCount());
					tmpUserCommit.plusFileChangeCount(userCommitCount.getFileChangeCount());
					exist = true;
				}
			}
			if(!exist)
				userCommitList.add(userCommitCount);
		}
		public Date getCommitDate() {
			return commitDate;
		}
		public void setCommitDate(Date commitDate) {
			this.commitDate = commitDate;
		}
		public List<UserCommitCount> getUserCommitList() {
			return userCommitList;
		}
		public void setUserCommitList(List<UserCommitCount> userCommitList) {
			this.userCommitList = userCommitList;
		}
		
}
