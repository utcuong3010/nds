package com.mbv.airtime2.vngmobile;

import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.vngmobile.domain.TopupRequest;

public class Utils {
	public static Object transformPayload(Object message) throws Exception{		
		if(message instanceof IntegrationPayload){
			IntegrationPayload payload = (IntegrationPayload) message;
			String atTxnId = payload.get("transaction_id");
			String msisdn = payload.get("msisdn");
			if(atTxnId == null || msisdn == null) throw new Exception("Invalid Topup Request");
			long amount = Long.parseLong(payload.get("amount"));			
			return new TopupRequest(atTxnId.trim(), msisdn.trim(), amount);
		}else{
			throw new Exception("Invalid Request");
		}
	}
}
