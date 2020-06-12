package com.zuci.electron;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.zuci.electron.websocket.OutputMessage;

@Component
public class CustomLogoutHandler implements LogoutHandler{

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		//request.getSession().invalidate();
		if(authentication != null) {
			String id = request.getSession(false) != null ? request.getSession(false).getId() : "";
			
			System.out.println("CustomLogoutHandler.logout() : "+id + " , "+authentication.getName());
			OutputMessage out = new OutputMessage(authentication.getName(), "LOGOUT",
					new SimpleDateFormat("HH:mm").format(new Date()));
			simpMessagingTemplate.convertAndSendToUser(authentication.getName(), "/queue/message", out);
				
		}
		
		/*
		 * try { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		System.out.println("CustomLogoutHandler.logout() : completed");
	}

}
