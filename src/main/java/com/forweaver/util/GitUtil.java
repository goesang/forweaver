package com.forweaver.util;
//https://github.com/centic9/jgit-cookbook와 
//https://github.com/kevinsawicki/gitective
//참고하여 코딩

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.archive.ArchiveFormats;
import org.eclipse.jgit.archive.TarFormat;
import org.eclipse.jgit.archive.ZipFormat;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Project;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitBlame;
import com.forweaver.domain.git.GitCommitLog;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.domain.git.GitSimpleFileInfo;
import com.forweaver.domain.git.statistics.GitChildStatistics;
import com.forweaver.domain.git.statistics.GitParentStatistics;

/** git과 관련된 모든 기능 구현.
 *
 */
@Component
public class GitUtil {

	private String gitPath;
	private String path;
	private Repository localRepo;
	private Git git;
	private StoredConfig config;
	private boolean isRepo;

	public String getGitPath() {
		return gitPath;
	}

	public void setGitPath(String gitPath) {
		this.gitPath = gitPath;
	}
	public GitUtil(){
		this.gitPath = "/home/git/";
	}
	/** 과제 저장소용 초기화 메서드
	 * @param repo
	 */
	public void Init(Repo repo) {
		try {
			this.path = gitPath + repo.getLectureName() + "/" + repo.getName()
					+ ".git";
			this.localRepo = new FileRepository(this.path);
			this.git = new Git(localRepo);
			this.config = localRepo.getConfig();
			this.isRepo = true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/** 프로젝트 초기화 메서드
	 * @param pro
	 */
	public void Init(Project pro) {
		try {
			this.path = gitPath + pro.getName() + ".git";
			this.localRepo = new FileRepository(this.path);
			this.git = new Git(localRepo);
			this.config = localRepo.getConfig();
			this.isRepo = false;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/** 초기화 메서드
	 * @param creatorName
	 * @param repositoryName
	 */
	public void Init(String creatorName, String repositoryName) {
		try {
			this.path = gitPath + creatorName + "/" + repositoryName
					+ ".git";
			this.localRepo = RepositoryCache.open(RepositoryCache.FileKey
					.lenient(new File(this.path), FS.DETECTED), true);
			this.git = new Git(localRepo);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}



	/** 초기화 메서드
	 * @param creatorName
	 * @param repositoryName
	 */
	public void Init(String path) {
		try {
			this.path = path+ "/.git";
			this.localRepo = RepositoryCache.open(RepositoryCache.FileKey
					.lenient(new File(this.path), FS.DETECTED), true);
			this.git = new Git(localRepo);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/** 저장소 생성함.
	 * @throws Exception
	 */
	public void createRepository() throws Exception {
		Git.init().setBare(true).setDirectory(new File(this.path)).call();
		StoredConfig config = localRepo.getConfig();
		config.setString("http", null, "receivepack", "true");
		config.save();
		if (this.isRepo){
			new File(path + "/refs/tags").setWritable(false, false); // 숙제 및 예제 저장소는 태그 생성 불가
			File hide = new File(path + "/refs/heads/edih"); // 저장소 접근을 막기 위한 디렉토리 추가
			hide.mkdir();
		}
	}

	/** git 디렉토리 삭제
	 * @throws Exception
	 */
	public void deleteRepository() throws Exception {
		FileUtils.deleteDirectory(new File(this.path));
	}

	/** 파일주소와 커밋아이디를 바탕으로 디렉토리인지 검사함.
	 * @param commitID
	 * @param filePath
	 * @return
	 */
	public boolean isDirectory(String commitID, String filePath){
		
		if(filePath.length() == 0)
			return true;
		
		TreeWalk treeWalk = new TreeWalk(this.localRepo);
		RevWalk revWalk = new RevWalk(this.localRepo);
		
		try{
			ObjectId revId = this.localRepo.resolve(commitID);
			treeWalk.addTree(revWalk.parseTree(revId));
			treeWalk.setRecursive(true);
			while (treeWalk.next()) {
				if(treeWalk.getPathString().equals(filePath)){
					return false;
				}
			}
			treeWalk.reset(revWalk.parseTree(revId));
			while (treeWalk.next()) {
				if(treeWalk.getPathString().startsWith(filePath)){
					return true;
				}
			}
			
		}catch(Exception e){
			treeWalk.close();
			revWalk.close();
			return false;
		}
		treeWalk.close();
		revWalk.close();
		return false;
	}


	/** 프로젝트의 파일 정보를 가져옴
	 * @param commitID
	 * @param filePath
	 * @return
	 */
	public GitFileInfo getFileInfo(String commitID, String filePath) {
		List<RevCommit> gitLogList = new ArrayList<RevCommit>();
		RevCommit selectCommit = this.getCommit(commitID);
		int selectCommitIndex= 0;
		if (selectCommit == null)
			return null;
		
		try {
			Iterable<RevCommit> gitLogIterable = git.log().all().addPath(filePath).call();
			
			for (RevCommit revCommit : gitLogIterable) 
					gitLogList.add(revCommit);
			
			for(;selectCommitIndex<gitLogList.size();selectCommitIndex++)
				if(gitLogList.get(selectCommitIndex).getId().equals(selectCommit.getId()))
					break;

		} finally {

			return new GitFileInfo(filePath, BlobUtils.getContent(
					this.localRepo, selectCommit.getId(), filePath),
					BlobUtils.getRawContent(this.localRepo, selectCommit.getId(), filePath),
					gitLogList, selectCommitIndex,isDirectory(commitID,filePath));

		}
	}

	/** 저장소에서 커밋을 갖고 옴
	 * @param refName
	 * @return
	 */
	public RevCommit getCommit(String refName) {
		return CommitUtils.getCommit(this.localRepo, refName);
	}
	/** 커밋 갯수를 가져옴
	 * @param refName
	 * @return
	 */
	public int getCommitListCount(String refName) {
		try {
			Iterable<RevCommit> gitLogIterable = this.git
					.log()
					.add(
							this.getCommit(refName))
							.call();
			int length = 0;

			for (RevCommit revCommit : gitLogIterable) {
				length++;
			}
			return length;
		} catch (Exception e) {
			return 0;
		}

	}

	/** 프로젝트의 파일 정보들을 가져와 파일 브라우져를 보여줄 때 사용.
	 * @param commitID
	 * @param filePath
	 * @return
	 */
	public List<GitSimpleFileInfo> getGitFileInfoList(String commitID,String filePath) {
		List<GitSimpleFileInfo> gitFileInfoList = new ArrayList<GitSimpleFileInfo>();
		List<String> fileList = this.getGitFileList(commitID);
		try{			
			for(String path: WebUtil.getFileList(fileList, "/"+filePath)){
				RevCommit revCommit = CommitUtils.getLastCommit(this.localRepo,
						commitID, path.substring(1));
				String[] strArray = path.substring(1).split("/");
				GitSimpleFileInfo gitFileInfo = new GitSimpleFileInfo(
						strArray[strArray.length-1], path.substring(1),
						isDirectory(commitID,path.substring(1)),
						revCommit.getName(), revCommit.getShortMessage(),
						revCommit.getCommitTime(),
						revCommit.getCommitterIdent().getName(),
						revCommit.getCommitterIdent().getEmailAddress());
				gitFileInfoList.add(gitFileInfo);
			}

		}catch(Exception e){}
		return gitFileInfoList;
	}

	/** 프로젝트의 파일 목록을 커밋 아이디를 가지고 가져옴.
	 * @param commitID
	 * @return
	 */
	public List<String> getGitFileList(String commitID) {
		List<String> fileList = new ArrayList<String>();
		try{
			ObjectId revId = this.localRepo.resolve(commitID);
			TreeWalk treeWalk = new TreeWalk(this.localRepo);
			treeWalk.addTree(new RevWalk(this.localRepo).parseTree(revId));
			treeWalk.setRecursive(true);

			while (treeWalk.next()) {
				fileList.add("/"+treeWalk.getPathString());
			}

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return fileList;
	}

	/** 저장소에서 GIT 로그 정보를 가져옴
	 * @param commitID
	 * @return
	 */
	public GitCommitLog getCommitLog(String commitID) {
		GitCommitLog gitCommitLog = null;
		String diffs = new String();
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
				df.close();
				diffs+=out.toString();
			} catch (Exception e) {
				diffs += simpleFileBrowser(commit);
			}

			gitCommitLog = new GitCommitLog(commit.getId().getName(),
					commit.getShortMessage(), commit.getFullMessage(), commit
					.getCommitterIdent().getName(), commit
					.getCommitterIdent().getEmailAddress(),
					diffs, this.getNote(commitID),commit.getCommitTime());
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			return gitCommitLog;
		}
	}

	/** 단순하게 커밋을 트리워크를 이용하여 당시 파일 내역을 출력.
	 * @param commit
	 * @return
	 */
	public String simpleFileBrowser(RevCommit commit){
		String out = new String();
		try
		{
			TreeWalk treeWalk = new TreeWalk(this.localRepo);
			treeWalk.addTree(new RevWalk(this.localRepo).parseTree(	commit));

			while (treeWalk.next())
			{
				out+="--- /dev/null\n";
				out+="+++ b/"+treeWalk.getPathString()+"\n";
				out+= "+"+BlobUtils.getContent(this.localRepo, commit,treeWalk.getPathString().replace("\n", "\n+"));
				out+="\n";
			}
		}finally{
			return out;
		}
	}

	// 커밋 로그 목록를 가져옴
	public List<GitSimpleCommitLog> getCommitLogList(String branchName,
			int page, int number) {
		List<GitSimpleCommitLog> gitCommitLogList = new ArrayList<GitSimpleCommitLog>();
		int startNumber = number * (page - 1);
		try {

			for(RevCommit commit:git.log().add(
					this.getCommit(branchName)).setSkip(startNumber).setMaxCount(number).call()){

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

	/** 저장소에서 브랜치 정보를 가져옴
	 * @return
	 */
	public List<String> getBranchList() {
		ArrayList<String> branchList = new ArrayList<String>();

		try {
			for (Ref ref : this.git.branchList().call()) {
				branchList.add(ref.getName());
			}
		} catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
		return branchList;

	}


	/** 브랜치 목록과 태그 목록을 가져옴
	 * @return
	 */
	public List<String> getSimpleBranchAndTagNameList() {
		String branchName = "";
		List<String> branchList = new ArrayList<String>();
		List<String> tagList = new ArrayList<String>();
		try {
			for (Ref ref : this.git.branchList().call()) {
				branchList.add(ref.getName().substring(11));
			}
			branchName = this.localRepo.getBranch();

			for (Ref ref : this.git.tagList().call()) {
				branchList.add(ref.getName().substring(10));
			}
		} catch (IOException e) {
			branchName = "empty_Branch";
		} catch (GitAPIException e) {
			System.err.println(e.getMessage());
		} finally {
			if (branchList.size() == 0)
				branchList.add("empty_Branch");
			else {
				branchList.remove(branchName);
				branchList.add(0, branchName);
			}
			branchList.addAll(tagList);
			return branchList;
		}
	}


	/** 커밋을 입력받으면 당시 파일들을 압축하여 사용자에게 보내줌.
	 * @param commitName
	 * @param format
	 * @param response
	 */
	public void getProjectZip(String commitName,String format, HttpServletResponse response) {

		try {
			ArchiveFormats.registerAll();
			ObjectId revId = this.localRepo.resolve(commitName);
			git.archive().setOutputStream(response.getOutputStream())
			.setFormat(format)
			.setTree(revId)
			.call();

			ArchiveCommand.unregisterFormat("zip");
			ArchiveCommand.unregisterFormat("tar");
			response.flushBuffer();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	/** 숙제 저장소의 경우 권한이 없는 브랜치에 접근하지 못하도록 숨김.
	 * @param userName
	 */
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

	/** 예제 저장소의 경우 학생이 접근하면 원래 브랜치에 푸시 못하게 막음
	 * 
	 */
	public void notWriteBranches(){
		File currentDirectory = new File(path+"/refs/heads");
		for(File file:currentDirectory.listFiles()){
			file.setWritable(false);	
		}
		new File(path+"/refs/heads").setWritable(false);		
	}


	/** 예제 저장소에서 학생이 접근하고 난 후 원래대로 복구함.
	 * 
	 */
	public void writeBranches(){
		File currentDirectory = new File(path+"/refs/heads");
		for(File file:currentDirectory.listFiles()){
			file.setWritable(true);	
		}
		new File(path+"/refs/heads").setWritable(true);		
	}


	/** 숙제 저장소에서 학생이 접근하고 원래대로 복구함.
	 * 
	 */
	public void showBranches(){
		File hideDirectory = new File(path+"/refs/heads/edih");
		hideDirectory.setWritable(true);
		hideDirectory.setReadable(true);
		for(File file:hideDirectory.listFiles()){
			file.renameTo(new File(path+"/refs/heads/"+file.getName()));
		}

	}

	/** 학생의 경우 숙제 저장소에 접근하면 자신의 브랜치만 읽을 수 있도록 함.
	 * @param weaverName
	 */
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
			System.err.println(e.getMessage());
		}
	}

	/** 학생이 숙제 저장소에 접근하고 나서 원래대로 복구함.
	 * 
	 */
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

	/** 강사가 숙제 저장소에 최초로 푸시하면 브랜치를 학생들 이름으로 복사함.
	 * 예를 들어 처음 그냥 푸시하면 master가 푸시되는데 이때 root라는 아이디의 학생이 있으면 
	 * master-root 브랜치가 파생되고 이 브랜치는 강사와 root라는 학생만 접근 가능.
	 * @param beforeBranchList
	 * @param lecture
	 */
	public void createStudentBranch(List<String> beforeBranchList,
			Lecture lecture) {
		List<String> createBranch = getBranchList();
		try {
			if (createBranch.removeAll(beforeBranchList)
					|| beforeBranchList.size() == 0) {
				for (String branchPath : createBranch) {
					String branchName = branchPath.substring(11);
					for (Weaver weaver : lecture.getJoinWeavers()) {// 학생들 이름별로 복사함.
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
	/** GIT이 없이도 프로젝트를 압축하여 업로드하면 자동으로 git에 푸시해주는 기능.
	 * @param name
	 * @param email
	 * @param branchName
	 * @param message
	 * @param zip
	 */
	public void uploadZip(String name,String email,String branchName,String message,MultipartFile zip){
		String directoryPath = "/tmp/"+new org.bson.types.ObjectId().toString();

		WebUtil.multipartFileToTempFile(directoryPath+".zip", zip);

		PersonIdent personIdent = new PersonIdent(name, email);
		try {
			// 임시 git 저장소 생성하고 클론.
			File localPath = new File(directoryPath);
			Git.cloneRepository().setURI(this.path).setDirectory(localPath)
			.call();
			// .git을 제외한 파일 모두 삭제.
			Git git = new Git(new FileRepository(new File(localPath.getAbsoluteFile()+ File.separator+".git")));
			this.localRepo = git.getRepository();

			if(!branchName.equals("empty_Branch")) // 브랜치가 존재하지 않는다면 새로 만듬.
				try{
					git.branchCreate().setStartPoint("refs/remotes/origin/"+branchName).setName(branchName).call();
				} 
			catch(Exception e) {}

			if(!branchName.equals("empty_Branch"))
				try{
					git.checkout().setCreateBranch(true).setName(branchName).call();
				} 
			catch(Exception e) 
			{
				git.checkout().setName(branchName).call();
			}

			for(String fileName:getGitFileList(branchName))
				git.rm().addFilepattern(fileName.substring(1)).call();	

			WebUtil.unZip(directoryPath+".zip", directoryPath,WebUtil.isOneDirectory(directoryPath+".zip"));

			git.add().addFilepattern(".").call();		
			git.commit().setCommitter(personIdent).setAuthor(personIdent).setMessage(message).call();
			git.push().setRemote("origin").call();

			FileUtils.deleteDirectory(new File(directoryPath));
			new File(directoryPath+".zip").delete();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			try{
				FileUtils.deleteDirectory(new File(directoryPath));
				new File(directoryPath+".zip").delete();
			}catch(Exception ex){}
		}
	}

	/** 프로젝트를 포크함.
	 * @param originRepo
	 * @param newRepo
	 */
	public void forkRepository(String originRepo, String newRepo){
		try{
			FileUtils.copyDirectory(new File(gitPath+originRepo+".git"),  new File(gitPath+newRepo+".git"));
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}


	/** GIT이 없이도 웹에서 파일을 수정하면 자동으로 git에 푸시해주는 기능.
	 * @param name
	 * @param email
	 * @param branchName
	 * @param message
	 * @param zip
	 */
	public void updateFile(String name,String email,String branchName,String message,String path,String code){
		String directoryPath = "/tmp/"+new org.bson.types.ObjectId().toString();

		PersonIdent personIdent = new PersonIdent(name, email);
		try {
			// 임시 git 저장소 생성하고 클론.
			File localPath = new File(directoryPath);
			Git.cloneRepository().setURI(this.path).setDirectory(localPath).call();

			// .git을 제외한 파일 모두 삭제.
			Git git = new Git(new FileRepository(new File(localPath.getAbsoluteFile()+ File.separator+".git")));
			this.localRepo = git.getRepository();

			if(!branchName.equals("empty_Branch")) // 브랜치가 존재하지 않는다면 새로 만듬.
				try{
					git.branchCreate().setStartPoint("refs/remotes/origin/"+branchName).setName(branchName).call();
				} 
			catch(Exception e) {}

			if(!branchName.equals("empty_Branch"))
				try{
					git.checkout().setCreateBranch(true).setName(branchName).call();
				} 
			catch(Exception e) 
			{
				git.checkout().setName(branchName).call();
			}
			
			File file = new File(directoryPath+"/"+path);
			
			if(!file.exists()) // 파일이 존재하지 않는다면.
				throw new Exception();
			
			FileWriter fw= new FileWriter(file); //파일을 수정함.
			fw.write(code);
			fw.close();
			git.add().addFilepattern(".").call();		
			git.commit().setCommitter(personIdent).setAuthor(personIdent).setMessage(message).call();
			git.push().setRemote("origin").call();

			FileUtils.deleteDirectory(new File(directoryPath));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			try{
				FileUtils.deleteDirectory(new File(directoryPath));
			}catch(Exception ex){}
		}
	}


	/** GIT이 없이도 파일을 업로드하면 자동으로 git에 푸시해주는 기능.
	 * @param name
	 * @param email
	 * @param branchName
	 * @param message
	 * @param zip
	 */
	public void uploadFile(String name,String email,String branchName,String message,String path,MultipartFile file){
		String directoryPath = "/tmp/"+new org.bson.types.ObjectId().toString();

		PersonIdent personIdent = new PersonIdent(name, email);
		try {
			// 임시 git 저장소 생성하고 클론.
			File localPath = new File(directoryPath);
			Git.cloneRepository().setURI(this.path).setDirectory(localPath).call();

			// .git을 제외한 파일 모두 삭제.
			Git git = new Git(new FileRepository(new File(localPath.getAbsoluteFile()+ File.separator+".git")));
			this.localRepo = git.getRepository();

			if(!branchName.equals("empty_Branch")) // 브랜치가 존재하지 않는다면 새로 만듬.
				try{
					git.branchCreate().setStartPoint("refs/remotes/origin/"+branchName).setName(branchName).call();
				} 
			catch(Exception e) {}

			if(!branchName.equals("empty_Branch"))
				try{
					git.checkout().setCreateBranch(true).setName(branchName).call();
				} 
			catch(Exception e) 
			{
				git.checkout().setName(branchName).call();
			}

			boolean existBranch = false; // 브랜치가 존재하는지 여부
			for(String brench:git.getRepository().getAllRefs().keySet())
				if(brench.equals("refs/remotes/origin/"+branchName))
					existBranch = true;

			if(!existBranch) // 새로 브랜치를 만들었다면 다 지움
				for(String fileName:getGitFileList(branchName))
					git.rm().addFilepattern(fileName.substring(1)).call();	

			WebUtil.multipartFileToTempFile(directoryPath+path+"/"+file.getOriginalFilename(), file);
			git.add().addFilepattern(".").call();		
			git.commit().setCommitter(personIdent).setAuthor(personIdent).setMessage(message).call();
			git.push().setRemote("origin").call();

			FileUtils.deleteDirectory(new File(directoryPath));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/** 일주일 24시간별로 커밋의 갯수를 측정하는 코드 빈도수 시각화에 쓰임
	 * @return
	 */
	public int[][] getDayAndHour(){

		int[][] array = new int[7][24];

		try{
			for(RevCommit rc:git.log().all().call())
				array[rc.getCommitterIdent().getWhen().getDay()]
						[rc.getCommitterIdent().getWhen().getHours()]++;

		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return array;
	}

	/** 각 유저가 날짜별로 커밋을 한 정보를 취합함.
	 * @return
	 */
	public GitParentStatistics getCommitStatistics(){
		GitParentStatistics gitParentStatistics = new GitParentStatistics();
		try{
			for(RevCommit rc:git.log().all().call()){
				String diffs = new String();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				if(rc.getParentCount()>0){
					DiffFormatter df = new DiffFormatter(out);
					df.setRepository(this.localRepo);
					df.format(rc.getParent(0), rc);
					df.flush();
					df.close();
					diffs = out.toString();
				} else {
					diffs = simpleFileBrowser(rc);
				}
				int addFile = StringUtils.countOccurrencesOf(diffs, "--- /dev/null");// 추가한 파일 수
				int deleteFile = StringUtils.countOccurrencesOf(diffs, "+++ /dev/null");// 삭제한 파일 수
				diffs = StringUtils.delete(diffs, "\n--- ");
				diffs = StringUtils.delete(diffs, "\n+++ ");
				gitParentStatistics.addGitChildStatistics(
						new GitChildStatistics(
								rc.getAuthorIdent().getEmailAddress(), // 유저 이메일
								StringUtils.countOccurrencesOf(diffs, "\n+"), //추가한 라인 수
								StringUtils.countOccurrencesOf(diffs, "\n-"), //삭제한 라인 수
								addFile,
								deleteFile,
								rc.getAuthorIdent().getWhen())); // 날짜
			}

		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return gitParentStatistics;
	}

	/** git blame기능을 구현함.
	 * @param filePath
	 * @param commitID
	 * @return
	 */
	public List<GitBlame> getBlame(String filePath, String commitID){
		List<GitBlame> gitBlames = new ArrayList<GitBlame>();
		RevCommit commit = CommitUtils.getCommit(this.localRepo, commitID);

		try{
			BlameResult result = git.blame().setStartCommit(commit).setFilePath(filePath).call();
			// 입력 받은 커밋을 기점으로 파일의 라인 별로 코드를 분석함.
			for(int i=0; i<result.getResultContents().size(); i++)
				gitBlames.add(new GitBlame(result.getSourceCommit(i)));

		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return gitBlames;
	}


	/** 프로젝트 정보를 가져옴
	 * @param branchName
	 * @return
	 */
	public GitInfo getGitInfo(String branchName){
		GitInfo gitInfo = new GitInfo();
		try{
			gitInfo.run(this.localRepo, branchName);
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return gitInfo;
	}

	/**  커밋에 붙어있는 GIT 노트를 가져옴
	 * @param commit
	 * @return
	 */
	public String getNote(String commit){
		String str = new String();
		try{
			Note note = git.notesShow().setObjectId(CommitUtils.getCommit(git.getRepository(), commit)).call();
			ObjectLoader loader = this.localRepo.open(note.getData());
			str = new String(loader.getBytes());
		}finally{
			return str;
		}
	}

	/** 브랜치 대 브랜치 병합이 아닌 커밋 대 브랜치 병합인 채리픽 방식으로 통합하는 기능
	 * @param cherryPickRepo
	 * @param cherryPickCommit
	 * @param originalRepoBranch
	 * @return
	 */
	public String cherryPick(String cherryPickRepo,String cherryPickCommit,String originalRepoBranch){
		String returnState = new String();
		cherryPickRepo = gitPath+cherryPickRepo+".git";

		try{
			//임시 git 저장소를 클론함.
			File localPath = File.createTempFile("cherry", "");
			localPath.delete();
			Git.cloneRepository()
			.setURI(cherryPickRepo)
			.setDirectory(localPath)
			.call();

			Repository repo = new FileRepository(localPath+"/.git");
			Git git = new Git(repo);
			StoredConfig config = git.getRepository().getConfig();
			config.unset("remote", "origin", "url");
			config.save();
			config.setString("remote", "origin", "url", this.path);// 보낼 것 위치 지정
			config.save();
			git.fetch().call();
			git.checkout().setName("refs/remotes/origin/"+originalRepoBranch).call();
			git.branchCreate().setName("org").call();
			git.checkout().setName("org").call();
			// 채리픽 시도하여 결과를 문자열로 저장
			returnState = git.cherryPick().include(CommitUtils.getCommit(repo, cherryPickCommit)).call().getStatus().toString();
			git.push().setRemote("origin").add("org:"+originalRepoBranch).call();
			// 원래 저장소로 푸시.
		}catch(Exception e){
			System.err.println(e.getMessage());
			returnState = "Error";
		}
		return returnState;
	}

}
