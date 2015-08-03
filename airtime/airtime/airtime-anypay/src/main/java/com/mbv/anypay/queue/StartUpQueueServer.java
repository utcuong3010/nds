package com.mbv.anypay.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.mbv.bp.common.executor.ExecutorFactory;


public class StartUpQueueServer {
	private static Logger log=Logger.getLogger(StartUpQueueServer.class);
	public static void startUp() {
		
		AbstractApplicationContext contextLoader = new ClassPathXmlApplicationContext(
				new String[] {"common-context.xml", "anypay-context.xml" });
		contextLoader.start();
		try {
			ExecutorFactory.getInstance().initExecutor(
					ExecutorFactory.ANYPAY_EXECUTOR);
		} catch (Exception e) {
			log.error("Error in startUp core server");
		}

	}
	public static void shutdown() {
		AbstractApplicationContext contextLoader = new ClassPathXmlApplicationContext(
				new String[] { "common-context.xml","anypay-context.xml" });
		contextLoader.stop();
	}
public static void main(String[] args) {
	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	StartUpQueueServer.startUp();
	System.out.println("Queue server is started.");
	try { 
		String s="";
		while (!s.equalsIgnoreCase("bye")){
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		s = br.readLine();
		
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	StartUpQueueServer.shutdown();
}
}
