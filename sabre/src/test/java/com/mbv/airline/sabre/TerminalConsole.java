package com.mbv.airline.sabre;

import java.io.BufferedInputStream;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.joda.time.DateTime;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.Availability;
import com.mbv.airline.sabre.commands.DesignatePrinters;
import com.mbv.airline.sabre.commands.IssueTickets;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.Pricing;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.RetrievePNR;
import com.mbv.airline.sabre.commands.SignIn;
import com.mbv.airline.sabre.commands.SignOut;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.utils.PricingClassUtil;
import com.sabre.gds.GDSDecoder;
import com.sabre.gds.GDSElemValue;
import com.sabre.gds.GDSError;
import com.sabre.gds.GDSSegValue;

public class TerminalConsole {
	private static SenderManager senderManager;
	public static void main(String[] args) {
		
		LinkedHashMap<String, String> pricingClassMap = new LinkedHashMap<String, String>();
		pricingClassMap.put("JH", "Group1");
		pricingClassMap.put("MH", "Group2");
		pricingClassMap.put("J|J[^H]+.*|JH.+|M|M[^H]+.*|MH.+|C.*", "Group3");
		pricingClassMap.put("K.*|L.*|Q.*|N.*|R.*", "Group4");
		pricingClassMap.put("E.*|P.*|A.*", "Group5");
		
		PricingClassUtil pcUtil = new PricingClassUtil();
		pcUtil.setPricingClassMap(pricingClassMap);
		
		
		System.out.println("Welcome to Sabre Terminal Console :D");
		
		Scanner scanner = new Scanner(new BufferedInputStream(System.in));

		TerminalConfig config = new TerminalConfig();
		config.setUsername("4428");
		config.setPassword("mbvsc858");
		config.setAirlineCode("apk");
		config.setPseudoCityCode("opk");
		config.setHardcopyPrinter("4CB4B6");
		config.setTicketPrinter("4CB4B7");
		config.setPrintRoutine("GF");
		config.setStationNumber("37982254");
		Terminal terminal = new Terminal(config);

		if (terminal.open() == false) {
			System.out.println("Connection Error");
			return;
		}

		Result result = (new SignIn(senderManager)).execute(terminal);
		if (result.getStatus() != Code.SUCCESS) {
			System.out.println("Sign-in Error");
			return;
		}
		
		
//		result = (new DesignatePrinters()).execute(terminal);
//		if (result.getStatus() != Code.SUCCESS) {
//			System.out.println("Printer Error");
//			return;
//		}
		// Command cmd = new RetrievePNR("EXNUWV");
		// Command cmd = new RetrievePNR("HKSGZE");
		// System.out.println(cmd.execute(terminal));

		// cmd = new RetrievePNR();
		// cmd.execute(terminal);
		String str;
		do {
			System.out.print(">");
			str = scanner.nextLine();
			if ("exit".equalsIgnoreCase(str)) {
				break;
			} 
			else if ("printer".equals(str)) {
				result = (new DesignatePrinters()).execute(terminal);
				System.out.println(result.getStatus());
			}
			else if("pnr".equals(str)){
				PNR pnr = (PNR) new RetrievePNR().execute(terminal);
				System.out.println(pnr);
			}else if("tkt".equals(str)){
				PNR pnr = (PNR) new IssueTickets("OZFCMU").execute(terminal);
				System.out.println(pnr);
			}else if ("av".equals(str)){
                DateTime date = DateTime.now();
                AirFareInfo fareInfo = new AirFareInfo();
                fareInfo.setVendor("VN");
                fareInfo.setOriginCode("SGN");
                fareInfo.setDestinationCode("HAN");
                fareInfo.setDepartureDate(date.toDate());
//				System.out.println(new Availability(fareInfo).execute(terminal));
			}else if("wp".equals(str)){
				System.out.println(new Pricing(null, null).execute(terminal));
				//System.out.println(new Pricing(null, pcUtil).execute(terminal));
			}else if ("".equals(str)) {
				try {
					str = terminal.receive();
					GDSDecoder decoder = new GDSDecoder();
					int code = decoder.allocMsg(str);
					if (code != GDSError.GDS_OK) {
						System.out.println(str);
					} else {
						GDSSegValue gdsSeg;
						GDSElemValue gdsElem;

						while ((gdsSeg = decoder.getNextSeg()) != null) {
							System.out.println(gdsSeg.id);
							while ((gdsElem = decoder.getNextElem()) != null) {
								System.out.println("\t" + gdsElem.id + "\t"
										+ gdsElem.value);
							}
						}

					}

				} catch (TerminalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					terminal.send(str);
				} catch (TerminalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (true);

		new SignOut().execute(terminal);
		terminal.close();
	}
}
