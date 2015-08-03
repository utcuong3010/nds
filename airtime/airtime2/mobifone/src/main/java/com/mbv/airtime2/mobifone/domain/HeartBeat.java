package com.mbv.airtime2.mobifone.domain;

import com.google.common.base.Objects;

public class HeartBeat {
	public static enum Status{
		READY,
		ERROR
	}
	
	private final Status status;
	
	public HeartBeat(Status status){
		this.status = status;
	}
		
	public String toString(){
		return Objects.toStringHelper(this).add("status", status).toString();
	}

	public Status getStatus() {
		return status;
	}
}
