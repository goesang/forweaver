package com.forweaver.util;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**<pre>메일을 보내기 위한 빈.
 * 참고 - http://www.mkyong.com/spring/spring-sending-e-mail-via-gmail-smtp-server-with-mailsender/</pre> 
 *b17100d39d057e62
 */
public class MailUtil {
	private MailSender mailSender;
    private String from; 
    
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void sendMail(String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	
}
