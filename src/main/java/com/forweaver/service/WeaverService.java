package com.forweaver.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;
import com.mongodb.DBObject;

@Service("userDetailsService")
public class WeaverService implements UserDetailsService {

	@Autowired 
	private WeaverDao weaverDao;
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired @Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;

	@Override
	public UserDetails loadUserByUsername(String id)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Weaver weaver = weaverDao.get(id);
		if (weaver == null)
			return null;
		return weaver;
	}

	public boolean idCheck(String id) { // 이름 중복 체크

		if (weaverDao.get(id) != null)
			return true;
		else
			return false;
	}

	public Weaver getCurrentWeaver() {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth.getName().equals("anonymousUser"))
			return null;
		return (Weaver) auth.getPrincipal();
	}

	public void add(Weaver weaver) { // 회원 추가 서비스
		Pass pass;
		if(weaverDao.existsWeaver())
			pass = new Pass("ROLE_USER"); 
		 else
			 pass = new Pass("ROLE_ADMIN"); // 최초 회원 가입시 운영자 지위
		weaver.addPass(pass);
		weaver.setPassword(passwordEncoder.encodePassword(weaver.getPassword(), null));
		weaverDao.insert(weaver);
		File file = new File(GitUtil.GitPath + weaver.getId());
		file.mkdir();
	}

	public void update(Weaver weaver) { // 회원 수정
		// TODO Auto-generated method stub
		weaver.setPassword(passwordEncoder.encodePassword(weaver.getPassword(), null));
		weaverDao.update(weaver);
	}

	public Weaver get(String id) { // 회원이름으로 회원 불러오기
		Weaver weaver = this.getLoginWeaver(id);
		if (weaver == null)
			weaver = weaverDao.get(id);
		return weaver;
	}



	public Weaver getLoginWeaver(String id) {

		for (Object object : sessionRegistry.getAllPrincipals()) {
			Weaver weaver = ((Weaver) object);
			if (weaver.getId().equals(id))
				return weaver;
		}
		return null;
	}


	// 프로젝트 삭제시 로그인 된 위버의 pass 삭제
	public void deletePass(String passName) {
		for (Weaver weaver : weaverDao.searchPassName(passName)) {
			weaver.deletePass(passName);
			weaverDao.update(weaver);

			Weaver currentWeaver = getLoginWeaver(weaver.getId()); //만약 로그인한 회원이라면
			if (currentWeaver != null)
				currentWeaver.deletePass(passName);
		}

	}


	public List<Weaver> weavers(int page,int size) {
		// TODO Auto-generated method stub
		return weaverDao.list(page,size);
	}

	public void delete(Weaver weaver) { //위버 삭제
		// TODO Auto-generated method stub
		weaverDao.delete(weaver);
		try {
			FileUtils.deleteDirectory(new File(GitUtil.GitPath + weaver.getId()));
		} catch (Exception e) {
		}
	}

	public boolean autoLoginWeaver(Weaver weaver, HttpServletRequest request) {
		boolean result = true;

		try {
			request.getSession();
			Authentication auth = new UsernamePasswordAuthenticationToken(
					weaver, null, weaver.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (Exception e) {

			result = false;
		}

		return result;
	}

	/*
	//위버정보들과 수 파악함.
	public Object[] getWeaverInfos(List<String> tags,int page, int size ){
		List<Weaver> weavers = new ArrayList<Weaver>();
		HashMap<String, DBObject> weaverHash = new HashMap<String, DBObject>();
		Object[] returnObject = new Object[2];
		int startNumber = size * (page - 1);
		System.out.println(startNumber);
		System.out.println(size);
		try{
			for(DBObject db:weaverDao.getWeaverInfosInPost(tags)){
				String name = new ObjectMapper().readTree(db.get("_id").toString()).get("$id").toString();
				name = name.substring(1, name.length()-1);
				weaverHash.put(name, db);
			}
			for(DBObject db:weaverDao.getWeaverInfosInRePost(tags)){
				String name = new ObjectMapper().readTree(db.get("_id").toString()).get("$id").toString();
				DBObject dbTmp = weaverHash.get( name.substring(1, name.length()-1));
				if(dbTmp != null){
					dbTmp.put("myRePostCount", db.get("myRePostCount"));
					dbTmp.put("rePostPush", db.get("rePostPush"));
				}
				else
					weaverHash.put(name, db);
			}
			for(DBObject db:weaverDao.getWeaverInfosInProject(tags)){
				String name = new ObjectMapper().readTree(db.get("_id").toString()).get("$id").toString();
				DBObject dbTmp = weaverHash.get( name.substring(1, name.length()-1));
				if(dbTmp != null){
					dbTmp.put("projectCount", db.get("projectCount"));
					dbTmp.put("childProjects", db.get("childProjects"));
				}
				else
					weaverHash.put(name, db);
			}
			for(DBObject db:weaverDao.getWeaverInfosInLecture(tags)){
				String name = new ObjectMapper().readTree(db.get("_id").toString()).get("$id").toString();
				DBObject dbTmp = weaverHash.get( name.substring(1, name.length()-1));
				if(dbTmp != null){
					dbTmp.put("lectureCount", db.get("lectureCount"));
					dbTmp.put("joinWeavers", db.get("joinWeavers"));
				}
				else
					weaverHash.put(name, db);
			}
			for(DBObject db:weaverDao.getWeaverInfosInCode(tags)){
				String name = new ObjectMapper().readTree(db.get("_id").toString()).get("$id").toString();
				DBObject dbTmp = weaverHash.get( name.substring(1, name.length()-1));
				if(dbTmp != null){
					dbTmp.put("codeCount", db.get("codeCount"));
					dbTmp.put("downCount", db.get("downCount"));
				}
				else
					weaverHash.put(name, db);
			}
			
			int i = 0;
			for(DBObject db:weaverHash.values()){
				if(startNumber <= i){
					String name = new ObjectMapper().readTree(db.get("_id").toString()).get("$id").toString();
					name = name.substring(1, name.length()-1);
					Weaver weaver = weaverDao.get(name);
					if(weaver != null){
						weaver.setWeaverInfo(db);
						weavers.add(weaver);
					}
				}
				if(startNumber+size <= i)
					break;
				i++;	
			}
			
		}
		finally{
			returnObject[0] = weaverHash.values().size();
			returnObject[1] = weavers;
			return returnObject;
		}
	}
	
	*/

}
