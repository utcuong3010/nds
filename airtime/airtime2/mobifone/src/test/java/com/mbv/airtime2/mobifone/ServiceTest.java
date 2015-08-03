package com.mbv.airtime2.mobifone;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mbv.airtime2.integration.IntegrationPayload;

public class ServiceTest {

	public static void main(String[] args){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("TestContext.xml");

		MobifoneService mobiService = (MobifoneService) context.getBean("MobifoneService");
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IntegrationPayload payload = new IntegrationPayload();
		payload.put("dest_wfp", "wfpMobiFoneBalanceRequestAfterQueue");
		
		for(int i = 0; i < 10; i++){
			try {
				mobiService.process(payload);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//transaction_id=201210150000257283, msisdn=0913928786, amount=5000,
		
		payload = new IntegrationPayload();
		payload.put("dest_wfp", "wfpMobiFoneTopupRequestAfterQueue");
		payload.put("transaction_id", "11111");
		payload.put("msisdn", "0973918228");
		payload.put("amount", "10000");
		try {
			mobiService.process(payload);
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
