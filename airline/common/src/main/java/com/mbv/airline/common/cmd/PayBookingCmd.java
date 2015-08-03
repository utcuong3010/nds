package com.mbv.airline.common.cmd;


/**
 * 
 * @author cuongtv
 *
 */
public class PayBookingCmd{
    private String id;

    public PayBookingCmd() {
    	
    }

    public PayBookingCmd(String Id) {
        this.id = Id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
