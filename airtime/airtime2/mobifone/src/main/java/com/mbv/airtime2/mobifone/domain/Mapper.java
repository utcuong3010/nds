package com.mbv.airtime2.mobifone.domain;

import org.apache.ibatis.annotations.Update;

public interface Mapper {
	
	@Update("UPDATE provider_amount SET total_loaded=#{availableAmount}, total_used=0 WHERE provider_id='MOBI'")
	public void UpdateBalance(BalanceResponse message);
	
	@Update("UPDATE at_transaction SET txn_status=#{status}, error_code=#{errorCode}, message_id=#{refTxnId} WHERE at_txn_id=#{atTxnId}")
	public void UpdateTxn(TopupResponse message);
}
