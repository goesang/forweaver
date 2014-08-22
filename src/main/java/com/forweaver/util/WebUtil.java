package com.forweaver.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

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
		try {
			return new Markdown4jProcessor().process(str);
		} catch (IOException e) {
			return str;
		}
	}
}
