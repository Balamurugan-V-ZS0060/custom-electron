package com.zuci.electron;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

@Component
public class CustomHttpSessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("CustomHttpSessionListener.sessionCreated() : "+se.getSession().getId());
		HttpSessionListener.super.sessionCreated(se);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("CustomHttpSessionListener.sessionDestroyed() : "+se.getSession().getId());
		HttpSessionListener.super.sessionDestroyed(se);
		
	}
	
}
