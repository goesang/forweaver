package com.forweaver.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.archive.ZipFormat;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;
import org.gitective.core.RepositoryUtils;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Project;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitCommitLog;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.domain.git.GitSimpleFileInfo;

public class GitUtil {

	public static String GitPath = "/home/git/";
	private String path;
	private Repository localRepo;
	private Git git;
	private StoredConfig config;
	private boolean isRepo;

	public GitUtil(Repo repo) {
		try {
			this.path = GitPath + repo.getLectureName() + "/" + repo.getName()
					+ ".git";
			this.localRepo = new FileRepository(this.path);
			this.git = new Git(localRepo);
			this.config = localRepo.getConfig();
			this.isRepo = true;
		} catch (Exception e) {

		}
	}

	public GitUtil(Project pro) {
		try {
			this.path = GitPath + pro.getName() + ".git";
			this.localRepo = new FileRepository(this.path);
			this.git = new Git(localRepo);
			this.config = localRepo.getConfig();
			this.isRepo = false;
		} catch (Exception e) {

		}
	}

	public GitUtil(String creatorName, String repositoryName) {
		try {
			this.path = GitPath + creatorName + "/" + repositoryName
					+ ".git";
			this.localRepo = RepositoryCache.open(RepositoryCache.FileKey
					.lenient(new File(this.path), FS.DETECTED), true);
			this.git = new Git(localRepo);
		} catch (Exception e) {

		}
	}

	public void createRepository() throws Exception {
		git.init().setBare(true).setDirectory(new File(this.path)).call();
		StoredConfig config = localRepo.getConfig();
		config.setString("http", null, "receivepack", "true");
		config.save();
		if (this.isRepo){
			new File(path + "/refs/tags").setWritable(false, false); // 숙제 및 예제 저장소는 태그 생성 불가
			File hide = new File(path + "/refs/heads/edih"); // 저장소 접근을 막기 위한 디렉토리 추가
			hide.mkdir();
		}
	}

	public void deleteRepository() throws Exception {
		FileUtils.deleteDirectory(new File(this.path));
	}

	public GitFileInfo getFileInfor(String commitID, String filePath) {
		List<RevCommit> gitLogList = new ArrayList<RevCommit>();
		RevCommit selectCommit = this.getCommit(commitID);

		int selectCommitIndex = 0;

		if (selectCommit == null)
			return null;
		try {
			Iterable<RevCommit> gitLogIterable = git.log().all()
					.addPath(filePath).call();
			int index = 0;
			for (RevCommit revCommit : gitLogIterable) {
				if (!gitLogList.contains(revCommit)) {
					if (selectCommit.getName().equals(revCommit.getName()))
						selectCommitIndex = index;

					gitLogList.add(revCommit);
					index++;
				}
			}

		} finally {

			return new GitFileInfo(filePath, BlobUtils.getContent(
					this.localRepo, selectCommit.getId(), filePath),
					gitLogList, selectCommitIndex);

		}
	}

	public RevCommit getCommit(String refName) {
		return CommitUtils.getCommit(this.localRepo, refName);
	}

	public int getCommitListCount(String refName) {
		try {
			Iterable<RevCommit> gitLogIterable = this.git
					.log()
					.add(CommitUtils.getCommit(this.localRepo, refName).getId())
					.call();
			int length = 0;
			for (RevCommit revCommit : gitLogIterable) {
				length++;
			}
			return length;
		} catch (Exception e) { // 추후 오류 잡아내기 바람
			return 0;
		}

	}

	public List<GitSimpleFileInfo> getGitFileInfoList(String commitID)
			throws Exception {
		List<GitSimpleFileInfo> gitFileInfoList = new ArrayList<GitSimpleFileInfo>();

		ObjectId revId = this.localRepo.resolve(commitID);
		DirCache cache = new DirCache(new File(this.path), FS.DETECTED);
		TreeWalk treeWalk = new TreeWalk(this.localRepo);

		treeWalk.addTree(new RevWalk(this.localRepo).parseTree(revId));
		treeWalk.addTree(new DirCacheIterator(cache));
		treeWalk.setRecursive(true);
		while (treeWalk.next()) {
			RevCommit revCommit = CommitUtils.getLastCommit(this.localRepo,
					commitID, treeWalk.getPathString());
			ObjectLoader loader = this.localRepo.open(treeWalk.getObjectId(0));
			GitSimpleFileInfo gitFileInfo = new GitSimpleFileInfo(
					treeWalk.getNameString(), treeWalk.getPathString(),
					treeWalk.getDepth(),
					loader.getType() == Constants.OBJ_TREE,
					revCommit.getName(), revCommit.getShortMessage(),
					revCommit.getCommitTime());
			gitFileInfoList.add(gitFileInfo);
		}
		return gitFileInfoList;
	}

	public GitCommitLog getCommitLog(String commitID) {
		GitCommitLog gitCommitLog = null;
		try {
			RevCommit commit = CommitUtils.getCommit(this.localRepo, commitID);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				RevCommit preCommit = CommitUtils.getCommit(this.localRepo,
						commitID + "~1");

				DiffFormatter df = new DiffFormatter(out);
				df.setRepository(this.localRepo);
				df.format(preCommit, commit);
				df.flush();
				df.release();
			} catch (Exception e) {
				
			}

			gitCommitLog = new GitCommitLog(commit.getId().getName(),
					commit.getShortMessage(), commit.getFullMessage(), commit
					.getCommitterIdent().getName(), commit
					.getCommitterIdent().getEmailAddress(),
					out.toString(), commit.getCommitTime());
		}catch (Exception e) {
			System.out.println(e.getMessage());
				System.out.println("aaaaaaaaa");
			}
		 finally {
			return gitCommitLog;
		}
	}



	public List<GitSimpleCommitLog> getCommitLogList(String branchName,
			int page, int number) {
		List<GitSimpleCommitLog> gitCommitLogList = new ArrayList<GitSimpleCommitLog>();
		int startNumber = number * (page - 1);
		try {

			for (int i = startNumber; i < startNumber + 10; i++) {
				RevCommit commit = CommitUtils.getCommit(this.localRepo,
						branchName + "~" + i);

				GitSimpleCommitLog gitCommitLog = new GitSimpleCommitLog(commit
						.getId().getName(), commit.getShortMessage(), commit
						.getCommitterIdent().getName(), commit
						.getCommitterIdent().getEmailAddress(),
						commit.getCommitTime());

				gitCommitLogList.add(gitCommitLog);
			}

		} finally {
			return gitCommitLogList;
		}
	}

	public List<String> getBranchList() {
		ArrayList<String> branchList = new ArrayList<String>();
		try {
			for (Ref ref : this.git.branchList().call()) {
				branchList.add(ref.getName());
			}
		} finally {
			return branchList;
		}
	}

	public List<String> readBranchList(String weaverName) {
		List<String> brnachList = new ArrayList<String>();
		try {
			for (String str : RepositoryUtils.getBranches(this.localRepo)) {
				if (!str.contains("-") || str.contains('-' + weaverName)) {
					brnachList.add(str);
				}
			}

		} finally {
			return brnachList;
		}
	}

	public boolean isReadCommit(String branchName, String commitID) {

		try {
			String orginalID = CommitUtils.getCommit(this.localRepo, commitID)
					.getName();

			for (int i = 0;; i++) {
				RevCommit commit = CommitUtils.getCommit(this.localRepo,
						branchName + "~" + i);
				if (commit.getName().equals(orginalID))
					return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public GitFileInfo getFileInfor(String commitID, String filePath,
			String WeaverName) {
		List<RevCommit> gitLogList = new ArrayList<RevCommit>();
		RevCommit selectCommit = this.getCommit(commitID);

		int selectCommitIndex = 0;

		if (selectCommit == null)
			return null;

		try {
			Iterable<RevCommit> gitLogIterable = git.log().all()
					.addPath(filePath).call();
			int index = 0;
			for (RevCommit revCommit : gitLogIterable) {
				if (selectCommit.getName().equals(revCommit.getName()))
					selectCommitIndex = index;
				for (String branchName : readBranchList(WeaverName)) {
					if (!gitLogList.contains(revCommit)
							&& isReadCommit(branchName, revCommit.getName())) {
						gitLogList.add(revCommit);
					}
				}

				index++;
			}

		} finally {

			return new GitFileInfo(filePath, BlobUtils.getContent(
					this.localRepo, selectCommit.getId(), filePath),
					gitLogList, selectCommitIndex);

		}
	}

	public List<String> getSimpleBranchNameList() {
		String branchName = "";
		List<String> branchList = new ArrayList<String>();
		try {
			for (Ref ref : this.git.branchList().call()) {
				branchList.add(ref.getName().substring(11));
			}
			branchName = this.localRepo.getBranch();
		} catch (IOException e) {
			branchName = "체크아웃한 브랜치 없음";
		} catch (GitAPIException e) {
			// 그냥 넘어감
		} finally {
			if (branchList.size() == 0)
				branchList.add("브랜치가 없습니다!");
			else {
				branchList.remove(branchName);
				branchList.add(0, branchName);
			}
			return branchList;
		}
	}

	public void getProjectZip(String commitName, HttpServletResponse response) {
		try {
			ArchiveCommand.registerFormat("zip", new ZipFormat());
			ObjectId revId = this.localRepo.resolve(commitName);
			git.archive().setOutputStream(response.getOutputStream())
			.setFormat("zip")
			.setTree(revId)
			.call();

			ArchiveCommand.unregisterFormat("zip");
			response.flushBuffer();
		} catch (Exception e) {

		}

	}

	public void hideBranches(String userName){

		File currentDirectory = new File(path+"/refs/heads");
		File hideDirectory = new File(path+"/refs/heads/edih");

		for(File file:currentDirectory.listFiles()){
			if(file.getName().contains("-") && !file.getName().endsWith("-"+userName))
				file.renameTo(new File(path+"/refs/heads/edih/"+file.getName()));
		}
		hideDirectory.setWritable(false);
		hideDirectory.setReadable(false);		
	}

	public void hideNotUserBranches(String userName){

		File currentDirectory = new File(path+"/refs/heads");
		File hideDirectory = new File(path+"/refs/heads/edih");

		for(File file:currentDirectory.listFiles()){
			if(!file.getName().endsWith("-"+userName))
				file.renameTo(new File(path+"/refs/heads/edih/"+file.getName()));
		}
		hideDirectory.setWritable(false);
		hideDirectory.setReadable(false);

	}

	public void notWriteBranches(){
		File currentDirectory = new File(path+"/refs/heads");
		for(File file:currentDirectory.listFiles()){
			file.setWritable(false);	
		}
		new File(path+"/refs/heads").setWritable(false);		
	}

	public void writeBranches(){
		File currentDirectory = new File(path+"/refs/heads");
		for(File file:currentDirectory.listFiles()){
			file.setWritable(true);	
		}
		new File(path+"/refs/heads").setWritable(true);		
	}

	public void showBranches(){
		File hideDirectory = new File(path+"/refs/heads/edih");
		hideDirectory.setWritable(true);
		hideDirectory.setReadable(true);
		for(File file:hideDirectory.listFiles()){
			file.renameTo(new File(path+"/refs/heads/"+file.getName()));
		}

	}

	public void checkOutBranch(String weaverName){

		try{
			for(String branchName : this.getBranchList()){
				if(branchName.endsWith("-"+weaverName)){
					FileWriter reader = new FileWriter(this.localRepo.getDirectory().getAbsolutePath()+"/HEAD");
					reader.write("ref: "+branchName);
					reader.close();
				}				
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void checkOutMasterBranch(){
		try{
			for(String branchName : this.getBranchList()){
				if(branchName.endsWith("master")){
					FileWriter reader = new FileWriter(this.localRepo.getDirectory().getAbsolutePath()+"/HEAD");
					reader.write("ref: "+branchName);
					reader.close();
				}				
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public void createStudentBranch(List<String> beforeBranchList,
			Lecture lecture) {
		List<String> createBranch = getBranchList();
		try {
			if (createBranch.removeAll(beforeBranchList)
					|| beforeBranchList.size() == 0) {
				for (String branchPath : createBranch) {
					String branchName = branchPath.substring(11);
					for (Weaver weaver : lecture.getJoinWeavers()) {
						git.branchCreate()
						.setStartPoint(branchName)
						.setName(
								branchName + "-" + weaver.getId())
								.call();
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void uploadZip(String name,String email,String message,InputStream zip){
		try {
			File localPath = File.createTempFile("git", "");
			localPath.delete();
			Git.cloneRepository()
			.setURI(this.path)
			.setDirectory(localPath)
			.call();

			for(String fileName:localPath.list())
				if(!fileName.equals(".git")){
					File file = new File(localPath.getAbsoluteFile()+"/"+fileName);
					if(file.isDirectory())
						FileUtils.deleteDirectory(file);
					else
						file.delete();
				}
			byte[] buffer = new byte[1024];

			ZipInputStream zis = 
					new ZipInputStream(zip);	    	
			ZipEntry ze = zis.getNextEntry();
			
			while(ze!=null){

				String fileName = ze.getName();
				File newFile = new File(localPath + File.separator + fileName);

				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);             

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();   
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			Git git = new Git(new FileRepository(new File(localPath.getAbsoluteFile()+ File.separator+".git")));
			git.add().addFilepattern(".").call();
			git.commit().setAuthor(name, email).setMessage(message).call();
			git.push().setRemote("origin").call();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
}
