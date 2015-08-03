package com.mbv.airtime2.epay.actors;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.mbv.airtime2.epay.EpayConfig;
import com.mbv.airtime2.epay.domain.AtTransaction;
import com.mbv.airtime2.epay.message.QueryBalanceMessage;
import com.mbv.airtime2.epay.message.RequestMessage;
import com.mbv.airtime2.epay.Stub.CheckOrdesrCDVResult;
import com.mbv.airtime2.epay.Stub.Interfaces;
import com.mbv.airtime2.epay.Stub.InterfacesServiceLocator;
import com.mbv.airtime2.epay.Stub.PaymentCdvResult;
import com.mbv.airtime2.epay.Stub.QueryBalanceResult;

import akka.actor.UntypedActor;

public class EpayActor extends UntypedActor {

	private static Logger LOGGER = Logger.getLogger(EpayActor.class);

	private Interfaces service = null;
	private EpayConfig config;
	private List<String> successCodeList;
	private List<String> pendingCodeList;

	@Override
	public void preStart() {
		LOGGER.info(getSelf() + " started");
		String successCodes = config.getSuccessCodes();
		String pendingCodes = config.getPendingCodes();
		successCodeList = Arrays.asList(successCodes.split("\\s*,\\s*"));
		pendingCodeList = Arrays.asList(pendingCodes.split("\\s*,\\s*"));

	}

	public void postStop() {
		LOGGER.info(getSelf() + " stopped");
	}

	public EpayActor(EpayConfig config) {
		this.config = config;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		try {
			/**
			 * Make the transaction with EPAY. After finishing, send the updated
			 * transaction back to the master to update database
			 */
			if (message instanceof RequestMessage) {
				// handle Request here
				RequestMessage myMessage = (RequestMessage) message;
				String partnerName = config.getPartnerName();
				String requestId = convertToEpayRequestId(partnerName,
						myMessage.getAtTxnId());
				String telcoId = myMessage.getTelcoId();
				List<String> providerAndType = config.getProviderTypeMap().get(
						telcoId);
				String provider = providerAndType.get(0);
				int type = Integer.parseInt(providerAndType.get(1));
				String account = myMessage.getAccount();
				long amount = myMessage.getAmount();
				String dataSign = requestId + partnerName + provider + type
						+ account + amount + config.getServiceTimeOut();
				String sign = config.getSigner().sign(dataSign);

				// Call the Epay service

				LOGGER.info("Make a transaction with id : " + requestId
						+ " and provider " + provider + " and type " + type
						+ " from account " + account + " with amount : "
						+ amount);

				AtTransaction transaction = new AtTransaction();
				transaction
						.setAt_txn_id(Long.parseLong(myMessage.getAtTxnId()));
				try {
					PaymentCdvResult result = getService().paymentCDV(
							requestId, partnerName, provider, type, account,
							amount, config.getServiceTimeOut(), sign);
					LOGGER.info("Transaction ID : " + requestId + " completed with errorcode " + result.getErrorCode() + " and amountTopUpSuccess "+ result.getAmountTopupSuccess());
					String txnStatus = getStringFromErrorCode(result
							.getErrorCode());
					//DDOS Epay server until amountTopUpSuccess equals to either zero or amountRequest
					if (txnStatus.equals("SUCCESS")){
						if (result.getAmountTopupSuccess() == 0)
							txnStatus = "ERROR";
						else if (result.getAmountTopupSuccess() < amount)
							txnStatus = "UNKNOWN";
					}
					if (txnStatus.equals("ERROR"))
						transaction.setError_code(Integer.toString(result
								.getErrorCode()));
					transaction.setTxn_status(txnStatus);
					
				} catch (Exception e) {
					transaction.setTxn_status("UNKNOWN");
					LOGGER.error(e.getMessage(), e);
				}
				getSender().tell(transaction);
			}
			/**
			 * Ask EPAY the current balance of account.Send the result back to
			 * master
			 */
			else if (message instanceof QueryBalanceMessage) {
				String partnerName = config.getPartnerName();

				QueryBalanceResult result = getService().queryBalance(
						partnerName, config.getSigner().sign(partnerName));
				LOGGER.info("Balance result with errorCode : "+ result.getErrorCode() + " and balance available : "+ result.getBalance_avaiable());

				if (result.getErrorCode() != 0) {
					LOGGER.info("Cannot update balance!! Error code : "
							+ result.getErrorCode() + " Message: "
							+ result.getMessage());
				}
				// if get balance success, tell the master to update.
				if (result != null && result.getErrorCode() == 0) {
					getSender().tell(result);
				}
			}
			/**
			 * Check the pending transactions with EPAY to see whether their
			 * status has changed. If it does, send those transactions back to
			 * the master to update database.
			 */
			else if (message instanceof AtTransaction) {
				AtTransaction transaction = (AtTransaction) message;

				String partnerName = config.getPartnerName();
				String requestId = convertToEpayRequestId(partnerName,
						transaction.getAt_txn_id().toString());
				String sign = config.getSigner().sign(requestId + partnerName);
				try {
					CheckOrdesrCDVResult result = getService().checkOrdersCDV(
							requestId, partnerName, sign);
					LOGGER.info("Check transactions ID : " + requestId + " completed with result error code: " + result.getErrorCode() + " and amountTopupSuccess "+ result.getAmountTopupSuccess());
					int errorCode = result.getErrorCode();
					String txnStatus = getStringFromErrorCode(errorCode);
					//DDOS Epay server until amountTopUpSuccess equals to either zero or amountRequest
					if (txnStatus.equals("SUCCESS") ){
						if (result.getAmountTopupSuccess() == 0)
							txnStatus = "ERROR";
						else if (result.getAmountTopupSuccess() < transaction.getAmount())
							txnStatus = "UNKNOWN";
					}
					
					//if the transaction status is still UNKNOWN, do nothing
					if (txnStatus.equals("UNKNOWN"))
						return;
					transaction.setTxn_status(txnStatus);
					transaction.setError_code(Integer.toString(errorCode));
					transaction.setAmount((int) result.getAmountTopupSuccess());
					getSender().tell(transaction);
				} catch (Exception ex) {
					// Exception, transaction status is still UNKNOWN, do nothing
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	private String convertToEpayRequestId(String partnerName, String atTxnId) {
		return partnerName + "_" + atTxnId;
	}

	// Map Error Code from Epay to Transaction Status
	private String getStringFromErrorCode(int errorCode) {
		String errorCodeString = Integer.toString(errorCode);
		if (successCodeList.contains(errorCodeString))
			return "SUCCESS";
		else if (pendingCodeList.contains(errorCodeString))
			return "UNKNOWN";
		else
			return "ERROR";
	}

	private Interfaces getService() throws Exception {
		if (service == null) {
			InterfacesServiceLocator locator = new InterfacesServiceLocator();
			locator.setInterfacesEndpointAddress(config.getWebservice_url());
			service = locator.getInterfaces();
		}
		return service;
	}
}