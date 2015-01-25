package com.forweaver.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	/**	이전시간과 현재시간과의 차이를 계산하여 지난시간 반환
	 * @param date 날짜를 문자열로 받는다.
	 * @return 지난시간을 문자열로 반환한다. (Ex] 1초전, 1시간, 1년)
	 */
	public static String TimeLagTest(String date) throws ParseException {
		long timeLag, year, month, day;
		int hour, min, sec;
		String str;

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date getDate, setDate;

		getDate = format.parse(date);
		setDate = new Date();

		timeLag = setDate.getTime() - getDate.getTime();
		sec = (int) (timeLag / 1000);

		day = sec / 86400;

		if (day < 30)
			month = 0;
		else
			month = day / 30;

		if (month < 12)
			year = 0;
		else
			year = month / 12;

		if (sec < 60) {
			min = 0;
		} else {
			min = (int) (sec / 60);
			sec = (int) (sec % 60);
		}

		if (min < 60) {
			hour = 0;
		} else {
			hour = min / 60;
			min = min % 60;
		}

		if (day == 0) {
			if (min < 1) {
				str = new String(sec + "초전");
			} else if (hour < 1) {
				str = new String(min + "분전");
			} else {
				str = new String(hour + "시간");
			}
		} else {
			if (month < 1) {
				str = new String(day + "일");
			} else if (year < 1) {
				str = new String(month + "달");
			} else {
				str = new String(year + "년");
			}
		}
		return str;
	}
}
