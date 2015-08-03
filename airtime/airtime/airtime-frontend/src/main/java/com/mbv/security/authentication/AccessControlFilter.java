package com.mbv.security.authentication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.userdetails.UserDetails;

import com.mbv.helpers.FormatHelper;
import com.mbv.helpers.URLHelper;
import com.mbv.security.AtUserDetails;

public class AccessControlFilter extends SpringSecurityFilter 
	implements InitializingBean {
	static final Log log = LogFactory.getLog(AccessControlFilter.class);
	AuthorizationCheck authorizationCheck;
	String accessDeniedUrl;
	@Override
	protected void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) 
	throws IOException, ServletException 
	{	
		
		String resource=request.getRequestURI();
		if(StringUtils.isNotBlank(resource)&& resource.contains(".mbv")){
			if (!checkPermission(resource)){
				response.sendRedirect(request.getContextPath()+accessDeniedUrl);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}
	private boolean checkPermission(String resource){
		int end=resource.lastIndexOf(".mbv");
		String action=resource.substring(0,end);
		action=action.substring(action.lastIndexOf("/"),action.length()).replace("/", "");
		if (log.isDebugEnabled())
			log.debug("check access for action-"+action+"| AppId"+authorizationCheck.getObjectType()+"| actionList-"+authorizationCheck.getActionMap().get(action));
		if (!authorizationCheck.getActionMap().containsKey(action)){
			if (log.isDebugEnabled())
				log.debug("return false, because action doesn't exist in author list");
			return false;
		}
		try{
			SecurityContext sc = SecurityContextHolder.getContext();
			Authentication user = sc.getAuthentication();
			Object details = user.getPrincipal();
			if (details instanceof UserDetails == false) {
				return false;
			}
			AtUserDetails userDetails = (AtUserDetails) details;
			return authorizationCheck.checkPermission(userDetails.getSessionInfo().getSessionId(), action);
		}catch (Exception e) {
			return false;
		}
	}
	@Override
	public int getOrder() {
		return 0;
	}

	public AuthorizationCheck getAuthorizationCheck() {
		return authorizationCheck;
	}

	public void setAuthorizationCheck(AuthorizationCheck authorizationCheck) {
		this.authorizationCheck = authorizationCheck;
	}

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}
	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
}
