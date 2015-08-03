package com.mbv.airtime2.xpay;

import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.xpay.domain.RequestType;
import com.mbv.airtime2.xpay.domain.TopupRequest;

public class Utils {

	public static Object fromIntegrationPayload(String postPaidSuffix,
			IntegrationPayload payload) throws Exception {
		try {
			String atTxnId = payload.get("transaction_id");
			String telcoId = payload.get("telco_id");
			String msisdn = payload.get("msisdn");
			if (atTxnId == null || msisdn == null)
				throw new Exception("Invalid XPAY request Request");
			long amount = Long.parseLong(payload.get("amount"));
			String reqType = RequestType.TOPUP;
			if (telcoId.endsWith(postPaidSuffix)) {
				telcoId = telcoId.replace(postPaidSuffix, "");
				reqType = RequestType.POSTPAID;
			}
			return new TopupRequest(reqType, atTxnId, toProduct_type(telcoId,
					amount), msisdn.trim(), amount);
		} catch (Exception e) {
			throw new Exception("Unknown payload request type");
		}
	}

	public static String toProduct_type(String telcoId, long amount) {
		String rs = telcoId.toUpperCase();
		rs = rs + (amount < 100000 ? "00" : "0") + (amount / 1000);
		return rs;
	}

	public static void main(String[] args) {

		System.err.println(toProduct_type("VINA", 50000));
	}

}
