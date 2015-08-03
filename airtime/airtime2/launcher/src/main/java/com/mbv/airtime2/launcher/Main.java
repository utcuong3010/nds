package com.mbv.airtime2.launcher;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.Logger;
/**
 * Hello world!
 *
 */
public class Main
{
	private static final Logger LOGGER = Logger.getLogger(Main.class);
    
	private ClassPathXmlApplicationContext context;
	
	public void init(String[] args) {
		System.out.println("start");
		context = new ClassPathXmlApplicationContext("AppContext.xml");
        LOGGER.info( "Hello World!" );
    }
	
	public void start() {
        context.start();
    }
	
	public void stop() {
		context.stop();
	}
	
	public void destroy(){
		context.close();
	}
	
	public static void main(String[] args){
		Main clazz = new Main();
		clazz.init(null);
	}
}
