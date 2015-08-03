package com.mbv.airtime2.epay;

import com.mbv.airtime2.epay.message.RequestMessage;
import com.mbv.airtime2.integration.IntegrationPayload;

public class Utils {

	/**
	 * Convert from system payload to the desired object passed to the master
	 * @param payload
	 * @return
	 * @throws Exception
	 */
	public static Object fromIntegrationPayloadWithConfig(
			IntegrationPayload payload) throws Exception {
		try{
			String atTxnId = payload.get("transaction_id");
			String telcoId = payload.get("telco_id");
			String msisdn = payload.get("msisdn");
			if (atTxnId == null || msisdn == null)
				throw new Exception("Invalid EPAY request Request");
			long amount = Long.parseLong(payload.get("amount"));
			return new RequestMessage(atTxnId, telcoId , msisdn.trim(), amount);
		}catch (Exception e){
			throw new Exception("Unknown payload request type");
		}
	}
}
