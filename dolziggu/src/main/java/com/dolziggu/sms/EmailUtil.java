package com.dolziggu.sms;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.glorial.util.StringUtil;

public class EmailUtil {
	/*
	* 다음과 같은 에러가 발생할 경우 gmail 계정을 2단계 인증으로 등록하고, 위 소스의 pwd란에 gmail용 비밀번호가 아닌 ACCESS 용 비밀번호를 등록해야 한다.
		535-5.7.8 Username and Password not accepted. Learn more at
		535 5.7.8 http://support.google.com/mail/bin/answer.py?answer=xxxx xxxxxx
		javax.mail.AuthenticationFailedException
		
		* gmail 2단계 인증하여 비밀번호 등록하는 법
		
		1. https://myaccount.google.com/
		2. https://accounts.google.com/b/0/SmsAuthConfig?hl=ko 
			> 설정 시작
		
		3. 재로그인
		4. https://accounts.google.com/b/0/SmsAuthSettings?Setup=1
			> 전화번호 입력 후 코드 전송
			> 인증코드 입력
		
		5. https://security.google.com/settings/security/apppasswords?pli=1
			> 기기선택과 앱(MAIL) 선택 후 생성
		6. 생성된 비밀번호를 위 소스의 pwd란에 입력한다. 
	
	 */
	public static void sendEmail(Map defaultInfo, Map emailInfo) throws Exception{
		Properties p = System.getProperties();
	    p.put("mail.smtp.starttls.enable","true");
		p.put("mail.smtp.user", "wjdgodnjs@gmail.com"); 
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465"); 
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		p.put("mail.smtp.socketFactory.fallback", "false");
	       
	    Authenticator auth = new Authenticator() {
	    	protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication("wjdgodnjs", "erqcbptbdeifhawh");
            }
		}; 
	    		
	    //session 생성 및  MimeMessage생성
	    Session session = Session.getDefaultInstance(p, auth);
		//session.setDebug(true);
	    MimeMessage msg = new MimeMessage(session);
	     
	    try{
	        //편지보낸시간
	        msg.setSentDate(new Date());
	        InternetAddress from = new InternetAddress() ;
	        from = new InternetAddress("dolziggu<"+StringUtil.nvl(defaultInfo.get("sender"))+">");
	        msg.setFrom(from);
	         
	        // 이메일 수신자
	        InternetAddress to = new InternetAddress("glorial@naver.com");
	        msg.setRecipient(Message.RecipientType.TO, to);
	         
	        // 이메일 제목
	        msg.setSubject(StringUtil.nvl(defaultInfo.get("title")), "UTF-8");
	         
	        // 이메일 내용
	        StringBuffer sb = new StringBuffer();
	        sb.append(StringUtil.nvl(emailInfo.get("itemNm"))+"<BR>");
	        sb.append(StringUtil.nvl(emailInfo.get("itemAmt"))+"<BR>");
	        sb.append(StringUtil.nvl(emailInfo.get("itemUrl"))+"<BR>");
	        // 이메일 헤더 
	    	msg.setContent(sb.toString(), "text/html;charset=utf-8");
	         
	        //메일보내기
	        javax.mail.Transport.send(msg);
	    }catch (AddressException addr_e) {
	        addr_e.printStackTrace();
	    }catch (MessagingException msg_e) {
	        msg_e.printStackTrace();
	    }
	}
}
