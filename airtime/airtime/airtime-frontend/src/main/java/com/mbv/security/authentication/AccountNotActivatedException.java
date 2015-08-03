package com.mbv.security.authentication;

import org.springframework.security.AuthenticationException;

public class AccountNotActivatedException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 282987273398971186L;

	public AccountNotActivatedException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public AccountNotActivatedException(String msg, Object o) {
		super(msg, o);
		// TODO Auto-generated constructor stub
	}
	
	public AccountNotActivatedException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}

}
