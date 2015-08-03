package com.mbv.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.commons.codec.binary.Hex;

public class MiscellaneousHelper {
	public static final Locale[] LOCALES = Locale.getAvailableLocales();
	public static final Hashtable<String, String> COUNTRIES = new Hashtable<String, String>();
	public static final Hashtable<String, String> LANGUAGES = new Hashtable<String, String>();
	static {
		for (Locale locale : LOCALES) {
			COUNTRIES.put(locale.getCountry(), locale.getDisplayCountry());
			LANGUAGES.put(locale.getLanguage(), locale.getDisplayLanguage());
		}
	}

	public static String getStackTrace(Throwable ex) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os, true, "UTF-8");
		do {
			ex.printStackTrace(ps);
			if (ex.getCause()==null || ex.getCause()==ex) break;
			ex = ex.getCause();
			ps.println();
		} while (true);
		
		String ret = os.toString("UTF-8");
		try {
			ps.close();
		} catch (Throwable ignored) {
		}
		try {
			os.close();
		} catch (Throwable ignored) {
		}
		return ret;
	}

	public static String getCountryName(String code) {
		if (COUNTRIES.containsKey(code)) {
			return COUNTRIES.get(code);
		} else {
			return "<Unknown>";
		}
	}

	public static String sha1HexString(String value) throws NoSuchAlgorithmException {
		return new String(Hex.encodeHex(sha1(value.getBytes())));
	}

	public static String getFullName(String firstName, String lastName,
			Locale locale) {
		if (firstName == null) {
			return lastName;
		} else if (lastName == null) {
			return firstName;
		} else {
			if (locale == null
					|| locale.getLanguage()
							.equals(Locale.ENGLISH.getLanguage())) {
				return firstName + " " + lastName;
			} else {
				return lastName + " " + firstName;
			}
		}
	}

	public static byte[] sha1(byte[] raw) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(raw);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Unexpected exception: "
					+ e.getMessage(), e);
		}
	}
}
