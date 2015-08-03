package com.mbv.security.authentication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.SpringSecurityFilter;

/**
 * This filter is supposed to put 
 * RIGHT BEHIND SecurityContextHolderAwareRequestFilter
 * to keep the session alive after it is awared
 * @author Nam Pham
 *
 */
public class KeepAliveAuthenticationFilter extends SpringSecurityFilter 
	implements InitializingBean {
	static final Log LOG = LogFactory.getLog(XsecdAuthenticationProvider.class);
	ArrayList<Pattern> ignoredServletPaths = new ArrayList<Pattern>();
	
	KeepAliveAuthenticationProvider keepAliveAuthenticationProvider;
	
	public void setKeepAliveAuthenticationProvider(KeepAliveAuthenticationProvider value)
	{
		keepAliveAuthenticationProvider = value;
	}
	
	public void setIgnoredServletPaths(List<String> patterns) {
		ignoredServletPaths.clear();
		for (String pattern:patterns) {
			ignoredServletPaths.add(Pattern.compile(pattern));
		}
	}
	
	@Override
	protected void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) 
	throws IOException, ServletException 
	{	
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication user = sc.getAuthentication();
		if (user==null || !user.isAuthenticated()) {
			LOG.debug("User is not authenticated! Not going to call additional authentication check.");
		}
		else {
			String servletPath = request.getServletPath();
			boolean bolIgnored = false;
			for (Pattern ignoredPaths:ignoredServletPaths) {
				if (ignoredPaths.matcher(servletPath).matches()) {
					bolIgnored = true;
					LOG.debug("Path " + servletPath + " matches ignored pattern " + ignoredPaths.pattern());
					break;
				}
			}
			try {
				if (!bolIgnored)
					keepAliveAuthenticationProvider.keepAlive(user);
			}
			catch (AuthenticationException ex) {
				LOG.debug("Failed to keep alive for current authentication " + user);
				LOG.debug("Set null to security context holder, succeeding filter will take care this user!");
				user.setAuthenticated(false);
				sc.setAuthentication(null);
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if (keepAliveAuthenticationProvider==null)
			throw new NullPointerException("KeepAliveAuthenticationProvider not is null!");
	}
}
