package com.mbv.airline.actors.jetstar;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.jsoup.Connection;

import com.mbv.airline.common.BookResult;
import com.mbv.airline.common.PayResult;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.util.JsoupExecute;

/**
 * 
 * @author phamtuyen
 *
 */
public class JetstarStub {

	/**
	 * logger
	 */
	final static Logger logger = Logger.getLogger(JetstarStub.class);

	/**
	 * search fare info
	 */
	private JetstarSearch jetstarSearch = null;

	/**
	 * book itinerary
	 */
	private JetstarBook jetstarBook = null;

	/**
	 * pay for reservation code
	 */
	private JetstarPay jetstarPay = null;

	/**
	 * retry reservation code
	 */
	private JetstarRetryBooking jetstarRetryBooking = null;

	/**
	 * flag: check thread working
	 */
	private AtomicBoolean isBusyFlag = new AtomicBoolean(false);

	/**
	 * keep session
	 */
	private static String SESSION;

	/**
	 * keep view state
	 */
	private static String VIEWSTATE;

	/**
	 * account info
	 */
	private JetstarConfig jetstarConfig;

	/**
	 * hash map params form login
	 */
	private HashMap<String, String> paramsFormLogin;

	/**
	 * init account Info
	 * @param jetstarConfig
	 */	
	public JetstarStub(final JetstarConfig jetstarConfig) {
		paramsFormLogin = new HashMap<String, String>() {		
			{						
				put("__EVENTTARGET", "ControlGroupSelectView$LinkButtonSubmit");			
				put("ControlGroupNewTradeLoginAgentView$AgentNewTradeLoginView$TextBoxUserID", jetstarConfig.getUsername());
				put("ControlGroupNewTradeLoginAgentView$AgentNewTradeLoginView$PasswordFieldPassword", jetstarConfig.getPassword());
				put("ControlGroupNewTradeLoginAgentView$AgentNewTradeLoginView$ButtonLogIn", "");
			}
		};		

		this.jetstarConfig = jetstarConfig;
		init();
		scheduleKeepSession();

		jetstarSearch = new JetstarSearch(jetstarConfig);
		jetstarBook = new JetstarBook(jetstarConfig);
		jetstarPay = new JetstarPay(jetstarConfig);
		jetstarRetryBooking = new JetstarRetryBooking(jetstarConfig);
	}

	/**
	 * do: go to application at search page
	 */
	private void init(){
		try {

			logger.info("[init] - Beginning ... ");
			doLogin();		
			getViewstateSearchPage();
			logger.info("[init] - End ... ");

		} catch (Exception ex) {

		}
	}

	/**
	 * do: login
	 * @throws Exception
	 */
	private void doLogin() throws Exception {
		try {			
			// doGet
			String urlLogin = jetstarConfig.getBaseUrl() + "TradeLoginAgent.aspx";
			Connection.Response response = JsoupExecute.doGet(urlLogin);			
			URL urlResult = response.url();
			if(!urlResult.toString().contains("TradeLoginAgent.aspx"))
				throw new Exception("CAN NOT GET [URL] = " + urlLogin);							
			// doPost
			SESSION = JsoupExecute.getSession(response);
			response = JsoupExecute.doPost(urlLogin, paramsFormLogin, SESSION);
			urlResult = response.url();
			if(!urlResult.toString().contains("TradeSalesHome.aspx"))
				throw new Exception("CAN NOT POST TO [URL] = " + urlLogin + " WITH [PARAMS] = " + paramsFormLogin);				

		} catch (Exception ex) {
			logger.error("LOGIN FAILED [ERROR] = " + ex);
			throw ex;
		}
	}	

	/**
	 * do: get view state of search page
	 * @return
	 * @throws Exception
	 */
	private boolean getViewstateSearchPage() throws Exception {
		try {
			String url = jetstarConfig.getBaseUrl() + "TradeSalesHome.aspx";
			Connection.Response response = JsoupExecute.doGet(url, SESSION);	
			URL urlResult = response.url();			
			if(!urlResult.toString().contains("TradeSalesHome.aspx")) {
				logger.error("CAN NOT GET [URL] = " + url);
				return false;
			}
			
			VIEWSTATE = JsoupExecute.getViewState(response);
			return true;

		} catch (Exception ex) {
			logger.error("CANT NOT GET [URL] = " + jetstarConfig.getBaseUrl() + "TradeSalesHome.aspx" + " [ERROR] = " + ex);			
			throw ex;
		}		
	}

	/**
	 * do: search fare info
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AirFarePriceInfos doSearch(SearchItineraryCmd searchItineraryCmd) throws Exception {		
		try {			

			isBusyFlag.set(true);
			return jetstarSearch.doSearch(searchItineraryCmd,SESSION,VIEWSTATE);

		} catch (Exception ex) {
			throw ex;

		} finally {
			isBusyFlag.set(false);
		}
	}

	/**
	 * do: book itinerary
	 * @param itinerary
	 * @return
	 * @throws Exception
	 */
	public BookResult doBook(AirItinerary itinerary) throws Exception {
		try {

			isBusyFlag.set(true);
			return jetstarBook.doBook(itinerary, SESSION, VIEWSTATE);

		} catch (Exception ex) {
			throw ex;

		} finally {
			isBusyFlag.set(false);
		}
	}

	/**
	 * do: pay for reservation code
	 * @param itinerary
	 * @return
	 * @throws Exception
	 */
	public PayResult doPay(AirItinerary itinerary) throws Exception {
		try {

			isBusyFlag.set(true);
			return jetstarPay.doPay(itinerary,SESSION);

		} catch (Exception ex) {
			throw ex;

		} finally {
			isBusyFlag.set(false);
		}
	}

	/**
	 * do: search itinerary with reservation code
	 * @param itinerary
	 * @return
	 * @throws Exception
	 */
	public void doRetrieveBooking(AirItinerary itinerary) throws Exception {
		try {

			isBusyFlag.set(true);
			jetstarRetryBooking.doRetrieveBooking(itinerary, SESSION);

		} catch (Exception ex) {
			throw ex;

		} finally {
			isBusyFlag.set(false);
		}
	}


	/**
	 * do: verify payment with reservation code
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public boolean doVerifyPayment(VerifyPaymentCmd verifyPaymentCmd) throws Exception {
		try {

			isBusyFlag.set(true);
			return jetstarPay.doVerifyPayment(verifyPaymentCmd, SESSION);

		} catch (Exception ex) {
			throw ex;

		} finally {
			isBusyFlag.set(false);
		}
	}

	/**
	 * do: check session expired
	 * @return
	 */
	private boolean checkExpireSession() throws Exception {
		try {		

			return getViewstateSearchPage();

		} catch (Exception ex) {
			logger.error("CAN NOT GET [URL] = " + jetstarConfig.getBaseUrl() + "TradeSalesHome.aspx" + " [ERROR] = " + ex);
			throw ex;
		}		
	}

	/**
	 * do: request in time for keeping session
	 */
	private void scheduleKeepSession(){	
		Timer time = new Timer();
		ScheduledTask scheduledTask = new ScheduledTask();
		time.schedule(scheduledTask, 90000L, jetstarConfig.getSessionExpire()*60000L); // 5 minute
	}

	public class ScheduledTask extends TimerTask {
		Date now;
		public void run() {
			now = new Date();
			logger.info("TIME NOW: " + now); 
			logger.info("[REFRESH]-STSART REFRESHING PAGE WITH FLAG: " + isBusyFlag.get());		
			if(isBusyFlag.get() == false){
				try {

					if(checkExpireSession() == false)
						init();	

				} catch (Exception ex) {
					init();
					logger.error("SCHEDULE FAILED [ERROR] = " + ex);
				}
			}
		}
	}
}
