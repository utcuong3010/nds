package com.mbv.airtime2.epay.domain;

import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Select;

import com.mbv.airtime2.epay.Stub.QueryBalanceResult;

public interface Mapper {

	@Update("UPDATE at_transaction SET txn_status=#{txn_status}, error_code=#{error_code}, updated_by=#{updated_by}, updated_date=#{updated_date} WHERE at_txn_id=#{at_txn_id} and txn_status IN ('PENDING','UNKNOWN')")
	public void UpdateTxn(AtTransaction at);
	
	//@Update("UPDATE at_transaction SET txn_status='UNKNOWN' where at_txn_id=#{atTxnId} and txn_status='PENDING'")
	//public void LockTxnFromRequestMessage(RequestMessage rm);
	
	@Update("UPDATE provider_amount SET total_loaded=#{balance_avaiable}, total_used=0 WHERE provider_id='EPAY'")
	public void UpdateBalance(QueryBalanceResult result);
	
	@Select("SELECT * from at_transaction where txn_status=#{param1} and conn_type=#{param2}")
	public List<AtTransaction>searchTransactionByStatusAndConnType(String status,String conn);
	
}
