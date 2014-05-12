package com.forweaver.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtil {

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

	public static String stringToMarkup(String str) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("\\[img\\s(http://\\S*)\\]");
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();

		while (m.find())
			m.appendReplacement(sb,
					"<div style='text-align:center'> <img class = 'post-img' src='" + m.group(1) + "'></div>");
		m.appendTail(sb);
		str = sb.toString();
		sb = new StringBuffer();
		p = Pattern.compile("\\[link\\s(.*?)\\s(http://\\S*)\\]");
		m = p.matcher(str);
		while (m.find()) 
			m.appendReplacement(sb,
					"<a class = 'post-link' href='" + m.group(2) + "'>"+ m.group(1) + "</a>");
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	public static String stringToMarkup(String str,Map<String,String> fileMap) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("\\[img\\s(http://\\S*)\\]");
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();

		while (m.find())
			m.appendReplacement(sb,
					"<div style='text-align:center'> <img class = 'post-img' src='" + m.group(1) + "'></div>");
		m.appendTail(sb);
		str = sb.toString();
		sb = new StringBuffer();
		p = Pattern.compile("\\[link\\s(.*?)\\s(http://\\S*)\\]");
		m = p.matcher(str);
		while (m.find()) 
			m.appendReplacement(sb,
					"<a class = 'post-link' href='" + m.group(2) + "'>"+ m.group(1) + "</a>");
		m.appendTail(sb);
		str = sb.toString();
		sb = new StringBuffer();
		p = Pattern.compile("\\[tmpimg\\s(.*?)\\]");
		m = p.matcher(str);
		while (m.find()) {
			m.appendReplacement(sb,
					"<div style='text-align:center'>"
					+ " <img class = 'post-img' src='http://127.0.0.1:8080/data/" + fileMap.get(m.group(1)) + "'></div>");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	public static String simpleStringToMarkup(String str) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("\\[link\\s(.*?)\\s(http://\\S*)\\]");
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();

		while (m.find()) 
			m.appendReplacement(sb,
					"<a class = 'post-link' href='" + m.group(2) + "'>"+ m.group(1) + "</a>");
		m.appendTail(sb);
		return sb.toString();
	}

	public static String markupToString(String str) {
		// TODO Auto-generated method stub
		Pattern p = 
				Pattern.compile("<div style='text-align:center'> <img class = 'post-img' src='(http://.*?)'></div>");
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();

		while (m.find())
			m.appendReplacement(sb,
					"[img " + m.group(1) + "]");
		m.appendTail(sb);
		str = sb.toString();
		sb = new StringBuffer();
		p = Pattern.compile("<a class = 'post-link' href='(http://.*?)'>(.*?)</a>");
		m = p.matcher(str);
		while (m.find()) 
			m.appendReplacement(sb,
					"[link " + m.group(2) + " "+ m.group(1) + "]");
		m.appendTail(sb);
		return sb.toString();
	}

}
