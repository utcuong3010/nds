/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.config.SpringContextLoader
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.generator.IdGenerator
 *  com.mbv.bp.common.generator.IdGeneratorFactory
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core;

import com.mbv.bp.common.config.SpringContextLoader;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.generator.IdGenerator;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StartUpScheduler {
    private static Log log = LogFactory.getLog((Class)StartUpScheduler.class);
    private static final String module = "Scheduler";

    public static void startUp() {
        SpringContextLoader contextLoader = new SpringContextLoader(new String[]{"common-context.xml", "scheduler-context.xml"});
        SpringContextLoader.start();
        try {
            ExecutorFactory.getInstance().initExecutor("Scheduler");
            log.error("Scheduler server is started.");
        }
        catch (Exception e) {
            log.error("Error in startUp Scheduler server");
        }
    }

    public static void shutdown() {
        ExecutorFactory.getInstance().destroyExecutor("Scheduler");
        try {
            IdGeneratorFactory.getInstance().getVnPayIdGenentor().shutdownSeq();
        }
        catch (Exception e) {
            // empty catch block
        }
        SpringContextLoader.destroy();
    }
}

