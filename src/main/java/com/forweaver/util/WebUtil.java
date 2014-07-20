package com.forweaver.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import com.maxmind.geoip.LookupService;
import org.markdown4j.Markdown4jProcessor;

//각종 웹 유틸 클래스
public class WebUtil {
	//MD5 변환을 위한 메서드
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
	//스크립트 해킹을 막기 위한 메서드입니다.
	public static String convertHtml(String str) {
		// TODO Auto-generated method stub
		if (str.length() == 0)
			return "";
		str = str.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		str = str.replace("\n", "<br>");
		return str;
	}

	public static String removeHtml(String str) {
		// TODO Auto-generated method stub
		if (str.length() == 0)
			return "";
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		return str;
	}

	public static String spacialSignEncoder(String str) {
		// TODO Auto-generated method stub
		str = str.replace(" ", "@$@");
		str = str.replace("+", "@#@");
		str = str.replace("%", "@!@");
		str = str.replace("&", "@4@");
		return str;
	}

	public static String specialSignDecoder(String str) {
		// TODO Auto-generated method stub
		str = str.replace("@$@", " ");
		str = str.replace("@#@", "+");
		str = str.replace("@!@", "%");
		str = str.replace("@4@", "&");
		return str;
	}
	
	public static String markDownEncoder(String str) {
		//마크 다운 코드가 들어가면 html 코드로 바뀜
		/*# This is an H1						<h1>This is an H1</h1>
		## This is an H2			-> 			<h2>This is an H2</h2>
		### This is an H3						<h3>This is an H3</h3>*/
		try {
			str = new Markdown4jProcessor().process(str);
		} catch (IOException e) {
			return str;
		}
		return str;
	}
	public static OutputStream pptToImg(InputStream is){
		// ppt 파일을 이미지로 출력해주는 메서드
		// 때에 따라서 매개변수 타입과 반환값의 타입이 다를 수 있음.
		return null;
	}
	
	//hackDay - [Docking] 문서에 접속한 사용자 간 작업 위치 표시 
	public static String ipToAddress(String ip){
		LookupService lookup = null;
		  
		try {
		   lookup = new LookupService("/home/go/GeoLiteCity.dat", LookupService.GEOIP_MEMORY_CACHE);
		} catch(IOException e) {
		   e.printStackTrace(System.out);
		}
		
		System.out.println(lookup.getLocation("218.209.82.120").latitude);
		System.out.println(lookup.getLocation("218.209.82.120").longitude);
		System.out.println(lookup.getLocation("218.209.82.120").city);
		System.out.println(lookup.getLocation("218.209.82.120").city);
		return null;
	}

}
