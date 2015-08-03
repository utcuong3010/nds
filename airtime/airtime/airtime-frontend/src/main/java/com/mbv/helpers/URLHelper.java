package com.mbv.helpers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLHelper {
	
	/**
	 * format url with query string 
	 * Ex: someuri?param=${param} ==> someuri?param=paramValue
	 * @param request
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String formatParamsURL(HttpServletRequest request, String url) throws UnsupportedEncodingException {
		StringBuilder buffer = new StringBuilder();
		int nLastIndex = 0;
		while (nLastIndex<url.length()) {
			int nCurrentIndex = url.indexOf("${", nLastIndex);
			if (nCurrentIndex>=0) {
				buffer.append(url.substring(nLastIndex, nCurrentIndex));
				int nNextIndex = url.indexOf("}", nCurrentIndex);
				if (nNextIndex>0) {
					String sValue = request.getParameter(url.substring(nCurrentIndex+2, nNextIndex));
					if (sValue!=null)
						buffer.append(URLEncoder.encode(sValue, "UTF-8"));// should encode param
					nLastIndex = nNextIndex+1;
				}
				else {
					buffer.append("${");
					nLastIndex = nCurrentIndex+2;
				}
			}
			else {
				buffer.append(url.substring(nLastIndex, url.length()));
				nLastIndex = url.length();
			}
		}
		return buffer.toString();
	}
	
	/**
	 * Insert context path at the beginning of the URL
	 * Encode URL the return value for redirection
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 */
	public static String getAbsoluteRequestURL(HttpServletRequest request, HttpServletResponse response, String url) {
		return response.encodeRedirectURL(request.getContextPath() + url);
	}
	
	/**
	 * Trim the context path from requestURI
	 * Append query string to return value
	 * @param request
	 * @return
	 */
	public static String getRelativeRequestURL(HttpServletRequest request) {
		String uri = request.getRequestURI().replace(request.getContextPath(), "");
		if (request.getQueryString()!=null && !request.getQueryString().equals(""))
			uri = uri + "?" + request.getQueryString();
		return uri;
	}
}
