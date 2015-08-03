package com.mbv.airline.sabre.commands.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Result {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public static enum Code {
		SUCCESS, ERROR, TERMINAL_ERROR, UNKNOWN
	}

	private String description;
	private Code status;

	public Result() {
		this.status = Code.SUCCESS;
	}

	public Result(Code status) {
		this.status = status;
	}

	public Result(Code status, String des) {
		this.status = status;
		this.description = des;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Code getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
