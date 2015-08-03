package com.mbv.bp.common.exception;

public class ReconnectException extends Exception {
	
	private static final long serialVersionUID = -7111880444459192788L;

	public ReconnectException(String message) {
        super(message);
    }

    public ReconnectException(String message, Throwable ex) {
        super(message, ex);
    }
}
