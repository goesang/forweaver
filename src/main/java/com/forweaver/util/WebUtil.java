package com.forweaver.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.markdown4j.Markdown4jProcessor;
//각종 웹 유틸 클래스
public class WebUtil {
	/** page url 부분을 해석하여 페이지 사이즈를 가져오는 메서드.
	 * @param pageUrl
	 * @return 페이지 사이즈
	 */
	public static int getPageSize(String pageUrl){  
		int size = 15;
		
		try{
			if(pageUrl.contains(",")){
				size = Integer.parseInt(pageUrl.split(",")[1]);
			}else{
				size =Integer.parseInt(pageUrl);
			}
		}finally{}
		
		return size;
	}
	
	/** page url 부분을 해석하여 페이지 번호를 가져오는 메서드.
	 * @param pageUrl
	 * @return 페이지의 번호
	 */
	public static int getPageNumber(String pageUrl){
		int page;
		try{
			if(pageUrl.contains(",")){
				page = Integer.parseInt(pageUrl.split(",")[0]);
			}else{
				page =Integer.parseInt(pageUrl);
			}
		}finally{}
		
		return page;
	}


	/** 문자열을 MD5 변환하여 암호화하는 메서드.
	 * @param str
	 * @return MD5로 변환된 문자열.
	 */
	public static String convertMD5(String str) {
		// TODO Auto-generated method stub
		String email = str;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(email.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			return number.toString(16);
		} catch (Exception e) {
			return "";
		}

	}

	
	/** xss 방지를 위해 <,> 제거 메서드
	 * @param str
	 * @return '<','>' 제거된 문자열
	 */
	public static String removeHtml(String str) {
		// TODO Auto-generated method stub
		if (str.length() == 0)
			return "";
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		return str;
	}
	
	/** 인코딩 에러를 방지하기 위해 문자열 변환 메서드
	 * @param str
	 * @return
	 */
	public static String spacialSignEncoder(String str) {
		// TODO Auto-generated method stub
		str = str.replace(" ", "@$@");
		str = str.replace("+", "@#@");
		str = str.replace("%", "@!@");
		str = str.replace("&", "@4@");
		return str;
	}
	
	/** 인코딩 에러를 방지하기 위해 문자열 변환 메서드
	 * @param str
	 * @return
	 */
	public static String specialSignDecoder(String str) {
		// TODO Auto-generated method stub
		str = str.replace("@$@", " ");
		str = str.replace("@#@", "+");
		str = str.replace("@!@", "%");
		str = str.replace("@4@", "&");
		return str;
	}

	
	/** 마크다운 문자열을 해석하여 html화된 문자열 변환하는 메서드.
	 * @param str
	 * @return html화된 문자열.
	 */
	public static String markDownEncoder(String str) {
		try {
			return new Markdown4jProcessor().process(str);
		} catch (IOException e) {
			return str;
		}
	}
}
