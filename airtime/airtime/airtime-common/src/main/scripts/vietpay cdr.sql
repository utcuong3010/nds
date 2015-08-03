DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.unknown-errorcode';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.unknown-errorcode','26,99,DELIVERY_RESPONSE_ERROR','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.vietpay-ftp-host';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-ftp-host','222.255.28.184','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.vietpay-ftp-passive-mode';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-ftp-passive-mode','false','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.vietpay-ftp-password';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-ftp-password','5byNkZHF','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.vietpay-ftp-port';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-ftp-port','21','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.vietpay-ftp-username';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-ftp-username','MBV','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.vietpay-local-path';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-local-path','D:/opt/ftp','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.vietpay-remote-path';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-remote-path',NULL,'prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='mobifone' and`type`='config' and`param_key`='settings.mobifone.amount-validate';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('mobifone','config','settings.mobifone.amount-validate','false','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='viettel' and`type`='config' and`param_key`='settings.viettel.amount-validate';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('viettel','config','settings.viettel.amount-validate','false','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vnpay' and`type`='config' and`param_key`='settings.vnpay.amount-validate';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vnpay','config','settings.vnpay.amount-validate','false','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='mobifone' and`type`='config' and`param_key`='settings.mobifone.process-result-time';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('mobifone','config','settings.mobifone.process-result-time','10000','prop',NULL);

DELETE FROM `provider_amount` WHERE `provider_id`='VTEL';
INSERT INTO `provider_amount` (`provider_id`,`total_loaded`,`total_used`,`notif_amount`,`notif_sent`) VALUES ('VTEL',0,0,0,0);

DELETE FROM `airtime_config` WHERE `module`='gateway' and`type`='config' and`param_key`='gateway.constant.error-convert';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('gateway','config','gateway.constant.error-convert','<app>
	<gateway>
		<airtime>
			<error-convert>
				<method id="TOPUP">
					<type id="sys-error">
						<default-error>SYS_INTERNAL_ERROR</default-error>
						<bypass-error>SUCCESS</bypass-error>
						<bypass-error>INVALID_REQUEST</bypass-error>
						<bypass-error>SYS_NOT_ENOUGH_MONEY</bypass-error>
						<bypass-error>AMOUNT_TOO_BIG</bypass-error>
					</type>
					<type id="txn-error">
						<default-error>TRANSACTION_FAILED</default-error>
						<bypass-error>SUCCESS</bypass-error>
						<bypass-error>REQUEST_TIME_OUT</bypass-error>
						<bypass-error>UNSUPPORTED_NUMBER</bypass-error>
						<bypass-error>MISISDN_NOT_EXISTED</bypass-error>
						<bypass-error>SYS_NOT_ENOUGH_MONEY</bypass-error>
						<bypass-error>DELIVERY_RESPONSE_ERROR</bypass-error>
						<bypass-error>INVALID_RESPONSE</bypass-error>
						<bypass-error>UMARKET_76</bypass-error>
						<bypass-error>UMARKET_77</bypass-error>
						<bypass-error>UMARKET_78</bypass-error>
						<bypass-error>00</bypass-error>
					</type>
				</method>
				<method id="INQUIRY">
					<type id="sys-error">
						<default-error>SYS_INTERNAL_ERROR</default-error>
						<bypass-error>SUCCESS</bypass-error>
						<bypass-error>TXN_NOT_EXISTED</bypass-error>
					</type>
					<type id="txn-error">
						<default-error>TRANSACTION_FAILED</default-error>
						<bypass-error>SUCCESS</bypass-error>
						<bypass-error>REQUEST_TIME_OUT</bypass-error>
						<bypass-error>UNSUPPORTED_NUMBER</bypass-error>
						<bypass-error>MISISDN_NOT_EXISTED</bypass-error>
						<bypass-error>SYS_NOT_ENOUGH_MONEY</bypass-error>
						<bypass-error>DELIVERY_RESPONSE_ERROR</bypass-error>
						<bypass-error>INVALID_RESPONSE</bypass-error>
						<bypass-error>UMARKET_76</bypass-error>
						<bypass-error>UMARKET_77</bypass-error>
						<bypass-error>UMARKET_78</bypass-error>
						<bypass-error>00</bypass-error>
					</type>
				</method>
			</error-convert>
		</airtime>
	</gateway>
</app>','xml',NULL);

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
		    ((not exists (select c.at_txn_id from `cdr_data` c where c.created_by='MOBIVI' and c.at_txn_id=a.at_txn_id))
		     or  (exists (select d.at_txn_id from `cdr_data` d where d.created_by='MOBIVI' and d.at_txn_id=a.at_txn_id and a.status<>d.status)));
		;


