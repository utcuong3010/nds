package xpay;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.xml.sax.SAXException;

import com.mbv.airtime2.xpay.XpayConfig;
import com.mbv.airtime2.xpay.actor.XpayActor;
import com.mbv.airtime2.xpay.domain.Response;

public class TestWebsericeXpay {

	public static void main(String[] args) throws ServiceException,
			SAXException, IOException, ParserConfigurationException,
			JAXBException, InterruptedException {
//		System.out.println("enum " + Response.State.UNKNOWN.getValue());
//		System.out.println("session " + XpayMaster.sessionId);
//		new Thread(new Runnable() {
//			public void run() {
//				XpayMaster.sessionId = "3213";
//				System.out.println("session in run " + XpayMaster.sessionId);
//			}
//		}).start();
//		
//		Thread.sleep(1000l);
//		System.out.println("session out run" + XpayMaster.sessionId);

		XpayActor xpay = new XpayActor(new XpayConfig(), null);
		System.out.println("XPAY Process Examle !");
		String st, result, sessionid;
		Response response;
		System.out.println("enum" + Response.State.SUCCESS.name());

		// st = xpay.About();
		// System.out.println(st);

		// st = xpay.Echo();
		// System.out.println(st);

		// Login
		result = xpay.login();
		// System.out.println("Response : " + result);
		response = new Response(result);
		sessionid = response.sessionid;
		System.out.println("state=" + response.state + "; message="
				+ response.message + "; sessionid=" + sessionid);

		// Prepaid
		/*
		 * result = xpay.Prepaid(sessionid, "VTEL0010", 2);
		 * System.out.println("Response : " + result); response = new
		 * Response(result); System.out.println("state=" + response.state +
		 * "; message=" + response.message + "; trace=" + response.tracenumber);
		 * if (response.state.equals("0")) { if (response._products != null) {
		 * for (int i=0; i<response._products.product.size(); i++) { Product pp
		 * = response._products.product.get(i); System.out.println("Prepaid[" +
		 * i + "] serial=" + pp.serial + "; code=" + pp.code + "; expdate=" +
		 * pp.expdate); } } }
		 */

		// Topup
//		result = xpay.callTopup(sessionid, "", "MOBI0010", "0902421990", 10000);
//		// System.out.println("Response : " + result);
//		// response = new Response(result);
//		System.out.println("state=" + response.state + "; message="
//				+ response.message + "; trace=" + response.tracenumber);

		result = xpay.callBalance("");
		System.out.println("Response : " + result);
		// System.out.println("state=" + response.state + "; message=" +
		// response.message + "; balance=" + response.balance);
		// Pospaid
		// result = xpay.Postpaid(sessionid, "VTEL", "0913567999", 10000);
		// System.out.println("Response : " + result);
		// response = new Response(result);
		// System.out.println("state=" + response.state + "; message=" +
		// response.message + "; trace=" + response.tracenumber);

		// Logout
		result = xpay.logout(sessionid);
		// System.out.println("Response : " + result);
		// response = new Response(result);
		System.out.println("state=" + response.state + "; message="
				+ response.message);
	}

}
