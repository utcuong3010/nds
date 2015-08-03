package com.mbv.security.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.ui.AuthenticationDetailsSource;
import com.mbv.services.xsecd.UserRequestInfo;
import javax.servlet.http.HttpServletRequest;

public class EWalletAuthenticationDetailsSource implements
		AuthenticationDetailsSource {
	static final Log LOG = LogFactory.getLog(EWalletAuthenticationDetailsSource.class);
	
	@Override
	public Object buildDetails(Object request) {
		if (request instanceof HttpServletRequest) {
			
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			UserRequestInfo userRequest = new UserRequestInfo();
			userRequest.setSessionId(httpRequest.getSession().getId());
			userRequest.setRemoteIP(httpRequest.getRemoteAddr());
			userRequest.setRemoteAgent(httpRequest.getHeader("User-Agent"));
			userRequest.setServerIP(httpRequest.getServerName());
			userRequest.setServerURL(httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort());
			userRequest.setRequestURI(httpRequest.getRequestURI().
					replace(httpRequest.getContextPath(), ""));
			if (httpRequest.getQueryString()!=null && !httpRequest.getQueryString().equals(""))
				userRequest.setRequestURI(userRequest.getRequestURI() + "?" + httpRequest.getQueryString());
			LOG.debug("RequestURI for domain resolve: " + userRequest.getRequestURI());
			return userRequest;
		}
		// TODO Auto-generated method stub
		return null;
	}
}
