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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StartUpWorkflowCore {
    private static Log log = LogFactory.getLog((Class)StartUpWorkflowCore.class);

    public static void startUp() {
        SpringContextLoader contextLoader = new SpringContextLoader(new String[]{"common-context.xml", "workflow-context.xml"});
        SpringContextLoader.start();
        try {
            ExecutorFactory.getInstance().initExecutor("WorkFlowConnection");
        }
        catch (Exception e) {
            log.error("Error in startUp core server");
        }
    }

    public static void shutdown() {
        ExecutorFactory.getInstance().destroyExecutor("WorkFlowConnection");
        try {
            IdGeneratorFactory.getInstance().getVnPayIdGenentor().shutdownSeq();
        }
        catch (Exception e) {
            // empty catch block
        }
        SpringContextLoader.destroy();
    }

    public static void main(String[] args) throws Exception {
        StartUpWorkflowCore.startUp();
        System.out.println("Workflow server is started.");
        try {
            String s = "";
            while (!s.equalsIgnoreCase("bye")) {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                s = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        StartUpWorkflowCore.shutdown();
    }
}

