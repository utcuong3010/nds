package com.mbv.bp.common.exception;

public class ResponseException extends Exception {

	private static final long serialVersionUID = 8912057824309008652L;

	public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String message, Throwable ex) {
        super(message, ex);
    }
}
