package com.mbv.airline.sabre.commands;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.TerminalConfig;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.utils.Utils;

public class SignIn extends BaseCommand {
	// username; //6 chars
	// password; //8 chars
	
	private SenderManager senderManager;
	
	public SignIn(SenderManager senderManager) {
		this.senderManager = senderManager;
	}

	public Result execute(Terminal terminal) {
		try {
			TerminalConfig config = terminal.getConfig();
			String username = config.getUsername();
			String password = config.getPassword();

			logger.info("Send command sign in!!!");
			this.send("SI*" + username, terminal);
			String response1 = this.receive(terminal);
			String response2 = this.receive(terminal);
			if (!response1.startsWith("SI<"))
				return new Result(Code.ERROR);

			String _username = ("000000" + username).substring(username
					.length());
			String _sufix = (config.getAirlineCode() + "     ").substring(0, 5);
			this.send("AG<" + password + "><" + _username + "><" + _sufix
					+ "><.><*><........>", terminal);
			response1 = this.receive(terminal);
			response2 = this.receive(terminal);

			if (response2.contains("RE-TYPE YOUR PASSCODE AND PRESS ENTER")) {
				this.send("AG<" + password + "><" + _username + "><" + _sufix
						+ "><.><*><........>", terminal);
				response1 = this.receive(terminal);
				response2 = this.receive(terminal);
			}

			if(response2.contains("UTIL YOUR PASSCODE MUST BE CHANGED")){
				String mailSubject = "Please change passcode of VNA "+username;
				senderManager.sendMailMessage(null, mailSubject, "Response from sabre server:\n"+Utils.removeNonASCII(response2));
				logger.info("SIGN IN RESPONSE2 : "+ Utils.removeNonASCII(response2));
			}
			
			if (!" ".equals(response1)){
				if(response2.contains("YOUR PASSCODE MUST BE CHANGED")){
					return new Result(Code.ERROR,"YOUR PASSCODE MUST BE CHANGED.\nTO CHANGE YOUR PASSCODE\nSIGN-IN AGAIN, SUPPLY CURRENT AND NEW PASSCODE");
				}
				return new Result(Code.ERROR);
			}
			logger.info("SIGN IN RESPONSE ", response1 + "   " + response2);
		} catch (TerminalException e) {
			logger.error("SIGN IN ERROR: ", e);
			return new Result(Code.TERMINAL_ERROR, "SIGN IN ERROR: "
					+ e.getMessage());
		}
		return new Result(Code.SUCCESS);
	}

}
