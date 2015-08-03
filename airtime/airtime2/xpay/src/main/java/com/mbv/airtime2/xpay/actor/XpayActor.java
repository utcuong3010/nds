package com.mbv.airtime2.xpay.actor;

import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.util.Duration;

import com.mbv.airtime2.xpay.XpayConfig;
import com.mbv.airtime2.xpay.Stub.PartnerServiceLocator;
import com.mbv.airtime2.xpay.Stub.PartnerServiceSoap;
import com.mbv.airtime2.xpay.domain.AtTransaction;
import com.mbv.airtime2.xpay.domain.BalanceRequest;
import com.mbv.airtime2.xpay.domain.BalanceResponse;
import com.mbv.airtime2.xpay.domain.Handshake;
import com.mbv.airtime2.xpay.domain.Login;
import com.mbv.airtime2.xpay.domain.Request;
import com.mbv.airtime2.xpay.domain.RequestType;
import com.mbv.airtime2.xpay.domain.Response;
import com.mbv.airtime2.xpay.domain.Stop;
import com.mbv.airtime2.xpay.domain.TopupRequest;

public class XpayActor extends UntypedActor {

	private static Logger LOGGER = Logger.getLogger(XpayActor.class);
	private PartnerServiceSoap serviceSoap;
	private XpayConfig config;
	private List<String> successCodeList;
	private List<String> pendingCodeList;
	private List<String> unknownCodeList;
	private ActorRef forwarder;

	@Override
	public void preStart() {
		LOGGER.info(getSelf() + " started");
		System.out.println(getSelf() + " started");
		String successCodes = config.getSuccessCodes();
		String pendingCodes = config.getPendingCodes();
		String unknownCodes = config.getUnknownCodes();
		successCodeList = Arrays.asList(successCodes.split("\\s*,\\s*"));
		pendingCodeList = Arrays.asList(pendingCodes.split("\\s*,\\s*"));
		unknownCodeList = Arrays.asList(unknownCodes.split("\\s*,\\s*"));
	}

	@Override
	public void postStop() {
		LOGGER.warn(getSelf() + " stopped");
	}

	public XpayActor(XpayConfig config, ActorRef forwarder) {
		this.config = config;
		this.forwarder = forwarder;
	}

	private PartnerServiceSoap getService() throws ServiceException {
		if (serviceSoap == null) {
			PartnerServiceLocator locator = new PartnerServiceLocator();
			locator.setPartnerServiceSoapEndpointAddress(config.getWebservice_url());
			serviceSoap = locator.getPartnerServiceSoap();
		}
		return serviceSoap;
	}

	public String GetMD5Hash(String encTarget) {
		MessageDigest mdEnc = null;
		try {
			mdEnc = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			LOGGER.error("Exception while encrypting to md5", e);
		} // Encryption algorithm
		mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
		String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
		while (md5.length() < 32) {
			md5 = "0" + md5;
		}
		return md5;
	}

	public String Sign(Request request) {
		String signRules = "";
		String sign;

		if (request.reqtype.equals(RequestType.LOGIN))
			signRules = request.reqtype + "|" + request.username + "|" + request.password + "|"
					+ request.version + "|" + request.partnercode;
		else if (request.reqtype.equals(RequestType.LOGOUT))
			signRules = request.reqtype + "|" + request.sessionid;
		else if (request.reqtype.equals(RequestType.CHANGE_PASSWORD))
			signRules = request.reqtype + "|" + request.sessionid + "|" + request.password + "|"
					+ request.new_pass;
		else if (request.reqtype.equals(RequestType.BALANCE))
			signRules = request.reqtype + "|" + request.sessionid;
		else if (request.reqtype.equals(RequestType.TOPUP))
			signRules = request.reqtype + "|" + request.sessionid + "|" + request.product_type
					+ "|" + request.topup_account + "|" + request.amount;
		else if (request.reqtype.equals(RequestType.POSTPAID))
			signRules = request.reqtype + "|" + request.sessionid + "|" + request.product_type
					+ "|" + request.topup_account + "|" + request.amount;
		else if (request.reqtype.equals(RequestType.PREPAID))
			signRules = request.reqtype + "|" + request.sessionid + "|" + request.product_type
					+ "|" + request.quantity;
		else if (request.reqtype.equals(RequestType.TRANSACTION_HISTORY))
			signRules = request.reqtype + "|" + request.sessionid + "|" + request.trace_number;
		else
			signRules = request.reqtype + "|" + request.sessionid;

		sign = GetMD5Hash(signRules);

		return sign;
	}

	public String base64Encode(String inputString) {
		String result = "";
		try {
			byte[] byteArray = inputString.getBytes("UTF-8");
			result = Base64.encode(byteArray);
		}
		catch (Exception ex) {

		}

		result = result.replaceAll("\r\n", "");
		return result;
	}

	public String base64Decode(String inputString) {
		String result = "";
		try {
			byte[] byteArray = inputString.getBytes("UTF-8");
			byteArray = Base64.decode(inputString);
			result = new String(byteArray);
		}
		catch (Exception ex) {

		}
		return result;
	}

	public String About() throws ServiceException, RemoteException {
		PartnerServiceSoap soap = getService();

		return soap.about();
	}

	public String Echo() throws ServiceException, RemoteException {
		PartnerServiceSoap soap = getService();

		return soap.echo();
	}

	public String login() throws ServiceException, JAXBException, SAXException, IOException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.LOGIN;
		request.username = config.getUsername();
		request.password = GetMD5Hash(config.getPassword());
		request.version = config.getVersion();
		request.appid = config.getAppid();
		request.partnercode = config.getPartner_code();
		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	public String logout(String sessionId) throws ServiceException, JAXBException, SAXException,
			IOException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.LOGOUT;
		request.sessionid = sessionId;
		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	public String callBalance(String sessionId) throws ServiceException, JAXBException,
			SAXException, IOException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.BALANCE;
		request.sessionid = sessionId;
		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	public String handshake(String sessionId) throws ServiceException, JAXBException, SAXException,
			IOException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.HANDSHAKE;
		request.sessionid = sessionId;
		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	public String Prepaid(String sessionId, String product_type, int quantity)
			throws ServiceException, JAXBException, SAXException, IOException,
			ParserConfigurationException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.PREPAID;
		request.sessionid = sessionId;
		request.product_type = product_type;
		request.quantity = quantity + "";

		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	public String callTopup(String sessionId, String requestId, String product_type,
			String topup_account, long topup_value) throws ServiceException, JAXBException,
			SAXException, IOException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.TOPUP;
		request.requestid = requestId;
		request.sessionid = sessionId;
		request.product_type = product_type;
		request.topup_account = topup_account;
		request.amount = topup_value + "";

		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	public String callPostPaid(String sessionId, String requestId, String product_type,
			String topup_account, long topup_value) throws ServiceException, JAXBException,
			SAXException, IOException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.POSTPAID;
		request.requestid = requestId;
		request.sessionid = sessionId;
		request.product_type = product_type;
		request.topup_account = topup_account;
		request.amount = topup_value + "";

		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	public String queryTrans(String sessionid, String trace_number) throws ServiceException,
			RemoteException, JAXBException {
		PartnerServiceSoap soap = getService();
		Request request;
		String result;

		request = new Request();
		request.reqtype = RequestType.TRANSACTION_HISTORY;
		request.sessionid = sessionid;
		request.trace_number = trace_number;
		request.sign = Sign(request);

		result = soap.userRequest(base64Encode(request.toXMLString()));
		result = base64Decode(result);
		return result;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		LOGGER.info(getSelf() + " : Receives from " + getSender() + " message " + message);
		Response response = null;
		String strRes = "";
		try {

			/**
			 * Login to XPay then send the current balance of account to the
			 * master.
			 */
			if (message instanceof Login) {
				strRes = login();
				if (!"".equals(strRes)) {
					response = new Response(strRes);
					if (Response.State.SUCCESS.getValue().equalsIgnoreCase(response.state)) {
						XpayMaster.sessionId = response.sessionid;
						BalanceResponse balanceResponse = new BalanceResponse(response);
						getSender().tell(balanceResponse, getSelf());
					}
					else {
						LOGGER.error("LOGIN ERROR: username=" + response.username + " message="
								+ response.message);
						getSender().tell(new Stop());
					}

				}
				else {
					LOGGER.error("LOGIN ERROR ! PLEASE CHECK CONFIG ...");
					getSender().tell(new Stop());
				}
			}
			/**
			 * Make the transaction with XPAY. After finishing, send the updated
			 * transaction back to the master to update database
			 */
			else if (message instanceof TopupRequest) {
				TopupRequest request = (TopupRequest) message;
				AtTransaction transaction = new AtTransaction();
				transaction.setAt_txn_id(Long.parseLong(request.getAtTxnId()));

				try {
					// TOPUP or POSTPAID
					if (request.getReqtype() == RequestType.TOPUP)
						strRes = callTopup(XpayMaster.sessionId, request.getAtTxnId(),
								request.getProduct_type(), request.getMsisdn(), request.getAmount());
					else
						strRes = callPostPaid(XpayMaster.sessionId, request.getAtTxnId(),
								request.getProduct_type(), request.getMsisdn(), request.getAmount());

					response = new Response(strRes);
					String error_code = response.state;
					transaction.setError_code(error_code);

					String txn_status = parseStatusFromErrorCode(error_code);
					transaction.setTxn_status(txn_status);

					LOGGER.info("\ntxId= " + transaction.getAt_txn_id() + " error_code= "
							+ error_code + " txn_status= " + txn_status + " tracenumber= "
							+ response.tracenumber);

					// error occur: retry after 1 minute
					if (txn_status.equalsIgnoreCase("PENDING")) {
						getContext()
								.system()
								.scheduler()
								.scheduleOnce(Duration.create(60, TimeUnit.SECONDS), getSender(),
										request);
					}
					else {
						transaction.setMessage_id(response.tracenumber);
					}
				}
				catch (Exception ex) {
					transaction.setTxn_status("PENDING");
					LOGGER.error("TopupRequest ERROR: " + request, ex);
				}
				getSender().tell(transaction, getSelf());

			}

			/**
			 * query UNKNOWN transaction and update new status.
			 */
			else if (message instanceof AtTransaction) {
				AtTransaction transaction = (AtTransaction) message;
				strRes = queryTrans(XpayMaster.sessionId, transaction.getMessage_id());
				if (!"".equals(strRes)) {
					response = new Response(strRes);
					String error_code = response.state;
					transaction.setError_code(error_code);
					String txn_status = parseStatusFromErrorCode(error_code);
					transaction.setTxn_status(txn_status);

					LOGGER.info("\ntxId= " + transaction.getAt_txn_id() + " message_id= "
							+ transaction.getMessage_id() + " error_code= " + error_code
							+ " txn_status= " + txn_status + " tracenumber= "
							+ response.tracenumber);

					// update transaction if success or refund
					if (Response.State.SUCCESS.getValue().equalsIgnoreCase(error_code)
							|| Response.State.REFUND.getValue().equalsIgnoreCase(error_code)) {
						getSender().tell(transaction, getSelf());
					}
				}
				else
					LOGGER.warn("queryTrans response '' : txID=" + transaction.getAt_txn_id() + ", messsage_id="
							+ transaction.getMessage_id() + ", sessionId" + XpayMaster.sessionId);
			}
			/**
			 * Ask XPAY the current balance of account.Send the result back to
			 * master
			 */
			else if (message instanceof BalanceRequest) {

				strRes = callBalance(XpayMaster.sessionId);
				if (!"".equals(strRes)) {
					response = new Response(strRes);
					if (Response.State.KICKOFF.getValue().equalsIgnoreCase(response.state)) {
						getSelf().tell(new Login(), this.forwarder);
					}else {
						BalanceResponse balanceResponse = new BalanceResponse(response);
						this.forwarder.tell(balanceResponse, getSelf());
					}
				}
			}

			/**
			 * Login again if call Handshake return KICKOFF
			 */
			else if (message instanceof Handshake) {
				strRes = handshake(XpayMaster.sessionId);
				if (!"".equals(strRes)) {
					response = new Response(strRes);
					if (Response.State.KICKOFF.getValue().equalsIgnoreCase(response.state)) {
						getSelf().tell(new Login(), this.forwarder);
					}
				}
			}
			else {
				throw new Exception("Unhandled message type " + message.getClass().toString());
			}

		}
		catch (Exception ex) {
			LOGGER.error(getSelf() + "Xpay throw Exception with request = " + message, ex);
		}

	}

	// Map Error Code from Xpay to Transaction Status
	private String parseStatusFromErrorCode(String errorCodeString) {
		if (successCodeList.contains(errorCodeString))
			return "SUCCESS";
		else if (pendingCodeList.contains(errorCodeString))
			return "PENDING";
		else if (unknownCodeList.contains(errorCodeString))
			return "UNKNOWN";
		else
			return "ERROR";
	}

}
