package com.mbv.bp.common.exception;

public class RequestException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5691644387389675676L;

	public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable ex) {
        super(message, ex);
    }
}
