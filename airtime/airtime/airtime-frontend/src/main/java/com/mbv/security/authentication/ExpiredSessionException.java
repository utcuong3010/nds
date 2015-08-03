package com.mbv.security.authentication;

import org.springframework.security.AuthenticationException;

public class ExpiredSessionException extends AuthenticationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -833518968786402305L;

	public ExpiredSessionException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public ExpiredSessionException(String msg, Object o) {
		super(msg, o);
		// TODO Auto-generated constructor stub
	}
	
	public ExpiredSessionException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}
}
