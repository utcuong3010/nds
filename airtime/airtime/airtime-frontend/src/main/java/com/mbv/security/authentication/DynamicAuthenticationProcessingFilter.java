package com.mbv.security.authentication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.regex.Matcher;
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
import org.springframework.security.AuthenticationManager;
import org.springframework.security.InsufficientAuthenticationException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.SpringSecurityFilter;

import com.mbv.helpers.FormatHelper;
import com.mbv.helpers.URLHelper;

public class DynamicAuthenticationProcessingFilter extends SpringSecurityFilter 
	implements InitializingBean {
	static final Log LOG = LogFactory.getLog(XsecdAuthenticationProvider.class);
	
	ArrayList<Hashtable<Class<?>, String>> exceptionMappings;
	ArrayList<Pattern> authenticationUrls;
	List<String> successMappings;

	AuthenticationManager authenticationManager;
	AuthenticationDetailsSource authenticationDetailsSource;
	String usernameParameter="username";
	String passwordParameter="password";
	
	public void setSuccessMappings(List<String> successMappings) {
		LOG.info("Reading success mappings: " + successMappings.size());
		this.successMappings = successMappings; 
	}

	public void setExceptionMappings(List<Properties> mappings) 
	throws ClassNotFoundException  {
		LOG.info("Reading exception mappings: " + mappings.size());
		exceptionMappings = new ArrayList<Hashtable<Class<?>, String>>();
		
		Iterator<Properties> iterator = mappings.iterator();
		while (iterator.hasNext()) {		
			Hashtable<Class<?>, String> expMapping = new Hashtable<Class<?>, String>();
			Properties mapping = iterator.next();
			Iterator<Entry<Object, Object>> entries = mapping.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<Object, Object> entry = entries.next();
				Class<?> klass = Class.forName((String)entry.getKey()); 
				String redirect = (String)entry.getValue();
				LOG.debug(klass.getCanonicalName() + ": " + redirect);
				expMapping.put(klass, redirect);
			}
			exceptionMappings.add(expMapping);
		}
	}

	public void setAuthenticationUrls(List<String> patterns) {
		LOG.info("Reading authentication URLs: " + patterns.size());
		authenticationUrls = new ArrayList<Pattern>();
		Iterator<String> iterator = patterns.iterator();
		while (iterator.hasNext()) {
			authenticationUrls.add(Pattern.compile(iterator.next()));
		}
	}

	public void setAuthenticationManager(AuthenticationManager authenticationmanager) {
		this.authenticationManager = authenticationmanager;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}
		
	@Override
	protected void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) 
	throws IOException, ServletException 
	{
		if (!request.getMethod().equals("POST")) {
			LOG.trace("Ignoring request because not post: " + request.getRequestURI());
			chain.doFilter(request, response);
			return;
		}
		LOG.trace("Checking POST request: " + request.getRequestURI());
		// TODO Auto-generated method stub
		String uri = URLHelper.getRelativeRequestURL(request);
		Iterator<Pattern> iterator = authenticationUrls.iterator();
		while (iterator.hasNext()) {
			Pattern pattern = iterator.next();
			if (LOG.isTraceEnabled())
				LOG.trace("Comparing uri\"" + uri + "\" against pattern " + pattern.pattern());
			Matcher matcher = pattern.matcher(uri);
			if (matcher.matches()) {
				try {	
					LOG.debug("Processing login for uri: " + uri);
					Authentication user = processLogin(request);
					int successIndex = authenticationUrls.indexOf(pattern);
					String redirectURL = request.getParameter("redirect");
					if (redirectURL==null)
						redirectURL = successMappings.get(successIndex);
					else if (redirectURL.startsWith("/"))
						redirectURL = redirectURL.replace(request.getContextPath(), "");
					processLoginSuccess(request, response, user, matcher, redirectURL);
				}
				catch (AuthenticationException ex) {
					LOG.debug("AuthenticationExcepion: " + ex.getMessage(), ex);
					int exMappingIndex = authenticationUrls.indexOf(pattern);
					Hashtable<Class<?>, String> exMapping = exceptionMappings.get(exMappingIndex);
					Iterator<Class<?>> iterClass = exMapping.keySet().iterator();
					while (iterClass.hasNext()) {
						Class<?> klass = iterClass.next();
						if (!klass.isInstance(ex)) continue;
						processLoginFailure(request, response, ex, matcher, exMapping.get(klass));
						break;
					}
				}
				finally {
					LOG.debug("Done processing login for uri: " + uri);
				}	
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}
		
	protected Authentication processLogin(HttpServletRequest request) 
	throws IOException, ServletException {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					request.getParameter(usernameParameter), request.getParameter(passwordParameter)
			);
			token.setDetails(authenticationDetailsSource.buildDetails(request));
			if (token.getName()==null || token.getName().equals(""))
				throw new InsufficientAuthenticationException("Cannot read username from " + usernameParameter);
			if (token.getCredentials()==null || token.getCredentials().equals(""))
				throw new InsufficientAuthenticationException("Cannot read password from " + passwordParameter);
			return authenticationManager.authenticate(token);
		}
		finally {
			
		}
	}
	
	protected void processLoginSuccess(HttpServletRequest request, HttpServletResponse response, 
			Authentication user, Matcher matcher, String redirect)
	throws IOException, ServletException 
	{
		LOG.debug("Authentication success! Updating SecurityConectHolder to contain the following Authentication: " + user);
		SecurityContextImpl sc = new SecurityContextImpl();
		sc.setAuthentication(user);
		SecurityContextHolder.setContext(sc);
		String redirectURL = FormatHelper.formatRegex(matcher, URLHelper.getRelativeRequestURL(request), redirect);
		redirectURL =  URLHelper.formatParamsURL(request, redirectURL);
		redirectURL = URLHelper.getAbsoluteRequestURL(request, response, redirectURL);
		LOG.debug("Redirecting to target URL: " + redirectURL);
		response.sendRedirect(redirectURL);
	}
	
	protected void processLoginFailure(HttpServletRequest request, HttpServletResponse response, 
			AuthenticationException exception, Matcher matcher, String redirect)
	throws IOException, ServletException 
	{
		LOG.debug("Authentication failed for " + exception.getAuthentication());
		String redirectURL = FormatHelper.formatRegex(matcher, URLHelper.getRelativeRequestURL(request), redirect);
		redirectURL = URLHelper.formatParamsURL(request, redirectURL);
		redirectURL = URLHelper.getAbsoluteRequestURL(request, response, redirectURL);
		LOG.debug("Redirecting to failure URL: " + redirectURL);
		response.sendRedirect(redirectURL);
	}
	
	

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		if (authenticationManager == null) 
			builder.append("\nauthenticationManager not set.");
		if (authenticationDetailsSource==null)
			builder.append("\nauthenticationDetailsSource not set.");
		if (authenticationUrls == null || authenticationUrls.size()==0)
			builder.append("\nauthenticationUrls not set.");
		if (exceptionMappings == null || exceptionMappings.size()==0)	
			builder.append("\nexceptionMappings not set.");
		if (successMappings == null || successMappings.size()==0)	
			builder.append("\nsuccessMappings not set.");
		if (builder.length()==0 && 
				(authenticationUrls.size()!=exceptionMappings.size() || 
				authenticationUrls.size()!=successMappings.size()))	
			builder.append("\nauthenticationUrls, exceptionMappings, successMappings must have the same size.");
		if (builder.length()>0)
			throw new Exception("Configuration error: " + builder.toString());
		LOG.trace("Initialization succeed! You should turn off TRACE logging or you will see many messages!!!");
	}
}
