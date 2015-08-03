package com.mbv.security.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.TargetUrlResolver;
import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;

import com.mbv.helpers.FormatHelper;

public class EWalletAuthenticationProcessingFilterEntryPoint extends
		AuthenticationProcessingFilterEntryPoint implements TargetUrlResolver {
	//static final Logger LOG = Logger.getLogger(AuthenticationProcessingFilterEntryPoint.class); 
	static final Log LOG = LogFactory.getLog(XsecdAuthenticationProvider.class);
		
	ArrayList<Pattern> loginFormPatterns = new ArrayList<Pattern>();
	Hashtable<Pattern,String> loginFormUrls = new Hashtable<Pattern,String>();
	
	ArrayList<Pattern> defaultTargetPatterns = new ArrayList<Pattern>();
	Hashtable<Pattern,String> defaultTargetUrls = new Hashtable<Pattern,String>();
	
	public void setLoginFormUrls(String[] value) {
		LOG.info("Updating LoginFormUrls . . .");
		loginFormPatterns.clear();
		loginFormUrls.clear();
		for (int index=0; index<value.length; index++) {
			String[] entry= value[index].split("-->");
			Pattern pattern = Pattern.compile(entry[0]);
			loginFormPatterns.add(pattern);
			loginFormUrls.put(pattern, entry[1]);
		}		
	}
	
	public void setDefaultTargetUrls(String[] value) {
		LOG.info("Updating DefaultTargetUrls . . .");
		defaultTargetPatterns.clear();
		defaultTargetUrls.clear();
		for (int index=0; index<value.length; index++) {
			String[] entry= value[index].split("-->");
			Pattern pattern = Pattern.compile(entry[0]);
			defaultTargetPatterns.add(pattern);
			defaultTargetUrls.put(pattern, entry[1]);
		}
	}
	
	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
	{
		if (loginFormPatterns!=null) {
			String uri = request.getServletPath() 
				+ (request.getQueryString()!=null?"?" + request.getQueryString():"");
				LOG.debug("Determine LogingURL for: " + uri);
			for (int index=0; index<loginFormPatterns.size(); index++) {
				Pattern pattern = loginFormPatterns.get(index);
				Matcher matcher = pattern.matcher(uri);
				if (!matcher.find()) continue;
				LOG.debug("The request matches pattern: " + (index + 1));
				String newURI = FormatHelper.formatRegex(matcher, uri, loginFormUrls.get(pattern));
				if (newURI.contains("$URI")) {
					try {
						newURI = newURI.replace("$URI", URLEncoder.encode(
								request.getContextPath() + response.encodeURL(uri), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				LOG.debug("Resolved URL for Login: " + newURI);
				return newURI;
			}
		}
		return super.determineUrlToUseForThisRequest(request, response, exception);
	}

	@Override
	public String determineTargetUrl(SavedRequest savedRequest, HttpServletRequest request, Authentication token) {
		// TODO Auto-generated method stub
		if (request.getParameter("redirect")!=null) {
			return request.getParameter("redirect");
		}
		else if (defaultTargetPatterns!=null && savedRequest!=null) {
			String uri = savedRequest.getServletPath() 
				+ (request.getQueryString()!=null?"?" + request.getQueryString():"");
			for (int index=0; index<defaultTargetPatterns.size(); index++) {
				Pattern pattern = defaultTargetPatterns.get(index);
				Matcher matcher = pattern.matcher(uri);
				if (!matcher.find()) continue;
				int nMatchCount = matcher.groupCount();
				int nMatch = 0;
				String newURI = defaultTargetUrls.get(pattern);
				while (++nMatch<nMatchCount) {
					if (newURI.contains("$" + nMatch)) {
						newURI = newURI.replace("$" + nMatch, uri.substring(matcher.start(nMatch), matcher.end(nMatch)));
					}
				} 
				return newURI; 
			}
		}
		return "/";
	}
}
