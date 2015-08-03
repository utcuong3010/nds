package com.mbv.airline.common.info;

public class CheckSession {
	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public CheckSession(){
		
	}
	
	public CheckSession(String command){
		this.command = command;
	}

	@Override
	public String toString() {
		return "AirRefreshPage [command=" + command + "]";
	}
}
