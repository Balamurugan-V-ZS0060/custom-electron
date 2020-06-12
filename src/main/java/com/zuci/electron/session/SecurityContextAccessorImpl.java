package com.zuci.electron.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//@Component
public class SecurityContextAccessorImpl implements SecurityContextAccessor{


	  @Autowired
	  private AuthenticationTrustResolverImpl authenticationTrustResolver;

	  @Override
	  public boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication =
	        SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	  }
}
