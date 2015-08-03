package com.mbv.airline.actors;

public class WorkerAvailableMessage {
    private String vendor;

    public WorkerAvailableMessage(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }
}
