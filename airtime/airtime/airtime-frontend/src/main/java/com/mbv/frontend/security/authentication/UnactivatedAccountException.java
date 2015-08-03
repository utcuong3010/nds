package com.mbv.frontend.security.authentication;

import org.springframework.security.AuthenticationException;

public class UnactivatedAccountException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 282987273398971186L;

	public UnactivatedAccountException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public UnactivatedAccountException(String msg, Object o) {
		super(msg, o);
		// TODO Auto-generated constructor stub
	}
	
	public UnactivatedAccountException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}

}
