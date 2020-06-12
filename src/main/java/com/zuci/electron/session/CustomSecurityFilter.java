package com.zuci.electron.session;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

//@Component
public class CustomSecurityFilter extends GenericFilterBean {

	@Autowired
	FindByIndexNameSessionRepository<? extends Session> sessions;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false);

		if (session != null && session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
			Collection<? extends Session> usersSessions = this.sessions.findByPrincipalName(cud.getUsername()).values();
			System.out.println("HomeController.checkIfUserHasAlreadyLoggedIn() : " + usersSessions);
			if (usersSessions.isEmpty()) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"The token is not valid.");
			}
			// do whatever you need here with the UserDetails
		} else {
			chain.doFilter(request, response);
		}
	}
}
