package com.mbv.airtime2.vngmobile.domain;

import org.apache.ibatis.annotations.Update;


public interface Mapper {
	@Update("UPDATE provider_amount SET total_loaded=#{balance}, total_used=0 WHERE provider_id=#{providerId}")
	public void UpdateBalance(BalanceInfo balanceInfo);
	
	@Update("UPDATE at_transaction SET txn_status=#{status}, error_code=#{errorCode} WHERE at_txn_id=#{atTxnId}")
	public void UpdateTxn(TopupResponse message);
}
