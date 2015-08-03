ALTER TABLE `provider_account` CHANGE COLUMN `total_amount` `total_amount` BIGINT(20) NULL DEFAULT 0  , CHANGE COLUMN `input_amount` `input_amount` BIGINT(20) NULL DEFAULT 0  ;

update provider_amount b  set b.total_loaded=(select sum(total_amount) from provider_account a where a.deleted=0 and a.provider_id='VNPAY') where b.provider_id='VNPAY';
update provider_amount b set b.total_loaded=(select sum(total_amount) from provider_account a where a.deleted=0 and a.provider_id='MOBI') where b.provider_id='MOBI';

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('forward','config','forward.active-mq.pool-size','1','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('forward','config','forward.active-mq.url','tcp://10.120.6.11:61616','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('forward','config','forward.result.channel','<app>
<forward>
<result>
	<channel id="CHANNEL1"> 
		<queue-id>queue.test.channel1.result</queue-id>
		<topic-id></topic-id>
		<removable>true</removable>
	</channel>
	<channel id="CHANNEL2"> 
		<queue-id>queue.test.channel2.result</queue-id>
		<topic-id></topic-id>
		<removable>false</removable>
	</channel>
</result>	
</forward>
</app>','xml',NULL);


CREATE TABLE `reserved_telco` (
  `telco_id` varchar(50) NOT NULL,
  `reserved_amount` bigint(20) DEFAULT '0',
  `percent` int(11) DEFAULT '0',
  PRIMARY KEY (`telco_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`) VALUES ('BLVN',0,100);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`) VALUES ('EVNT',0,100);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`) VALUES ('MOBI',0,100);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`) VALUES ('SFON',0,100);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`) VALUES ('VINA',0,100);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`) VALUES ('VNMB',0,100);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`) VALUES ('VTEL',0,100);

CREATE TABLE `reserved_account` (
  `account_id` varchar(100) NOT NULL,
  `total_debit` bigint(20) DEFAULT '0',
  `total_credit` bigint(20) DEFAULT '0',
  `system_id` varchar(100) NOT NULL,
  `telco_ids` varchar(100) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `system_id_index` (`system_id`),
  KEY `created_date_index` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `reserved_txn` (
  `txn_id` varchar(100) NOT NULL,
  `account_id` varchar(100) NOT NULL,
  `system_id` varchar(100) NOT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `reference_id` varchar(100) DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`txn_id`),
  KEY `system_id_index` (`system_id`),
  KEY `account_id_index` (`account_id`),
  KEY `reference_id_index` (`reference_id`),
  KEY `created_date_index` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
