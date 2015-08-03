package com.mbv.airline.sabre.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by phuongvt on 12/8/14.
 */

@Component("configInfo")
public class ConfigInfo {
	private int hour;
	private int minute;
	private int second;
	private boolean enable;
	private boolean mailDetail;
	private int numberRetry;
	private long timeDelay;
	private String[] mobiles;
	private String[] emails;
	private String[] emailTests;
	private String[] mobileTests;
	private String timeZone = "GMT+07:00";

	public int getHour() {
		return hour;
	}

	@Value("${close.report.hour}")
	public void setHour(String h) {
		try {
			h = h.replaceAll(" ", "").replaceAll("\t", "");
			if (!h.equals(""))
				hour = Integer.parseInt(h);
		} catch (Exception ex) {
			hour = 22;
		}
	}

	public int getMinute() {
		return minute;
	}

	@Value("${close.report.minute}")
	public void setMinute(String m) {
		try {
			m = m.replaceAll(" ", "").replaceAll("\t", "");
			if (!m.equals(""))
				minute = Integer.parseInt(m);
		} catch (Exception ex) {
			minute = 0;
		}
	}

	public int getSecond() {
		return second;
	}

	@Value("${close.report.second}")
	public void setSecond(String s) {
		try {
			s = s.replaceAll(" ", "").replaceAll("\t", "");
			if (!s.equals(""))
				second = Integer.parseInt(s);
		} catch (Exception ex) {
			second = 0;
		}
	}

	public String getTimeZone() {
		return timeZone;
	}

	@Value("${close.report.timezone}")
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public int getNumberRetry() {
		return numberRetry;
	}

	@Value("${close.report.numRetry}")
	public void setNumberRetry(String retry) {
		try {
			retry = retry.replaceAll(" ", "").replaceAll("\t", "");
			if (!retry.equals(""))
				numberRetry = Integer.parseInt(retry);
		} catch (Exception ex) {
			numberRetry = 1;
		}
	}

	public long getTimeDelay() {
		return timeDelay;
	}

	@Value("${close.report.timeDelay}")
	public void setTimeDelay(String time) {
		try {
			time = time.replaceAll(" ", "").replaceAll("\t", "");
			if (!time.equals(""))
				timeDelay = Long.parseLong(time);
		} catch (Exception ex) {
			timeDelay = 180000;
		}
	}

	public boolean isEnable() {
		return enable;
	}

	@Value("${close.report.enable}")
	public void setEnable(String enable) {
		if (enable.equals("true"))
			this.enable = true;
		else
			this.enable = false;
	}

	public boolean isMailDetail() {
		return mailDetail;
	}

	@Value("${close.report.mailDetail}")
	public void setMailDetail(String mailDetail) {
		if (mailDetail.equals("true"))
			this.mailDetail = true;
		else
			this.mailDetail = false;
	}

	public String[] getMobiles() {
		return mobiles;
	}

	@Value("${close.report.mobiles}")
	public void setMobiles(String mobis) {
		mobiles = mobis.split(" ");
	}

	public String[] getEmails() {
		return emails;
	}

	@Value("${close.report.emails}")
	public void setEmails(String emailAdds) {
		emails = emailAdds.split(" ");
	}

	public String[] getMobileTests() {
		return mobileTests;
	}

	@Value("${sabre.notify.mobiles}")
	public void setMobileTests(String mobis) {
		mobileTests = mobis.split(" ");
	}

	public String[] getEmailTests() {
		return emailTests;
	}

	@Value("${sabre.notify.emails}")
	public void setEmailTests(String emailAdds) {
		emailTests = emailAdds.split(" ");
	}

	public String toString() {
		String str = "hour: " + hour + " minute: " + minute + " second: "
				+ second;
		for (int i = 0; i < mobiles.length; i++) {
			str += " mobile " + i + " : " + mobiles[i];
		}
		return str;
	}
}
