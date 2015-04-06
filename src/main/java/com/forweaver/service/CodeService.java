package com.forweaver.service;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Code;
import com.forweaver.domain.SimpleCode;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.CodeDao;
import com.forweaver.mongodb.dao.RePostDao;
import com.forweaver.util.WebUtil;

/** 코드 서비스
 *
 */
@Service
public class CodeService {

	@Autowired CodeDao codeDao;
	@Autowired RePostDao rePostDao;

	/** 코드를 추가함.
	 * @param code
	 * @param file
	 */
	public void add(Code code, MultipartFile file) {
		try {
			if ((file.getContentType().equals("application/zip") ||
					file.getContentType().equals("application/x-zip-compressed")) && 
					file.getOriginalFilename().toLowerCase().endsWith(".zip")) { 
				// zip파일의 경우 내부를 살펴봄
				ZipInputStream in = new ZipInputStream(file.getInputStream(),Charset.forName("8859_1"));
				ZipEntry entry = in.getNextEntry();
				while (entry != null) {
					if (!entry.isDirectory() && (!entry.getName().endsWith(".class") || 
							!entry.getName().endsWith(".bak") ||
							!entry.getName().endsWith(".log"))) { // 만약 파일의 경우
						byte[] buf = new byte[1024];
						int len;
						String content = "";
						while ((len = in.read(buf)) != -1)
							
							if(WebUtil.isCodeName(new String(entry.getName().getBytes("8859_1"),"EUC-KR")))
								content += new String(buf, 0, len,Charset.forName("EUC-KR"));
							else
								content += new String(buf, 0, len,Charset.forName("8859_1"));
						

						if (entry.getName().toUpperCase().endsWith("README.MD")){ // 리드미파일의 경우
							code.setReadme(content);
							code.addFirstSimpleCode(new SimpleCode(entry.getName(),content));
						}else{
							
							code.addSimpleCode(new SimpleCode(new String(entry.getName().getBytes("8859_1"),"EUC-KR"),content)); // 일반 파일의 경우
						}
					}
					entry = in.getNextEntry();
				}
				in.close();
				codeDao.insert(code);
			} else if(WebUtil.isCodeName(file.getOriginalFilename())){ // 압축파일이 아닌 일반 파일의 경우
				byte[] buf = new byte[1024];
				int len;
				String content = "";
				InputStream is = file.getInputStream();
				while ((len = is.read(buf)) != -1)
				{
					content += new String(buf, 0, len);
				}
				code.addSimpleCode(new SimpleCode(file.getOriginalFilename(), content)); // 일반 파일의 경우
				codeDao.insert(code);
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		} 
	}

	/** 코드를 가져옴
	 * @param codeID
	 * @return
	 */
	public Code get(int codeID,boolean onlyCode) {
		Code code = codeDao.get(codeID) ;
		if(code != null && onlyCode)
			code.onlyViewCode();
		return code;
	}

	/** 코드를 다운로드함.
	 * @param code
	 * @param os
	 */
	public void dowloadCode(Code code, OutputStream os){
		try {
			ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(os),Charset.forName("8859_1"));
			for(SimpleCode simpleCode :code.getCodes()){
				zip.putNextEntry(new ZipEntry(new String (simpleCode.getFileName().getBytes(),"8859_1") ));
				if(WebUtil.isCodeName(new String(simpleCode.getFileName().getBytes("8859_1"),"EUC-KR")))
					zip.write(simpleCode.getContent().getBytes("EUC-KR"));
				else
					zip.write(simpleCode.getContent().getBytes("8859_1"));
				
			}	
			code.download();
			codeDao.update(code);
			zip.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/** 코드를 검색하고 갯수를 파악함.
	 * @param weaver
	 * @param tags
	 * @param serach
	 * @param sort
	 * @return
	 */
	public long countCodes(Weaver weaver,List<String> tags,
			 String search, String sort) {
		return codeDao.countCodes(weaver, tags, search, sort);
	}

	/** 코드를 검색함.
	 * @param weaver
	 * @param tags
	 * @param search
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Code> getCodes(Weaver weaver, List<String> tags,
			String search, String sort, int page, int size) {
		return codeDao.getCodes(tags, search, weaver, sort, page, size);
	}


	/** 코드 삭제.
	 * @param weaver
	 * @param code
	 * @return
	 */
	public boolean delete(Weaver weaver,Code code) {
		if(weaver == null || code == null)
			return false;

		if(weaver.isAdmin() || weaver.getId().equals(code.getWriterName())){
			rePostDao.deleteAll(code);
			codeDao.delete(code);
			return true;
		}
		return false;
	}

	/** 코드 수정
	 * @param code
	 */
	public void update(Code code) {
		codeDao.update(code);
	}

}
