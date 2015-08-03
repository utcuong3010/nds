package com.mbv.airline.util;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupExecute {

	/**
	 * logger
	 */
	final static Logger logger = Logger.getLogger(JsoupExecute.class);
	
	/**
	 * timeout of session
	 */
	private static final int TIMEOUT = 9000000;

	/**
	 * do: get url have not session
	 * @param url
	 * @throws Exception
	 */
	public static Response doGet(String url) throws Exception {
		try {
			return Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36")
					.method(Method.GET).timeout(TIMEOUT).execute();
		} catch (Exception ex) {
			logger.error("CAN NOT GET [URL] = " + url);
			throw ex;
		}	
	}
	
	/**
	 * do: get url have session
	 * @param url
	 * @param session
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Response doGet(String url,String session) throws Exception {
		try {
			return Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36")
					.cookie("ASP.NET_SessionId", session).method(Method.GET)
					.timeout(TIMEOUT).execute();
		} catch (Exception ex) {
			logger.error("CAN NOT GET [URL] = " + url);
			throw ex;
		}
	}
	
	/**
	 * do: get view state
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String getViewState(Response response) throws Exception {
		try {
			Document document = Jsoup.parse(response.body());
			return document.select("input[name=viewState]").attr("value");
		} catch (Exception ex) {
			logger.error("CAN NOT GET VIEWSTATE: " + ex);
			throw ex;
		}	
	}
	
	/**
	 * do: get session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String getSession(Response response) throws Exception {
		try {
			return response.cookies().get("ASP.NET_SessionId");
		} catch (Exception ex) {
			logger.error("CAN NOT GET SESSION: " + ex);
			throw ex;
		}
	}
	
	/**
	 * do: post form data
	 * @param url
	 * @param params
	 * @param session
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Response doPost(String url, HashMap<String, String> params, String session) throws Exception {
		try {
			return Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36")
					.data(params).cookie("ASP.NET_SessionId", session).method(Method.POST)
					.timeout(TIMEOUT).execute();
		} catch (Exception ex) {
			logger.error("CAN NOT POST DATA TO [URL] = " + url + " [PARAMS] = " + params);
			throw ex;
		}
	}
}
