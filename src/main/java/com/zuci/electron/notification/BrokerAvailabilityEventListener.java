package com.zuci.electron.notification;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

public class BrokerAvailabilityEventListener implements ApplicationListener<BrokerAvailabilityEvent>{

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		// TODO Auto-generated method stub
		System.out.println("BrokerAvailabilityEventListener.onApplicationEvent() : "+event.isBrokerAvailable());
	}
}
