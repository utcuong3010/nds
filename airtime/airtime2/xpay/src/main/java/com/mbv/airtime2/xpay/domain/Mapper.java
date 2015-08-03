package com.mbv.airtime2.xpay.domain;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface Mapper {
	
	@Update("UPDATE at_transaction SET txn_status=#{txn_status}, error_code=#{error_code}, message_id=#{message_id}, updated_by=#{updated_by}, updated_date=#{updated_date} WHERE at_txn_id=#{at_txn_id} and txn_status IN ('PENDING','UNKNOWN')")
	public void UpdateTxn(AtTransaction at);
	
	@Update("UPDATE provider_amount SET total_loaded=#{balance} WHERE provider_id='XPAY'")
	public void UpdateBalance(BalanceResponse result);
	
	@Select("SELECT * from at_transaction where txn_status=#{param1} and conn_type=#{param2}")
	public List<AtTransaction>searchTransactionByStatusAndConnType(String status,String conn);
	
}