package com.mbv.airtime2.vngmobile;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mbv.airtime2.integration.IntegrationPayload;


/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("TestContext.xml");
		Service service = (Service) context.getBean("VietnamMobileService");
		IntegrationPayload payload = new IntegrationPayload();
		payload.put("transaction_id", "111112");
		payload.put("msisdn", "01993992995");
		payload.put("amount", "1000");
		try {
			for(int i = 0; i < 10; i++){
				service.process(payload);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		final Scanner scanner = new Scanner(System.in);
		while (true) {
			final String input = scanner.nextLine();
			if("q".equals(input.trim())) break;
		}		
		context.close();
	}
}
