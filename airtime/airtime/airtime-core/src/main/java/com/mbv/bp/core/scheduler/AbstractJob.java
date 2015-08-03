/*
 * Decompiled with CFR 0_101.
 */
package com.mbv.bp.core.scheduler;

public abstract class AbstractJob {
    protected OPERARION_TYPE operationType;
    private boolean enable;

    public abstract void execute();

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public static enum OPERARION_TYPE {
        INSERT,
        UPDATE_BY_AT_TXN,
        UPDATE_BY_MSG_ID,
        NO_ACTION;
        

        private OPERARION_TYPE() {
        }
    }

}

