package com.mbv.bp.common.exception;

public class IntegrationException extends Exception {

	private static final long serialVersionUID = 6919097272724880988L;

	public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, Throwable ex) {
        super(message, ex);
    }
}
