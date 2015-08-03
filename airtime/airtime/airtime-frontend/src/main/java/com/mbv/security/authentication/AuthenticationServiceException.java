package com.mbv.security.authentication;

import org.springframework.security.AuthenticationException;

public class AuthenticationServiceException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3688713502050700234L;

	public AuthenticationServiceException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public AuthenticationServiceException(String msg, Object o) {
		super(msg, o);
		// TODO Auto-generated constructor stub
	}
	
	public AuthenticationServiceException(String msg, Throwable e) {
		super(msg, e);
		// TODO Auto-generated constructor stub
	}
}
