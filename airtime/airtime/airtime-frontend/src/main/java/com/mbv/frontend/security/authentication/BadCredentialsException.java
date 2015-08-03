package com.mbv.frontend.security.authentication;

import org.springframework.security.AuthenticationException;

public class BadCredentialsException extends AuthenticationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3769954559611231749L;

	public BadCredentialsException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public BadCredentialsException(String msg, Object o) {
		super(msg, o);
		// TODO Auto-generated constructor stub
	}
	
	public BadCredentialsException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}

}
