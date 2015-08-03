DROP VIEW IF EXISTS `cdr_comparison_view`;
CREATE VIEW `cdr_comparison_view` AS 
		select a.*, 'MATCHED' as result from `cdr_data` a, `cdr_data` b where 
		a.provider_id='MOBI' and a.created_by='MOBIVI' and a.provider_id=b.provider_id and b.created_by='MOBIFONE' and
		a.message_id=b.message_id and a.at_txn_id=b.at_txn_id and 
		a.error_code=b.error_code 
		union
		select a.*, 'UNMATCHED' as result from  `cdr_data` a where 
		a.provider_id='MOBI' and a.created_by='MOBIVI' and
		a.txn_date<=(select max(txn_date) from `cdr_data` b where b.created_by='MOBIFONE') and
		not exists(select c.message_id from `cdr_data` c where 
		    a.provider_id=c.provider_id and c.created_by='MOBIFONE' and
		a.message_id=c.message_id and a.at_txn_id=c.at_txn_id and 
		a.error_code=c.error_code
		)
		union
		select a.*, 'UNMATCHED' as result from  `cdr_data` a where 
		a.provider_id='MOBI' and a.created_by='MOBIFONE' and
		a.txn_date<=(select max(txn_date) from `cdr_data` b where b.created_by='MOBIVI') and
		not exists(select c.message_id from `cdr_data` c where 
		    a.provider_id=c.provider_id and c.created_by='MOBIVI' and
		a.message_id=c.message_id and a.at_txn_id=c.at_txn_id and 
		a.error_code=c.error_code
		)
		union
		select a.*, 'MATCHED' as result from `cdr_data` a, `cdr_data` b where 
		a.provider_id='VTEL' and a.created_by='MOBIVI' and a.provider_id=b.provider_id and b.created_by='VIETTEL' and
		a.message_id=b.message_id and 
		a.error_code=b.error_code 
		union
		select a.*, 'UNMATCHED' as result from  `cdr_data` a where 
		a.provider_id='VTEL' and a.created_by='MOBIVI' and
		a.txn_date<=(select max(txn_date) from `cdr_data` b where b.created_by='VIETTEL') and
		not exists(select c.message_id from `cdr_data` c where 
		    a.provider_id=c.provider_id and c.created_by='VIETTEL' and
		a.message_id=c.message_id and 
		a.error_code=c.error_code
		)
		union
		select a.*, 'UNMATCHED' as result from  `cdr_data` a where 
		a.provider_id='VTEL' and a.created_by='VIETTEL' and
		a.txn_date<=(select max(txn_date) from `cdr_data` b where b.created_by='MOBIVI') and
		not exists(select c.message_id from `cdr_data` c where 
		    a.provider_id=c.provider_id and c.created_by='MOBIVI' and
		a.message_id=c.message_id and 
		a.error_code=c.error_code
		)
		union
		select b.*, 'MATCHED' as result from `cdr_data` a, `cdr_data` b where 
		a.provider_id='VIETPAY' and a.created_by='MOBIVI' and a.provider_id=b.provider_id and b.created_by='VIETPAY' and
		a.at_txn_id=b.at_txn_id and 
		a.status=b.status
		union
		select a.*, 'UNMATCHED' as result from  `cdr_data` a where 
				a.provider_id='VIETPAY' and a.created_by='MOBIVI' and
			    a.status='SUCCESS' and
		    ((not exists (select c.at_txn_id from `cdr_data` c where c.created_by='VIETPAY' and c.at_txn_id=a.at_txn_id))
		     or  (exists (select d.at_txn_id from `cdr_data` d where d.created_by='VIETPAY' and d.at_txn_id=a.at_txn_id and a.status<>d.status)))
		union     
		select a.*, 'UNMATCHED' as result from  `cdr_data` a where 
				a.provider_id='VIETPAY' and a.created_by='VIETPAY' and
		    ((not exists (select c.at_txn_id from `cdr_data` c where c.created_by='MOBIVI' and provider_id='VIETPAY' and c.at_txn_id=a.at_txn_id))
		     or  (exists (select d.at_txn_id from `cdr_data` d where d.created_by='MOBIVI' and provider_id='VIETPAY' and d.at_txn_id=a.at_txn_id and a.status<>d.status)));