package com.mbv.bp.common.dao;

public class DaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3423419481598875397L;


	public DaoException(String message) {
        super(message);
    }

    
    public DaoException(String message, Throwable ex) {
        super(message, ex);
    }
}

