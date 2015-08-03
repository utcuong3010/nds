package com.mbv.airline.common.info;

/**
 * Created by phuongvt on 2/25/15.
 */
public class ItineraryUpdateCommand {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

	@Override
	public String toString() {
		return "ItineraryUpdateCommand [command=" + command + "]";
	}
    
    
}
