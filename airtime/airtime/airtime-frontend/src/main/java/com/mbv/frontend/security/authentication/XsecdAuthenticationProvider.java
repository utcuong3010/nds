package com.mbv.frontend.security.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; //import org.apache.log4j.Logger;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.userdetails.UserDetails;

//import com.mbv.helpers.FormatHelper;
//import com.mbv.security.EWalletUserDetails;
import com.mbv.frontend.security.AtUserDetails;
import com.mbv.services.SOAP11Exception;
import com.mbv.services.ServerBusyException;
import com.mbv.services.ServicePool;
import com.mbv.services.UnavailabilityException;
import com.mbv.services.xsecd.XsecdService;
import com.mbv.services.xsecd.SessionInfo;
import com.mbv.services.xsecd.UserInfo;
import com.mbv.services.xsecd.UserRequestInfo;

public class XsecdAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider implements
		KeepAliveAuthenticationProvider {
	// static final Logger LOG =
	// Logger.getLogger(XsecdAuthenticationProvider.class);
	static final Log LOG = LogFactory.getLog(XsecdAuthenticationProvider.class);

	static final Pattern PASSWORD_PATTERN_SHA1HEX = Pattern
			.compile("[0-9a-fA-F]{40}");

	String rolePrefix = "ROLE_";
	ServicePool<XsecdService> xsecdPool;
	Map<Pattern, String> domainPatterns;

	public void setXsecdPool(ServicePool<XsecdService> pool) {
		this.xsecdPool = pool;
	}

	public void setDomainPatterns(Map<String, String> patterns) {
		domainPatterns = new Hashtable<Pattern, String>();
		Iterator<String> keys = patterns.keySet().iterator();
		while (keys.hasNext()) {
			String pattern = keys.next();
			Pattern key = Pattern.compile(pattern);
			domainPatterns.put(key, patterns.get(pattern));
		}
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	/**
	 * resolve user domain via authentication URI
	 * 
	 * @param uri
	 * @return
	 */
	String resolveAuthenticationDomain(String uri) {
		// LOG.debug("Resolving login domain for uri: " + uri);
		// if (domainPatterns!=null) {
		// Iterator<Pattern> keys = domainPatterns.keySet().iterator();
		// while(keys.hasNext()) {
		// Pattern pattern = keys.next();
		// if (LOG.isTraceEnabled())
		// LOG.trace("Comparing uri with " + pattern.pattern());
		// Matcher matcher = pattern.matcher(uri);
		// if (matcher.matches()) {
		// String domain = FormatHelper.formatRegex(matcher, uri,
		// domainPatterns.get(pattern));
		// LOG.debug("Resolved domain: " + domain);
		// return domain;
		// }
		// } // while
		// } // if
		LOG.debug("Cannot resolve any domain, treating as end-user login.");
		return "mobivi";
	}

	/**
	 * resolve granted authorities via UserInfo
	 * 
	 * @param info
	 * @return
	 */
	GrantedAuthority[] resolveAuthorities(UserInfo info) {
		String[] groups = info.getGroups();
		if (groups == null)
			groups = new String[0];
		GrantedAuthority[] value = new GrantedAuthority[groups.length + 2];
		value[0] = new GrantedAuthorityImpl(rolePrefix + "ANONYMOUS");
		if (info.getUserType().equals(UserInfo.TYPE_ENDUSER)) {
			value[1] = new GrantedAuthorityImpl(rolePrefix + "END-USER");
		} else {
			value[1] = new GrantedAuthorityImpl(rolePrefix
					+ (info.getDomain().equals("mobivi") ? "MOBIVI" : "BANK")
					+ "-USER");
		}
		for (int index = 0; index < groups.length; index++)
			value[index + 2] = new GrantedAuthorityImpl(rolePrefix
					+ groups[index].toUpperCase());
		return value;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails details,
			UsernamePasswordAuthenticationToken token)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		XsecdService theXsecd = acquireXsecdService();
		try {
			AtUserDetails userDetails = (AtUserDetails) details;
			theXsecd.setSession(userDetails.getSessionInfo());
			LOG.debug("Calling xsecd keep alive for user " + details);
			theXsecd.keepAlive();
		} catch (SOAP11Exception e) {
			if (e.getFaultString() != null
					&& e.getFaultString().equals("NOT_AUTHENTICATED")) {
				throw new ExpiredSessionException(
						"Session is no longer valid.", e);
			} else {
				throw new AuthenticationServiceException(
						"Unknown SOAP11 exception.", e);
			}
		} catch (Throwable ex) {
			throw new AuthenticationServiceException("Unknown exception.", ex);
		} finally {
			releaseXsecdService(theXsecd);
		}
	}

	XsecdService acquireXsecdService() throws AuthenticationException {
		try {
			return xsecdPool.acquire();
		} catch (Exception ex) {
			throw new AuthenticationServiceException(
					"Cannot acquire xsecd connection", ex);
		}
	}

	void releaseXsecdService(XsecdService xsecd) {
		xsecd.setSession(null);
		xsecdPool.release(xsecd);
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken token)
			throws AuthenticationException {

		// TODO Auto-generated method stub
		String domain = null;
		String userType = "end-user";
		String password = token.getCredentials().toString();
		String passwordType = null;
		if (PASSWORD_PATTERN_SHA1HEX.matcher(password).matches()) {
			passwordType = "SHA1HEX";
			password = password.toLowerCase();
		} else {
			passwordType = "plain text";
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(password.getBytes());
				password = new String(Hex.encode(md.digest()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				LOG.fatal("Failed to instanctiate SHA1", e);
				throw new AuthenticationServiceException(
						"Failed to instantiate SHA1", e);
			}
		}
		UserRequestInfo requestInfo;
		if (token.getDetails() != null
				&& token.getDetails() instanceof UserRequestInfo) {
			requestInfo = (UserRequestInfo) token.getDetails();
			domain = resolveAuthenticationDomain(requestInfo.getRequestURI());
			if (domain != null)
				userType = "system";
		} else {
			requestInfo = new UserRequestInfo();
		}
		XsecdService theXsecd = acquireXsecdService();
		try {
			LOG.debug("Login " + username + "@" + domain + "(" + userType
					+ ") using " + passwordType + " password.");
			SessionInfo info = theXsecd.login(domain, userType, username,
					password, requestInfo);
			info.setPassword(password);
			LOG.debug("Login " + username + " successfully.");
			info.setUserInfo(theXsecd.getAccountInfo());
			info.setPrivileges(theXsecd.getSessionPrivileges());
			AtUserDetails details = new AtUserDetails(info,
					resolveAuthorities(info.getUserInfo()));
			return details;
		} catch (SOAP11Exception e) {
			LOG.debug("Login failed for " + username + ": "
					+ e.getFaultString());
			if (e.getFaultString() != null
					&& e.getFaultString().equals("NOT_AUTHENTICATED")) {
				throw new BadCredentialsException(
						"Username and password not found.", e);
			} else if (e.getFaultString() != null
					&& e.getFaultString().equals("NOT_ACTIVATED")) {
				throw new UnactivatedAccountException(
						"Account is not activated", e);
			} else if (e.getFaultString() != null
					&& e.getFaultString().equals("ACCOUNT_NOT_ACTIVATED")) {
				throw new AccountNotActivatedException(
						"Account is not activated", e);
			} else {
				throw new AuthenticationServiceException(
						"Unknown SOAP11 exception.", e);
			}
		} catch (UnavailabilityException e) {
			LOG.fatal("Unable to connect Xsecd.", e);
			throw new AuthenticationServiceException(
					"Unable to connect Xsecd.", e);
		} catch (ServerBusyException e) {
			LOG.fatal("Cannot receive response from Xsecd.", e);
			throw new AuthenticationServiceException(
					"Cannot receive response from Xsecd.", e);
		} catch (Throwable e) {
			LOG.error("Unknown exception.", e);
			// TODO Auto-generated catch block
			throw new AuthenticationServiceException("Unknown exception.", e);
		} finally {
			releaseXsecdService(theXsecd);
		}
		// return null;
	}

	@Override
	public void keepAlive(Authentication user) throws AuthenticationException {
		// TODO Auto-generated method stub
		Object details = user.getPrincipal();
		if (details instanceof UserDetails == false) {
			LOG.debug("User is not E-Wallet User, ignored!");
			return;
		}
		UserDetails detail = (UserDetails) details;
		LOG.debug("Keep alive for authentication " + user);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				detail.getUsername(), detail.getPassword());
		additionalAuthenticationChecks(detail, token);
	}

}
