/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.scheduler;

import com.mbv.bp.core.scheduler.AbstractJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobifoneCdrGeneratorJob
extends AbstractJob {
    private Log log = LogFactory.getLog((Class)MobifoneCdrGeneratorJob.class);
    private static final String MODULE = "cdrsync";
    private static final String TYPE = "cacher";
    private static final String KEY = "mobifone.last-export-date";

    @Override
    public void execute() {
        if (!this.isEnable()) {
            this.log.info(("no process for this job | enable-" + this.isEnable()));
            return;
        }
    }
}

