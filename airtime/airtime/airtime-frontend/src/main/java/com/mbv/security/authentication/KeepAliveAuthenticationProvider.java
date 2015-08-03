package com.mbv.security.authentication;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;

public interface KeepAliveAuthenticationProvider {
	public void keepAlive(Authentication user) throws AuthenticationException;
}
