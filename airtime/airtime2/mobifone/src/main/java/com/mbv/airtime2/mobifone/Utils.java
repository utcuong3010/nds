package com.mbv.airtime2.mobifone;

import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.mobifone.domain.BalanceRequest;
import com.mbv.airtime2.mobifone.domain.TopupRequest;

public class Utils {

	private static final String REQUEST_TYPE= "dest_wfp";
	private static final String REQUEST_TOPUP ="wfpMobiFoneTopupRequestAfterQueue";
	private static final String REQUEST_BALANCE ="wfpMobiFoneBalanceRequestAfterQueue";
	
	public static Object fromIntegrationPayload(IntegrationPayload payload) throws Exception{
		String type = payload.get(REQUEST_TYPE);		
		if(REQUEST_TOPUP.equals(type)){
			//transaction_id=201210150000257283, msisdn=0913928786, amount=5000,
			String atTxnId = payload.get("transaction_id");
			String msisdn = payload.get("msisdn");
			if(atTxnId == null || msisdn == null) throw new Exception("Invalid Topup Request");
			long amount = Long.parseLong(payload.get("amount"));			
			return new TopupRequest(atTxnId.trim(), msisdn.trim(), amount);
		} else if(REQUEST_BALANCE.equals(type)){
			return new BalanceRequest();
		}else{
			throw new Exception("Unknown payload request type");
		}		
	}
}
