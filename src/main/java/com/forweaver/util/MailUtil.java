package com.forweaver.util;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**<p>메일을 보내기 위한 빈.</p>
 * <p>참고 - http://www.mkyong.com/spring/spring-sending-e-mail-via-gmail-smtp-server-with-mailsender/</p>
 *
 */
public class MailUtil {
	private MailSender mailSender;
	 
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void sendMail(String from, String to, String subject, String msg) {
 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
}
