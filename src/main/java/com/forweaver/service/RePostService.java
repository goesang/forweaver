package com.forweaver.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Code;
import com.forweaver.domain.Data;
import com.forweaver.domain.Post;
import com.forweaver.domain.RePost;
import com.forweaver.domain.Reply;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.CodeDao;
import com.forweaver.mongodb.dao.DataDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.mongodb.dao.RePostDao;
import com.forweaver.mongodb.dao.WeaverDao;

@Service
public class RePostService {
	@Autowired private RePostDao rePostDao;
	@Autowired private PostDao postDao;
	@Autowired private DataDao dataDao;
	@Autowired private CodeDao codeDao;
	@Autowired private WeaverDao weaverDao;
	@Autowired private CacheManager cacheManager;

	/** 답변을 추가함.
	 * @param rePost
	 * @param datas
	 * @return
	 */
	public boolean add(RePost rePost,List<Data> datas) {
		if(rePost == null)
			return false;
		if(datas != null)
			for(Data data:datas){
				dataDao.insert(data);
				rePost.addData(dataDao.getLast());
			}
		
		
		if(rePost.getOriginalCode() != null)
			weaverDao.updateInfo(rePost.getOrigianlWriter(),"weaverInfo.codeRePostCount",1); //코드의 답변 갯수 추가

		if(rePost.getOriginalPost() != null)
			weaverDao.updateInfo(rePost.getOrigianlWriter(),"weaverInfo.rePostCount",1); //글의 답변 갯수 추가
		
		weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePostCount",1); //자신의 답변 갯수 추가
		weaverDao.update(rePost.getWriter());
		rePostDao.insert(rePost);
		return true;
	}
	

	/** 댓글을 추가함.
	 * @param rePost
	 * @param reply
	 * @return
	 */
	public boolean addReply(RePost rePost,Reply reply) {
		if(rePost == null || reply == null || reply.getContent() == null)
			return false;
		rePost.addReply(reply);
		weaverDao.updateInfo(reply.getWriter(),"weaverInfo.myReplysCount",1); //자신의 댓글 갯수 증가
		weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.replysCount",1); //답변에 달린 댓글 갯수 증가
		rePostDao.update(rePost);
		return true;
	}
	
	/** 댓글을 삭제함.
	 * @param rePost
	 * @param reply
	 * @return
	 */
	public boolean deleteReply(RePost rePost,Weaver weaver,int number) {
		
		if(rePost == null || weaver == null)
			return false;
		Weaver replyWriter = rePost.getReplyWriter(number);
		
		if(replyWriter == null || !rePost.removeReply(weaver, number))
			return false;
		
		weaverDao.updateInfo(replyWriter,"weaverInfo.myReplysCount",-1); //자신의 댓글 갯수 감소
		weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.replysCount",-1); //답변에 달린 댓글 갯수 감소
		rePostDao.update(rePost);
		return true;
	}

	/** 답변들을 가져옴
	 * @param ID
	 * @param kind
	 * @param sort
	 * @return
	 */
	public List<RePost> gets(int ID,int kind,String sort) {
		return rePostDao.gets(ID,kind,sort);
	}

	public RePost get(int rePostID) {
		return rePostDao.get(rePostID);
	}

	public boolean push(RePost rePost, Weaver weaver) {
		if(rePost == null || weaver == null)
			return false;
		rePost.push();
		Cache cache = cacheManager.getCache("push");
		Element element = cache.get("re"+rePost.getRePostID());
		if (element == null || (element != null && element.getValue() == null)) {
			rePostDao.update(rePost);
			Element newElement = new Element("re"+rePost.getRePostID(), weaver.getId());
			cache.put(newElement);
			weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePostPush",1); //자신의 답변 추천수 늘림
			weaverDao.update(rePost.getWriter());
			return true;
		}
		return false;
	}

	public boolean update(RePost rePost,String[] removeDataList){
		if(rePost == null)
			return false;
		if(removeDataList != null)
			for(String dataName: removeDataList){
				dataDao.delete(rePost.getData(dataName));
				rePost.deleteData(dataName);
			}
		rePostDao.update(rePost);
		return true;
	}

	/** 글에 쓴 답변을 삭제할 때
	 * @param post
	 * @param rePost
	 * @param weaver
	 * @return
	 */
	public boolean delete(Post post,RePost rePost,Weaver weaver){

		if(post == null ||rePost == null || weaver == null)
			return false;
		
		if(rePost.getWriterName().equals(weaver.getId()) 
				||  weaver.isAdmin()){
			
			for(Reply reply : rePost.getReplys()){ // 댓글들을 가져옴.
				weaverDao.updateInfo(reply.getWriter(),"weaverInfo.myReplysCount",-1); //댓글단 사람들의 댓글 갯수 삭제.
				weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.replysCount",-1); //답변에 댓글 점수 삭감.
			}
			weaverDao.updateInfo(post.getWriter(),"weaverInfo.rePostCount",-1); //글에 달린 답변갯수 삭감.
			post.rePostCountDown();
			postDao.update(post);
			weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePostCount",-1); //내가 단 답변갯수 삭감.
			weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePostPush",-rePost.getPush());  //내가 단 답변 추천 삭감.
			rePostDao.delete(rePost);
			return true;
		}
		return false;
	}

	/** 코드에 단 댓글을 삭제할 때
	 * @param code
	 * @param rePost
	 * @param weaver
	 * @return
	 */
	public boolean delete(Code code,RePost rePost,Weaver weaver){

		if(code == null ||rePost == null || weaver == null)
			return false;
		
		if(rePost.getWriterName().equals(weaver.getId()) 
				||  weaver.isAdmin()){
			
			for(Reply reply : rePost.getReplys()){ // 댓글들을 가져옴.
				weaverDao.updateInfo(reply.getWriter(),"weaverInfo.myReplysCount",-1); //댓글단 사람들의 댓글 갯수 삭제.
				weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.replysCount",-1); //답변에 댓글 점수 삭감.
			}
			
			weaverDao.updateInfo(code.getWriter(),"weaverInfo.codeRePostCount",-1); //글에 달린 답변갯수 삭감.
			code.rePostCountDown();
			codeDao.update(code);
			weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePost",-1); //내가 단 답변갯수 삭감.
			weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePostPush",-rePost.getPush());  //내가 단 답변 추천 삭감.
			rePostDao.delete(rePost);
			return true;
		}
		return false;
	}

}