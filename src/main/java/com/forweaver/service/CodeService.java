package com.forweaver.service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

@Service
public class CodeService {

	@Autowired CodeDao codeDao;

	public void add(Code code, MultipartFile file) {
		try {
			if ((file.getContentType().equals("application/zip") ||
					file.getContentType().equals("application/x-zip-compressed")) && 
					file.getOriginalFilename().endsWith(".zip")) { 
				// zip파일의 경우 내부를 살펴봄
				ZipInputStream in = new ZipInputStream(file.getInputStream());
				ZipEntry entry = in.getNextEntry();
				while (entry != null) {
					if (!entry.isDirectory()) { // 만약 파일의 경우
						byte[] buf = new byte[1024];
						int len;
						String content = "";
						while ((len = in.read(buf)) != -1)
						{
							content += new String(buf, 0, len);
						}

						if (entry.getName().toUpperCase().endsWith("README.MD")){ // 리드미파일의 경우
							code.setReadme(content);
							code.addFirstSimpleCode(new SimpleCode(entry.getName(),content)); // 일반 파일의 경우
						}else{
							code.addSimpleCode(new SimpleCode(entry.getName(),content)); // 일반 파일의 경우
						}
					}
					entry = in.getNextEntry();
				}
				in.close();
				codeDao.insert(code);
			} else if(file.getOriginalFilename().endsWith(".c") || file.getOriginalFilename().endsWith(".h")|| file.getOriginalFilename().endsWith(".ino")
					|| file.getOriginalFilename().endsWith(".java")|| file.getOriginalFilename().endsWith(".py")|| file.getOriginalFilename().endsWith(".cpp")
					|| file.getOriginalFilename().endsWith(".html")|| file.getOriginalFilename().endsWith(".css")|| file.getOriginalFilename().endsWith(".pl")
					|| file.getOriginalFilename().endsWith(".sql")|| file.getOriginalFilename().endsWith(".php")|| file.getOriginalFilename().endsWith(".cs")
					|| file.getOriginalFilename().endsWith(".rb")|| file.getOriginalFilename().endsWith(".txt")|| file.getOriginalFilename().endsWith(".js")
					|| file.getOriginalFilename().endsWith(".xml")|| file.getOriginalFilename().endsWith(".md")){ // 압축파일이 아닌 일반 파일의 경우
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

	public Code get(int codeID) {
		return codeDao.get(codeID);
	}

	public void dowloadCode(Code code, OutputStream os){
		try {
			ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(os));
			for(SimpleCode simpleCode :code.getCodes()){
				zip.putNextEntry(new ZipEntry(simpleCode.getFileName()));
				zip.write(simpleCode.getContent().getBytes());
			}	
			code.download();
			codeDao.update(code);
			zip.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public long countCodes(String sort) {
		return codeDao.countCodes(null, null, null, sort);
	}

	public List<Code> getCodes(String sort, int page, int size) {
		return codeDao.getCodes(null, null, null, sort, page, size);
	}

	public long countCodesWithTags(List<String> tags, String sort) {
		return codeDao.countCodes(tags, null, null, sort);
	}

	public List<Code> getCodesWithTags(List<String> tags, String sort,
			int page, int size) {
		return codeDao.getCodes(tags, null, null, sort, page, size);
	}

	public long countCodesWithTagsAndSearch(List<String> tags, String serach,
			String sort) {
		return codeDao.countCodes(tags, serach, null, sort);
	}

	public List<Code> getCodesWithTagsAndSearch(List<String> tags,
			String serach, String sort, int page, int size) {
		return codeDao.getCodes(tags, serach, null, sort, page, size);
	}

	public long countCodesWhenWeaverHome(Weaver weaver, String sort) {
		return codeDao.countCodes(null, null, weaver, sort);
	}

	public List<Code> getCodesWhenWeaverHome(Weaver weaver, String sort,
			int page, int size) {
		return codeDao.getCodes(null, null, weaver, sort, page, size);
	}

	public long countCodesWhenWeaverHomeWithTags(List<String> tags,
			Weaver weaver, String sort) {
		return codeDao.countCodes(tags, null, weaver, sort);
	}

	public List<Code> getCodesWhenWeaverHomeWithTags(List<String> tags,
			Weaver weaver, String sort, int page, int size) {
		return codeDao.getCodes(tags, sort, weaver, sort, page, size);
	}

	public long countCodesWhenWeaverHomeWithTagsAndSeach(List<String> tags,
			Weaver weaver, String serach, String sort) {
		return codeDao.countCodes(tags, serach, weaver, sort);
	}

	public List<Code> getCodesWhenWeaverHomeWithTagsAndSeach(List<String> tags,
			Weaver weaver, String serach, String sort, int page, int size) {
		return codeDao.getCodes(tags, serach, weaver, sort, page, size);
	}

	public void delete(Code code) {
		codeDao.delete(code);
	}

	public void update(Code code) {
		codeDao.update(code);
	}

}
