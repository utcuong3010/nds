package com.mbv.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.util.TextUtils;

public class AuthenticationProcessingFilter extends AbstractProcessingFilter{
	
	String usernameParameter="username";
	String passwordParameter="password";
	String authenticationUrl="login.action";
	AuthenticationDetailsSource authenticationDetailsSource;
	public AuthenticationProcessingFilter() {
		this.usernameParameter="username";
		this.passwordParameter="password";
	}
	 public Authentication attemptAuthentication(HttpServletRequest request)
     throws AuthenticationException
 {
     String username = obtainUsername(request);
     String password = obtainPassword(request);
     if(username == null)
         username = "";
     if(password == null)
         password = "";
     username = username.trim();
     UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
     authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
     HttpSession session = request.getSession(false);
     if(session != null || getAllowSessionCreation())
         request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", TextUtils.escapeEntities(username));
//     setDetails(request, authRequest);
     
     return getAuthenticationManager().authenticate(authRequest);
 }

	 protected String obtainPassword(HttpServletRequest request)
	 {
	     return request.getParameter(passwordParameter);
	 }
	
	 protected String obtainUsername(HttpServletRequest request)
	 {
	     return request.getParameter(usernameParameter);
	 }
	
	 protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest)
	 {
	     authRequest.setDetails(super.authenticationDetailsSource.buildDetails(request));
	 }
	
	 public int getOrder()
	 {
	     return FilterChainOrder.AUTHENTICATION_PROCESSING_FILTER;
	 }
	
	
	 public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	 public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
	 public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

	protected String determineFailureUrl(HttpServletRequest request, AuthenticationException failed)
    {
		String result=super.determineFailureUrl(request, failed);
        return result;
    }
	
	 public String getDefaultFilterProcessesUrl()
	    {
	        return authenticationUrl;
	    }
	 
	
	public String getUsernameParameter() {
		return usernameParameter;
	}

	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	public String getPasswordParameter() {
		return passwordParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}
	public AuthenticationDetailsSource getAuthenticationDetailsSource() {
		return authenticationDetailsSource;
	}
	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}
	
	
}
