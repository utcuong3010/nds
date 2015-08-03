package com.mbv.bp.common.exception;

public class ShuttingDownException extends Exception {

	private static final long serialVersionUID = -4765571580045435026L;

	public ShuttingDownException(String message) {
        super(message);
    }

    public ShuttingDownException(String message, Throwable ex) {
        super(message, ex);
    }
}
