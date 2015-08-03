package com.mbv.airtime2.mobifone.domain;

public abstract class Response {
	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String STATUS_ERROR = "ERROR";
	
	protected final String status;
	protected final String errorCode;

	public Response(String status, String errorCode){
		this.status = status;
		this.errorCode = errorCode;
	}
	
	public String getStatus() {
		return status;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public boolean isSuccess(){
		return STATUS_SUCCESS.equals(this.status);
	}
}
