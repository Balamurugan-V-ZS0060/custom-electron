package com.zuci.electron.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zuci.electron.config.AppConfig;


public class Utils {
	public  static void sendForgetPasswordMail(AppConfig appConfig , String toEmailId) throws AddressException, MessagingException {
		Properties mailServerProperties = System.getProperties();
			
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(mailServerProperties, null);
		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailId));
		
		mimeMessage.setSubject("Forgot Password Recovery - electron");
		String emailBody = "<br>Hello,<br>" + 
				"We understand that you have forgotten your password to access the <br>electron."
				+"\n Please use the below link to set new password \n"
				+"<br><br><a href='http://localhost:8099/electron/recreatepwd'>Reset Password</a><br>" + 
				 "<br><br> Thanks, <br>Electron Admin";
		mimeMessage.setContent(emailBody, "text/html");
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com",appConfig.getSmtpusername(), appConfig.getSmtppassword());
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
	}
	
	public  static void sendPwdResetSuccessMail(AppConfig appConfig , String toEmailId) throws AddressException, MessagingException {
		Properties mailServerProperties = System.getProperties();
			
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(mailServerProperties, null);
		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailId));
		
		mimeMessage.setSubject("Reset Password - electron");
		String emailBody = "<br>Hello,<br>" + 
				"\n Your password has been updated. Please start using the new credentials. \n"+
				 "<br><br> Thanks, <br>Electron Admin";
		mimeMessage.setContent(emailBody, "text/html");
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com",appConfig.getSmtpusername(), appConfig.getSmtppassword());
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
	}
}
