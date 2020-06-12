package com.zuci.electron.notification;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class SessionConnectEventListener implements ApplicationListener<SessionConnectEvent>{

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		// TODO Auto-generated method stub
	    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
	    System.out.println("SessionConnectEventListener.onApplicationEvent() : "+sha.getSessionId());
	    //String  company = sha.getNativeHeader("company").get(0);
	    //logger.debug("Connect event [sessionId: " + sha.getSessionId() +"; company: "+ company + " ]");
	}

}
