package com.forweaver.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitCommitLog;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.domain.git.GitSimpleFileInfo;
import com.forweaver.domain.git.statistics.GitParentStatistics;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;

@Service
public class GitService {
	@Autowired 
	private CacheManager cacheManager;
	
	@Autowired
	private WeaverDao weaverDao;

	
	public GitFileInfo getFileInfo(String parentDirctoryName,String repositoryName,
			String commitID,String filePath){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		filePath = filePath.replace('>', '/');
		filePath = filePath.replace(',', '.');
		GitFileInfo gitFileInfo = gitUtil.getFileInfor(commitID, filePath);
		gitFileInfo.setGitBlames(gitUtil.getBlame(filePath, commitID));
		return gitFileInfo;
	}
	
	public void hideBranch(String parentDirctoryName,String repositoryName,String weaverName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		gitUtil.hideNotUserBranches(weaverName);
		gitUtil.checkOutBranch(weaverName);
	}
	
	public void showBranch(String parentDirctoryName,String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		gitUtil.showBranches();
		gitUtil.checkOutMasterBranch();
	}
	
	public List<String> getBranchList(String parentDirctoryName,
			String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		List<String> branchList = gitUtil.getSimpleBranchAndTagNameList();
		return branchList;
	}
	
	public boolean existCommit(String parentDirctoryName,
			String repositoryName,String commit){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		try{
		RevCommit revCommit = gitUtil.getCommit(commit);
		if(revCommit == null)
			return false;
		else
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public int getCommitListCount(String parentDirctoryName,
			String repositoryName,String commit){
		Cache cache = cacheManager.getCache("gitCommitCount");
		Element element = cache.get(parentDirctoryName+"/"+repositoryName+"/"+commit);
		if (element == null || (element != null && element.getValue() == null)) {		
				GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
				int count = gitUtil.getCommitListCount(commit);
				Element newElement = 
						new Element(parentDirctoryName+"/"+repositoryName+"/"+commit,count);
				cache.put(newElement);
				return count;
		}
		return Integer.parseInt(element.getValue().toString());
	}
		
	public List<GitSimpleFileInfo> getGitSimpleFileInfoList(String parentDirctoryName,
			String repositoryName,String commitID) {
		Cache cache = cacheManager.getCache("gitSimpleFileInfoList");
		Element element = cache.get(parentDirctoryName+"/"+repositoryName+"-"+commitID);
		if (element == null || (element != null && element.getValue() == null)) {		
			try {
				GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
				List<GitSimpleFileInfo> gitFileInfoList = gitUtil.getGitFileInfoList(commitID);
				Element newElement = 
						new Element(parentDirctoryName+"/"+repositoryName+"-"+commitID, 
						gitFileInfoList);
				cache.put(newElement);
				return gitFileInfoList;
			} catch (Exception e) {
				return null;
			}
		}
		return (List<GitSimpleFileInfo>) element.getValue();
	}

	public List<GitSimpleCommitLog> getGitCommitLogList(String parentDirctoryName,
			String repositoryName,String branchName,int page,int number) {
		Cache cache = cacheManager.getCache("gitCommitLogList");
		Element element = 
				cache.get(parentDirctoryName+"/"+repositoryName+"-"+branchName+"/"+page+"/"+number);
		if (element == null || (element != null && element.getValue() == null)) {		
			try {
				GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
				List<GitSimpleCommitLog> gitCommitLogList = 
						loadImgSrc(gitUtil.getCommitLogList(branchName,page,number));
				Element newElement = 
						new Element(parentDirctoryName
								+"/"+repositoryName
								+"-"+branchName
								+"/"+page
								+"/"+number, 
						gitCommitLogList);
				cache.put(newElement);
				return gitCommitLogList;
			} catch (Exception e) {
				return null;
			}
		}
		return (List<GitSimpleCommitLog>) element.getValue();
	}
	
	
	public GitCommitLog getGitCommitLog(String parentDirctoryName,
			String repositoryName,String branchName) {
		Cache cache = cacheManager.getCache("gitCommitLog");
		Element element = cache.get(parentDirctoryName+"/"+repositoryName+"-"+branchName);
		if (element == null || (element != null && element.getValue() == null)) {		
			try {
				GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
				GitCommitLog gitCommitLog = gitUtil.getCommitLog(branchName);
				Element newElement = 
						new Element(parentDirctoryName+"/"+repositoryName+"-"+branchName, 
								gitCommitLog);
				cache.put(newElement);
				return gitCommitLog;
			} catch (Exception e) {
				return null;
			}
		}
		return (GitCommitLog) element.getValue();
	}
	
	
	public void getProjectZip(String parentDirctoryName,
			String repositoryName,String commitName,HttpServletResponse response){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		gitUtil.getProjectZip(commitName, response);
	}
	
	public List<GitSimpleCommitLog> loadImgSrc(List<GitSimpleCommitLog> gitSimpleCommitLogList) {
		for(GitSimpleCommitLog gitSimpleCommitLog : gitSimpleCommitLogList){
			Weaver loadWeaver =  weaverDao.get(gitSimpleCommitLog.getCommiterName());
			if (loadWeaver != null)
				gitSimpleCommitLog.setImgSrc("/"+loadWeaver.getId()+"/img");
		}
		
		return gitSimpleCommitLogList;
	}
	
	public GitParentStatistics loadStatistics(String parentDirctoryName,
			String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		return gitUtil.getCommitStatistics();
	}
	
	public int[][] loadDayAndHour(String parentDirctoryName,
			String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName, repositoryName);	
		return gitUtil.getDayAndHour();
	}

}
