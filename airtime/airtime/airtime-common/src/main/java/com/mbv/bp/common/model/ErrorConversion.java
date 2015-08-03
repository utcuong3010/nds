package com.mbv.bp.common.model;

public class ErrorConversion {
	String orgError;
	String orgMessage;
	String destError;
	public String getOrgError() {
		return orgError;
	}
	public void setOrgError(String orgError) {
		this.orgError = orgError;
	}
	public String getOrgMessage() {
		return orgMessage;
	}
	public void setOrgMessage(String orgMessage) {
		this.orgMessage = orgMessage;
	}
	public String getDestError() {
		return destError;
	}
	public void setDestError(String destError) {
		this.destError = destError;
	}
	@Override
	public String toString() {
		return "ErrorConvertion [destError=" + destError + ", orgError="
				+ orgError + ", orgMessage=" + orgMessage + "]";
	}
	
}
