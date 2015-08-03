package com.mbv.airline.sabre;

/**
 * Created by phuongvt on 12/11/14.
 */
public class SabreServiceException extends Exception {
    private String label = "SABRE ERROR";

    public SabreServiceException() {
        // TODO Auto-generated constructor stub
    }

    public SabreServiceException(String message) {
        super(message);
    }

    public SabreServiceException(Exception exp) {
        super(exp);
    }

    public SabreServiceException(String label, Exception e) {
        super(label, e);
        this.label = label;
    }

    public SabreServiceException(String message, String ex) {
        super(message, new Throwable(ex));
    }

    public String getMessage() {
        return label + ":     " + getCause().toString();
    }
}
