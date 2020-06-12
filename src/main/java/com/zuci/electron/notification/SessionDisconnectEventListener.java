package com.zuci.electron.notification;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class SessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent>{

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
		System.out.println("SessionDisconnectEventListener.onApplicationEvent() : " + sha.getSessionId());
		//System.out.println("SessionDisconnectEventListener.onApplicationEvent() : " + sha.getSessionId() +" , "+sha.getUser().getName() +" , "+sha.getId());
	}

}
