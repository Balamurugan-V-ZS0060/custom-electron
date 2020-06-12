package com.zuci.electron.notification;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class SessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {

	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
		
		//System.out.println("SessionConnectedEventListener.onApplicationEvent() : " + sha.getSessionId() +" , "+sha.getUser().getName() +" , "+sha.getId());
		System.out.println("SessionConnectedEventListener.onApplicationEvent() : "+ sha.getSessionAttributes());
		System.out.println("SessionConnectedEventListener.onApplicationEvent() : "+ sha.getMessageHeaders());
	}

}
