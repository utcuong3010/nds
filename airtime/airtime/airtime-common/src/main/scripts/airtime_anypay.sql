CREATE TABLE `sim_info` (
  `msisdn` varchar(20) NOT NULL,
  `com_id` varchar(10) NOT NULL,
  `sim_status` varchar(1) NOT NULL,
  `sim_type` varchar(50) NOT NULL,
  `current_amount` bigint(20) NOT NULL,
  `lock_amount` bigint(20) NOT NULL,
  `ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`msisdn`,`com_id`),
  UNIQUE KEY `com_id_UNIQUE` (`com_id`),
  UNIQUE KEY `msisdn_UNIQUE` (`msisdn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `sim_info` (`msisdn`,`com_id`,`sim_status`,`sim_type`,`current_amount`,`lock_amount`,`ip`) VALUES ('01666204861','COM31','Y','ANYPAY',0,0,NULL);
INSERT INTO `sim_info` (`msisdn`,`com_id`,`sim_status`,`sim_type`,`current_amount`,`lock_amount`,`ip`) VALUES ('0977736073','COM30','Y','ANYPAY',0,0,NULL);


CREATE TABLE `sim_transaction` (
  `txn_id` bigint(20) NOT NULL,
  `msisdn` varchar(20) DEFAULT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `txn_type` varchar(50) DEFAULT NULL,
  `txn_status` varchar(50) DEFAULT NULL,
  `txn_date` datetime DEFAULT NULL,
  `error_code` varchar(50) DEFAULT NULL,
  `message_id` varchar(50) DEFAULT NULL,
  `sim_amount` bigint(20) DEFAULT NULL,
  `lock_amount` bigint(20) DEFAULT NULL,
  `billing` varchar(1) DEFAULT NULL COMMENT 'Y, N, R',
  `sim_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`txn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `sms_content` (
  `message_id` varchar(50) NOT NULL,
  `processed` varchar(1) DEFAULT NULL,
  `msg_date` datetime DEFAULT NULL,
  `txt_content` varchar(300) DEFAULT NULL,
  `org_content` varchar(500) DEFAULT NULL,
  `sender` varchar(20) DEFAULT NULL,
  `txn_date` datetime DEFAULT NULL,
  `txn_type` varchar(50) DEFAULT NULL,
  `txn_amount` varchar(20) DEFAULT NULL,
  `txn_status` varchar(20) DEFAULT NULL,
  `sms_amount` varchar(20) DEFAULT NULL,
  `total_part` int(11) DEFAULT NULL,
  `part_no` int(11) DEFAULT NULL,
  `part_id` varchar(10) DEFAULT NULL,
  `fraud_status` varchar(1) DEFAULT NULL,
  `msisdn` varchar(20) DEFAULT NULL,
  `sim_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `anypay_account_txn` (
  `txn_id` varchar(100) NOT NULL,
  `deleted` bigint(20) unsigned NOT NULL,
  `txn_date` datetime DEFAULT NULL,
  `sim_id` varchar(50) DEFAULT NULL,
  `amount` bigint(20) DEFAULT '0',
  `employee` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`txn_id`,`deleted`),
  KEY `employee_index` (`employee`),
  KEY `txn_date` (`txn_date`)
) ENGINE=InnoDB DEFAULT CHARSET=ascii;

INSERT INTO `provider_amount` (`provider_id`,`total_loaded`,`total_used`,`notif_amount`,`notif_sent`) VALUES ('ANYPAY',0,0,0,0);

DELETE FROM `airtime_config` WHERE `module`='airtime' and `type`='config' and `param_key`='app-context-loader.airtime-launcher';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','app-context-loader.airtime-launcher','common-context.xml,workflow-context.xml,queue-context.xml,anypay-context.xml','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('anypay','config','setting.anypay.anypay-gateway.url','http://localhost:8080/sim-gateway/services/SimServices.SimServicesHttpSoap12Endpoint/','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('anypay','config','setting.anypay.sms-profile','<app>
<anypay>
<sms-profile>
	<profile-id name="VTEL_TOPUP_SUCCESS" TxnStatus="SUCCESS" TxnType="TOPUP">
		<start-with>Quy khach da nap thanh cong</start-with>
		<sender>148</sender>
		<fields>
			<field-id name="Msisdn">
				<start-with>dong cho so </start-with>
				<end-with> vao luc </end-with>
			</field-id>
			<field-id name="TxnAmount">
				<start-with>Quy khach da nap thanh cong </start-with>
				<end-with>dong cho so </end-with>
			</field-id>
			<field-id name="SimAmount">
				<start-with>So du cua Quy khach la </start-with>
				<end-with> dong</end-with>
			</field-id>
			<field-id name="TxnDate">
				<start-with>vao luc </start-with>
				<end-with>\, So du</end-with>
			</field-id>
			<field-id name="MessageId">
				<start-with>ma giao dich </start-with>
				<end-with>.</end-with>
			</field-id>
		</fields>
	</profile-id>
	<profile-id name="VTEL_TOPUP_ERROR" TxnStatus="ERROR" TxnType="TOPUP">
		<start-with>Quy khach nap tien chua thanh cong</start-with>
		<sender>148</sender>
		<fields>
			<field-id name="Msisdn">
				<start-with>cho so </start-with>
				<end-with>\,</end-with>
			</field-id>
			<field-id name="MessageId">
				<start-with>ma giao dich </start-with>
				<end-with>.</end-with>
			</field-id>
		</fields>
	</profile-id>
</sms-profile>
</anypay>
</app>','xml',NULL);


DELETE FROM `airtime_config` WHERE `module`='airtime' and `type`='config' and `param_key`='airtime.constant.airtime-provider';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','airtime.constant.airtime-provider','<app>
<airtime>
<constant>
<airtime-provider> 
	<provider id="VTEL" name="Viettel"/>
	<provider id="MOBI" name="MobiFone"/>
	<provider id="VNPAY" name="VnPay"/>
	<provider id="VIETPAY" name="VietPay"/>
    <provider id="VTC" name="VTC"/>
    <provider id="ANYPAY" name="ANYPAY"/>
</airtime-provider>
</constant>
</airtime>
</app>','xml',NULL);
