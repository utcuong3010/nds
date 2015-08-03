package com.mbv.airline.sabre.actors.agent;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.TerminalConfig;
import com.mbv.airline.sabre.commands.CloseReportCommand;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.SignIn;
import com.mbv.airline.sabre.common.ConfigInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by phuongvt on 12/8/14.
 */

public class CloseReportAgent {
	private String SMS_Structure = "Dong bao cao xuat ve VNA ngay %s vao luc %s: %s";
	private String Email_Content = "Đóng báo cáo xuất vé VNA ngày %s vào lúc %s: %s";
	private String Email_Subject = "VNA_AutoCloseReport_%s";
	private String Error_Detail = "Sign-in error";

	private final String CLOSE_REPORT_SUCCESS = "Thanh cong -^___^-";
	private final String CLOSE_REPORT_FAIL = "That bai #__# !!!";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ConfigInfo closeReportInfo;

	private Terminal terminal;

	private TerminalConfig terminalConfig;

	private Timer timer;

//	private JMSSending jmsSending;
	
	private SenderManager senderManager;

	public void setSenderManager(SenderManager senderManager) {
		this.senderManager = senderManager;
	}

	public CloseReportAgent() {
		timer = new Timer();
	}

	public void start() {
		if (closeReportInfo.isEnable()) {
			logger.info("start auto_close_report service ...");
			Calendar today = Calendar.getInstance(TimeZone
					.getTimeZone(closeReportInfo.getTimeZone()));
			today.set(Calendar.HOUR_OF_DAY, closeReportInfo.getHour());
			today.set(Calendar.MINUTE, closeReportInfo.getMinute());
			today.set(Calendar.SECOND, closeReportInfo.getSecond());

			logger.info(String
					.format("Configured time: %s:%s:%s %s --> System time correlation: %s",
							closeReportInfo.getHour(), closeReportInfo
									.getMinute(), closeReportInfo.getSecond(),
							today.getTimeZone().getDisplayName(), today
									.getTime()));

			try {
				TimerTask tt = new TimerTask() {
					public void run() {
						int numRetry = closeReportInfo.getNumberRetry();
						try {
							if (numRetry <= 0)
								tryToClose(true, closeReportInfo.isMailDetail());
							else {
								boolean tryToclose = tryToClose(false,
										closeReportInfo.isMailDetail());
								if (!tryToclose && numRetry > 1) {
									int count = 1;
									for (int i = numRetry; i > -1; i--) {
										logger.info("Retry to close: " + count);
										if (i == 0)
											tryToclose = tryToClose(true,
													closeReportInfo
															.isMailDetail());
										else
											tryToclose = tryToClose(false,
													closeReportInfo
															.isMailDetail());
										if (tryToclose)
											break;
										count++;
										Thread.sleep(closeReportInfo
												.getTimeDelay());
									}
								}
							}
						} catch (Exception ex) {
							logger.info(ex.getMessage());
							sendMessage(CLOSE_REPORT_FAIL,
									"Can't connect sabre server");
						}
					}
				};

				timer.schedule(tt, today.getTime(),
						TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}
		}
	}

	private boolean tryToClose(boolean sendMsg, boolean detail) {
		if (startTerminal()) {
			CloseReportCommand report = new CloseReportCommand(terminalConfig);
			Result result = report.execute(terminal);
			terminal.close();
			if (result.getStatus().equals(Result.Code.SUCCESS)) {
				if (detail)
					sendMessage(CLOSE_REPORT_SUCCESS, result.getDescription());
				else
					sendMessage(CLOSE_REPORT_SUCCESS, "");
				return true;
			} else if (sendMsg) {
				logger.info("CLOSE REPORT FAILED: " + result.getDescription());
				if (detail)
					sendMessage(CLOSE_REPORT_FAIL, result.getDescription()
							+ "\nPlease check Sabre Interact.");
				else
					sendMessage(CLOSE_REPORT_FAIL, "");
			}
		} else if (sendMsg) {
			logger.info("CLOSE REPORT FAILED: " + Error_Detail);
			sendMessage(CLOSE_REPORT_FAIL, Error_Detail);
		}
		return false;
	}

	public void stop() {
		logger.info("stop auto_close_report service ...");
		terminal.close();
		timer.cancel();
	}

	private boolean startTerminal() {
		if (terminal.open() == false) {
			logger.info("Connection Error");
			return false;
		}

		Result result = (new SignIn(senderManager)).execute(terminal);
		if (result.getStatus() != Result.Code.SUCCESS) {
			Error_Detail=result.getDescription();
			logger.info("Sign-in Error");
			return false;
		}

		return true;
	}

	private void sendMessage(String msg, String detail) {
		Calendar today = Calendar.getInstance(TimeZone
				.getTimeZone(closeReportInfo.getTimeZone()));
		String date = new SimpleDateFormat("yyyy/MM/dd")
				.format(today.getTime());
		String time = new SimpleDateFormat("HH:mm:ss").format(today.getTime());
		if (!detail.equals(""))
			sendEmail(msg + "\n\nDETAIL: \n" + detail, date, time);
		else
			sendEmail(msg, date, time);
		sendSms(msg, date, time);
	}

	private void sendSms(String msg, String date, String time) {
		try {
			for (String mobileNo : closeReportInfo.getMobiles()) {
				senderManager.sendSmsMessage(mobileNo,
						String.format(SMS_Structure, date, time, msg), true);
//				jmsSending.sendSMSMessage(mobileNo,
//						String.format(SMS_Structure, date, time, msg), true);
			}
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}

	private void sendEmail(String msg, String date, String time) {
		try {
			msg = msg.replaceAll("\n", "<br>");
//			jmsSending.sendMailMessage(closeReportInfo.getEmails(),
//					String.format(Email_Subject, date),
//					String.format(Email_Content, date, time, msg), true);
			senderManager.sendMailMessage(Arrays.asList(closeReportInfo.getEmails()), String.format(Email_Subject, date),
					String.format(Email_Content, date, time, msg));
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}

	public void setCloseReportInfo(ConfigInfo reportInfo) {
		this.closeReportInfo = reportInfo;
	}

	public void setTerminalConfig(TerminalConfig terminalConfig) {
		this.terminalConfig = terminalConfig;
		terminal = new Terminal(terminalConfig);
	}

//	public void setJmsSending(JMSSending jmsSending) {
//		this.jmsSending = jmsSending;
//	}
}
