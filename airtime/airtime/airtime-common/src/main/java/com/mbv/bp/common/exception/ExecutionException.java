package com.mbv.bp.common.exception;

public class ExecutionException extends Exception {

	private static final long serialVersionUID = 123969890602385762L;

	public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(String message, Throwable ex) {
        super(message, ex);
    }
}
