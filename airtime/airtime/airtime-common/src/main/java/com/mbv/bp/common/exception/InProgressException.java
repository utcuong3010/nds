package com.mbv.bp.common.exception;

public class InProgressException extends Exception {

	private static final long serialVersionUID = -6630163930516119292L;

	public InProgressException(String message) {
        super(message);
    }

    public InProgressException(String message, Throwable ex) {
        super(message, ex);
    }
}
