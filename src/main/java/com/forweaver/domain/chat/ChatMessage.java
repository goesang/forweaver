package com.forweaver.domain.chat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** 채팅 메시지 보관용 클래스
 * message 실제 메시지
 * weaver 회원 정보
 * date 시간
 */
public class ChatMessage  implements Serializable{
	
	static final long serialVersionUID = 19911217L;
	
	private String message;
	private String weaver;
	private String date;
	public ChatMessage(String message, String weaver) {
		super();
		this.message = message;
		this.weaver = weaver;
		SimpleDateFormat formatter = new SimpleDateFormat ( "dd일 hh:mm:ss", Locale.KOREA );
		Date currentTime = new Date ( );
		this.date = formatter.format ( currentTime );
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWeaver() {
		return weaver;
	}
	public void setWeaver(String weaver) {
		this.weaver = weaver;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	
	
}
