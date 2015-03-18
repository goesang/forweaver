package com.forweaver.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

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
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Data;
import com.forweaver.domain.Pass;
import com.forweaver.domain.RePassword;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;
import com.forweaver.util.MailUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service("userDetailsService")
public class WeaverService implements UserDetailsService {

	@Autowired 
	private WeaverDao weaverDao;
	@Autowired 
	private PasswordEncoder passwordEncoder;
	@Autowired @Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	@Autowired 
	private CacheManager cacheManager;
	@Autowired 
	private MailUtil mailUtil;
	@Autowired 
	private GitUtil gitUtil;

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
		File file = new File(gitUtil.getGitPath() + weaver.getId());
		file.mkdir();
	}

	public void update(Weaver weaver,String password,String newpassword,List<String> tags,String studentID,String say,MultipartFile image) { // 회원 수정
		// TODO Auto-generated method stub
		if(image != null && image.getSize() > 0)
			weaver.setImage(new Data(image, weaver.getId()));

		if(this.validPassword(weaver,password) && newpassword != null && newpassword.length() > 3)
			weaver.setPassword(passwordEncoder.encodePassword(newpassword, null));
		
		if(studentID != null && !studentID.equals(""))
			weaver.setStudentID(studentID);
		
		if(say != null && !say.equals(""))
			weaver.setSay(say);
		
		weaver.setTags(tags);
		weaverDao.update(weaver);
	}
	
	public void update(Weaver weaver) { // 회원 수정
		// TODO Auto-generated method stub
				
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
		return weaverDao.getWeavers(page,size);
	}

	public boolean delete(String password,Weaver weaver) { //위버 삭제
		// TODO Auto-generated method stub

		if(weaver == null || password == null || weaver.isAdmin())
			return false;

		if(weaver.getPassword().equals(passwordEncoder.encodePassword(password, null))){

			try {
				FileUtils.deleteDirectory(new File(gitUtil.getGitPath() + weaver.getId()));
				weaverDao.delete(weaver);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public boolean delete(Weaver adminWeaver,Weaver weaver) { //위버 삭제
		// TODO Auto-generated method stub

		if(adminWeaver == null || weaver == null || weaver.isAdmin())
			return false;

		if(adminWeaver.isAdmin()){
			try {
				FileUtils.deleteDirectory(new File(gitUtil.getGitPath() + weaver.getId()));
				weaverDao.delete(weaver);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
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


	/** 비밀번호를 재발급을 위한 메서드
	 * @param email
	 * @return 성공여부
	 */
	public boolean sendRepassword(String email){
		Cache rePasswordCache = cacheManager.getCache("repassword");
		Object object = rePasswordCache.get(email);

		if(object != null ||  weaverDao.get(email) == null) //등록된 이메일이 없을 경우.
			return false;
		String password = KeyGenerators.string().generateKey();
		String key = passwordEncoder.encodePassword(password, null);
		RePassword rePassword = new RePassword(key, password);

		//mailUtil.sendMail(email,"",""); 미구현.
		Element newElement = new Element(email, rePassword);
		rePasswordCache.put(newElement);

		return true;
	}

	/** 인증된 키를 통해 재발급된 비밀번호로 변경하는 메서드
	 * @param email
	 * @param key
	 * @return 성공여부
	 */
	public boolean changePassword(String email,String key){
		Cache rePasswordCache = cacheManager.getCache("repassword");
		Element element = rePasswordCache.get(email);
		if(element == null)
			return false;
		RePassword rePassword =  (RePassword)element.getValue();

		if(rePassword.getKey().equals(key)){
			Weaver weaver = weaverDao.get(email);
			weaver.setPassword(passwordEncoder.encodePassword(rePassword.getPassword(),null));	
			weaverDao.update(weaver);
		}
		return true;
	}

	/** 회원의 원래 비밀번호와 입력한 비밀번호가 같은지 비교하는 메서드.
	 * @param weaver
	 * @param password
	 * @return
	 */
	public boolean validPassword(Weaver weaver,String password){
		if(password != null && password.length()>3 && 
				weaver.getPassword().equals(passwordEncoder.encodePassword(password, null)))
			return true;
		return false;
	}

	public void getWeaverInfos(Weaver weaver){
		BasicDBObject basicDB = new BasicDBObject();
		DBObject tempDB = weaverDao.getWeaverInfosInPost(weaver);

		tempDB = weaverDao.getWeaverInfosInPost(weaver);
		if(tempDB != null){
			basicDB.put("postCount", tempDB.get("postCount"));
			basicDB.put("push", tempDB.get("push"));
			basicDB.put("rePostCount", tempDB.get("rePostCount"));
		}
		tempDB = weaverDao.getWeaverInfosInRePost(weaver);
		if(tempDB != null){
			basicDB.put("myRePostCount", tempDB.get("myRePostCount"));
			basicDB.put("rePostPush", tempDB.get("rePostPush"));
		}
		tempDB = weaverDao.getWeaverInfosInProject(weaver);
		if(tempDB != null){
			basicDB.put("projectCount", tempDB.get("projectCount"));
			basicDB.put("childProjects", tempDB.get("childProjects"));
		}
		tempDB = weaverDao.getWeaverInfosInLecture(weaver);
		if(tempDB != null){
			basicDB.put("lectureCount", tempDB.get("lectureCount"));
			basicDB.put("joinWeavers", tempDB.get("joinWeavers"));
		}
		tempDB = weaverDao.getWeaverInfosInCode(weaver);
		if(tempDB != null){
			basicDB.put("codeCount", tempDB.get("codeCount"));
			basicDB.put("downCount", tempDB.get("downCount"));
		}
	}
	
	public long countWeavers(){
		return weaverDao.countWeavers();
	}
	
	public List<Weaver> getWeavers(int page, int size) {
		List<Weaver> weavers = weaverDao.getWeavers(page, size);
		for(Weaver weaver : weavers)
			this.getWeaverInfos(weaver);
		return weavers;
	}
	
	/** 태그를 가지고 위버를 검색하고 활동내역도 검색함.
	 * @param tags
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Weaver> getWeavers(List<String> tags,int page, int size ){
		List<Weaver> weavers = weaverDao.getWeavers(tags,page, size);
		for(Weaver weaver : weavers)
			this.getWeaverInfos(weaver);
		return weavers;
	}
	

	/** 태그를 가지고 위버를 검색하고 숫자를 셈.
	 * @param tags
	 * @return
	 */
	public long countWeavers(List<String> tags){
		return weaverDao.countWeavers(tags);
	}


}
