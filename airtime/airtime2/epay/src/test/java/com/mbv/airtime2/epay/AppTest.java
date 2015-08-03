package com.mbv.airtime2.epay;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AppTest {
    public static void main(String[] args){
    	System.out.println("Sin Sun");
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("TestContext.xml");

		EpayService epayService = (EpayService) context.getBean("EpayService");
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		final Scanner scanner = new Scanner(System.in);
//		while (true) {
//			final String input = scanner.nextLine();
//			if("q".equals(input.trim())) break;
//		}
//		scanner.close();
//		
//		context.close();
//		System.out.println("MDoneq");
    }
}
