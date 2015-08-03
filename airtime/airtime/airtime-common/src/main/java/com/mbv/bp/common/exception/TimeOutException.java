package com.mbv.bp.common.exception;

public class TimeOutException extends Exception {

	private static final long serialVersionUID = 7534370501819101349L;

	public TimeOutException(String message) {
        super(message);
    }

    public TimeOutException(String message, Throwable ex) {
        super(message, ex);
    }
}
