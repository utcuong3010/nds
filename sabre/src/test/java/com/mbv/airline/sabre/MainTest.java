package com.mbv.airline.sabre;

import java.io.BufferedInputStream;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
	public static void main(String args[]) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
		
		Scanner scanner = new Scanner(new BufferedInputStream(System.in));
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();	
			
			if("exit".equalsIgnoreCase(line)) break;
		}
		ctx.close();
	}
}
