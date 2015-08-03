package com.mbv.airline.sabre;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

public class SignInTest {

	@BeforeClass
	public static void setup(){
		
	}
	
//	@Test
//	public void invalidUsername() throws TerminalException{
//		DateTime d1 = new DateTime(2014, 1, 20, 15, 0, 0);
//		DateTime d2 = new DateTime(2014, 1, 29, 13, 0, 0);		
//		System.out.println(Days.daysBetween(d1.toLocalDate(), d2.toLocalDate()).getDays());
//		
//		
//		DateTime d2_ = new DateTime(d2.getYear(), d2.getMonthOfYear(), d2.getDayOfMonth(), 23, 59, 59);
//		
//		DateTime eap5 = d2_.minusDays(5);
//		DateTime eap7 = d2_.minusDays(7);
//		
//		System.out.println(eap5);
//		System.out.println(eap7);		
//		
//		fail("Not yet implemented");
//		Terminal terminal = new Terminal("4428", "mbvsc858");
//		boolean result = terminal.open();
//		
//		Availability cmd = new Availability("han", "sgn", new DateTime(2014, 2, 1, 0, 0));
//		System.out.println(cmd.execute(terminal));
//	
//		cmd = new Availability("han", "sgn", new DateTime(2014, 2, 2, 0, 0));
//		System.out.println(cmd.execute(terminal));
//		terminal.close();
//		assertEquals(false, result);
//	}
	
	@Test
	public void test() {
//		fail("Not yet implemented");
		Date now = new Date();
		DateTime dtNow = new DateTime(now);
		System.out.println(dtNow);
	}

}
