update  `airtime`.`provider_amount` set total_used=total_used+(select sum(ext2) from airtime.cdr_data where created_by='VIETPAY')  where provider_id='VIETPAY';

ALTER TABLE `airtime`.`at_transaction` ADD COLUMN `server_id` VARCHAR(100) NULL DEFAULT NULL  AFTER `msisdn` , ADD COLUMN `txn_type` VARCHAR(45) NULL DEFAULT NULL  AFTER `conn_type` , CHANGE COLUMN `msisdn` `msisdn` VARCHAR(100) NULL DEFAULT NULL  ;

INSERT INTO `provider_amount` (`provider_id`,`total_loaded`,`total_used`,`notif_amount`,`notif_sent`) VALUES ('VTC',0,0,70000000,0);

CREATE TABLE `cdr_sync` (
  `at_txn_id` bigint(20) NOT NULL DEFAULT '0',
  `message_id` varchar(50) NOT NULL DEFAULT '',
  `provider_id` varchar(50) DEFAULT NULL,
  `txn_date` datetime DEFAULT NULL,
  `msisdn` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `error_code` varchar(100) DEFAULT NULL,
  `status` varchar(45) NOT NULL DEFAULT '',
  `p_txn_id` varchar(50) NOT NULL DEFAULT '',
  `p_price` int(11) DEFAULT NULL,
  `p_status` varchar(45) NOT NULL DEFAULT '',
  `p_error_code` varchar(45) DEFAULT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`at_txn_id`,`message_id`,`p_txn_id`),
  KEY `at_txn_id_index` (`at_txn_id`),
  KEY `at_txn_id_provider_id_index` (`at_txn_id`,`provider_id`),
  KEY `message_id_provider_id_index` (`provider_id`,`message_id`),
  KEY `txn_date` (`txn_date`),
  KEY `status_pstatus` (`status`,`p_status`),
  KEY `p_txn_id_index` (`p_txn_id`),
  KEY `msisdn_index` (`msisdn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO airtime.cdr_sync
(`at_txn_id`,
`message_id`,
`provider_id`,
`txn_date`,
`msisdn`,
`amount`,
`error_code`,
`status`,
`p_txn_id`,
`p_price`,
`p_status`,
`p_error_code`,
`ext`
)
SELECT
b.`at_txn_id`,
ifnull(b.`message_id`,''),
b.`conn_type`,
b.`txn_date`,
b.`msisdn`,
b.`amount`,
b.`error_code`,
b.`txn_status`,
'',
null,
'',
null,
null
FROM `airtime`.`at_transaction` b where (b.`conn_type`='MOBI' or b.`conn_type`='VIETPAY') and b.deleted=0;

UPDATE airtime.cdr_sync a, airtime.cdr_data b
SET
a.p_txn_id = b.message_id,
a.p_status = b.status,
a.p_error_code = b.error_code
WHERE 
b.provider_id='MOBI'
and
b.created_by='MOBIFONE'
and 
a.message_id=b.message_id;

INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('AS_THIENTU',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('AS_PLAYPARK',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('AS_RAGNAROK',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('AS_HUANGYI',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('AS_DBGH',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('SGT_CHIENQUOC',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('SGT_SHAIYA',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('SGT_ZERO',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('SGT_LINHGIOI',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('SGT_THONGLINH',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('VDC_SILK',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('VTC_VCOIN',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('DECO_COLONG',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('PHONGVAN',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('ONCASH',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('GATE',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('ZING_XU',0,0);
INSERT INTO `reserved_telco` (`telco_id`,`reserved_amount`,`percent`)VALUES('NETGAME',0,0);


DELETE FROM `airtime_config` WHERE `module`='cdrsync' and `type`='cacher' and `param_key`='mobifone.last-at_txn_id';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('cdrsync','cacher','mobifone.last-at_txn_id','201203200000215421','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='cdrsync' and `type`='cacher' and `param_key`='vietpay.last-at_txn_id';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('cdrsync','cacher','vietpay.last-at_txn_id','201203200000797234','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='cdrsync' and `type`='cacher' and `param_key`='vietpay.last-import-date-game';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('cdrsync','cacher','vietpay.last-import-date-game','20120306054500','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='mobifone' and `type`='config' and `param_key`='settings.mobifone.local-backup-path';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('mobifone','config','settings.mobifone.local-backup-path','d:/opt/mobifone_bk','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and `type`='config' and `param_key`='settings.vietpay.game-servers';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.game-servers','ZERO_1:1:Taurus,ZERO_12:12:Athena,ZERO_13:13:Oracle,LINHGIOI_2:2:Poseidon,THONGLINH_2:2:Vuong_Lang,THONGLINH_7:7:Luong_Truong','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and `type`='config' and `param_key`='settings.vietpay.vietpay-local-backup-path';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.vietpay-local-backup-path','D:/opt/backup','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.cdr-processor-queue-id';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.cdr-processor-queue-id','CDR_PROCESSSOR','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.cdr-processor-wfp';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.cdr-processor-wfp','cdrProcessorWfp','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.delivery-queue-id';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.delivery-queue-id','VTC_DELIVERY','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.delivery-wfp';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.delivery-wfp','wfpVtcTopupGameRequestAfterQueue','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.error-errorcode';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.error-errorcode','-55,-302,-303,-304,-305,-306,-307,-308,-309,-310,-311,-313,-315,-316,-317,-318,-320,-348,-350,-500,-501','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.max-seq-error';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.max-seq-error','5','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.mobivi-private-key';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.mobivi-private-key','MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL3hHTS4XDZOAszHhECut84zgxOuSCwN9ZOBXMegEdqPIOlUHdPGe3AxoLDZpjvp12wAlbKBsrP98s++FQI6yX2SAjUQdf7OZwlZJCqykHFTbt/mf9ihs2k9s5HkZMk7HHZHOT8wOIMmAtz2ZvdTIzo6KcQ/xyLKbvxYY4LJYWWNAgMBAAECgYBANC479Vq4wggQViZR+MIN5z0bGoMs4kt3ZPaKSYW/8UWfn+G2ChcTCLTdF7st5xQAYPI9Ob3DPssrk4pnBpm6VaJXY10rCo+gmGQhvc4LKVG37fCg+uATRIsNLDVwu2JxTFgo1Psyha6GY03DU0siAFQML6oUmsx4MtepV8hpwQJBAPqM08GFRqjSKznG/HM9Jwr0qd01YEv1pBlkg7fcB7tUrzQ6UEaFaLmtsFb0xDpv/qv8GEm2Ta+1+K9DRUSzgKsCQQDCAnIQCvwT5hE62aTkBKFw/iuweFJ+w1YvmnMC6mKq/uXFcIxtcTbgxK0/pvyOpPBhAhcfxEKoHGwaEDQZFWKnAkEAi4GcarWV2WxkqyAT8uqK8bu3VTdiLglRXN4txVMbbwBBKdiKWCnyXOjMNi7FkDBJ4mNU9r4uVXcCSDwxtoYoTwJAQDfJA7BvIjMMTvuNzgAOZDVtxrr9K4KC+7zXBwcIY+t9qO4JPYy1Co9vfVtLy/eiramgd95h5f2KdtIYPJlMjwJBAI0j+IIm8PFCYN13PpiUkTDjADEUN449Z87uvLO/XMKPqnlJCBk8p4P6mCDOeKZewBDQGwKg8osJVxgcJRQhB/Y=','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.partner-code';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.partner-code','MOBIVI2012','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.response-time-out';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.response-time-out','30000','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.success-errorcode';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.success-errorcode','1','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.unknown-errorcode';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.unknown-errorcode','-1,DELIVERY_RESPONSE_ERROR','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.vcoin-service-code';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.vcoin-service-code','VTC0115','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='vtc' and `type`='config' and `param_key`='settings.vtc.vtc-public-key';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vtc','config','settings.vtc.vtc-public-key','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfa12iA1BZDCehCDraOvRlEGgrkYXKLQZDmDP28k74nvdOpK6bp410BtGVrZaf1TXPQEgVibuyULSQ7ZYH+CjMJLu/IpQAYjdYjkckzHtDJpvIfaaJcDtysxPYKUkUvZejVKTWbij+ruW8BUGKjmFHj7W3wlgh7bsFwTTo1KoQNQIDAQAB','prop',NULL);

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
</airtime-provider>
</constant>
</airtime>
</app>','xml',NULL);

DELETE FROM `airtime_config` WHERE `module`='airtime' and `type`='config' and `param_key`='airtime.constant.integation-gateway';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','airtime.constant.integation-gateway','<app>
	<integation>
		<client>
			<gateway>
				<module>AirTimeAdmin</module>
                <client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>WorkFlowConnection</name>
				<host>localhost</host>
				<port>5002</port>
				<pool-size>2</pool-size>
			</gateway>
			<gateway>
				<module>AirTimeGateWay</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>WorkFlowConnection</name>
				<host>localhost</host>
				<port>5002</port>
				<pool-size>10</pool-size>
			</gateway>
			<gateway>
				<module>AirTimeGateWay</module>
				<client-connection-type>INQUIRY</client-connection-type>
				<local-enable>false</local-enable>
				<name>WorkFlowConnection</name>
				<host>localhost</host>
				<port>5002</port>
				<pool-size>10</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<name>QueueConnection</name>
				<local-enable>true</local-enable>
				<class>com.mbv.bp.queue.integration.QueueLocalExecutor</class>
				<host>localhost</host>
				<port>5001</port>
				<pool-size>50</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>VNPAY</name>
				<host>localhost</host>
				<port>1010</port>  
				<pool-size>1</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>MOBI</name>
				<host>http://localhost:8080/airtime-simulator/services/UMarketSC</host>
               	<port>0</port>  
				<pool-size>1</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<name>WorkFlowConnection</name>
				<local-enable>true</local-enable>
				<class>com.mbv.bp.core.integration.WfpLocalExecutor</class>
				<host>localhost</host>
				<port>5002</port>
				<pool-size>50</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>VIETPAY</name>
				<!--host>http://service.vtopup.vn:9006/TopupGateService</host-->
                <host>http://localhost:8080/airtime-simulator/services/TopupService</host>
				<port>0</port>  
				<pool-size>1</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>VTC</name>
			    <host>http://sandbox2.vtcebank.vn/paygate/WS/GoodsPaygate.asmx</host>
				<port>0</port>  
				<pool-size>1</pool-size>
			</gateway>
		</client>
	</integation>
</app>','xml',NULL);

DELETE FROM `airtime_config` WHERE `module`='airtime' and `type`='config' and `param_key`='airtime.constant.telco-provider';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','airtime.constant.telco-provider','<app>
<airtime>
<constant>
	<telco-provider> 
		<provider id="VTEL"  group="TELCO" name="Viettel">
			<connection-id>VIETPAY,VNPAY</connection-id>
		</provider>
		<provider id="MOBI"  group="TELCO"  name="MobiFone">
			<connection-id>MOBI,VIETPAY,VNPAY</connection-id>
		</provider>
		<provider id="VINA"  group="TELCO" name="VinaPhone">
			<connection-id>VIETPAY,VNPAY</connection-id>
		</provider>
		<provider id="BLVN"  group="TELCO" name="Beeline VietNam">
			<connection-id>VIETPAY,VNPAY</connection-id>
		</provider>
		<provider id="SFON" group="TELCO" name="S-Fone">
			<connection-id>VIETPAY,VNPAY</connection-id>
		</provider>
		<provider id="EVNT" group="TELCO"  name="EVN Telecom">
			<connection-id>VIETPAY,VNPAY</connection-id>
		</provider>
		<provider id="VNMB"  group="TELCO" name="Vietnam Mobile">
			<connection-id>VIETPAY,VNPAY</connection-id>
		</provider>
		<provider id="AS_THIENTU" group="GAME" name="Thien Tu">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="AS_PLAYPARK" group="GAME" name="PlayPark" >
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="AS_RAGNAROK" group="GAME" name="Ragnarok">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="AS_HUANGYI" group="GAME" name="Huangyi">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="AS_DBGH" group="GAME" name="Doc Ba Giang Ho">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="SGT_CHIENQUOC" group="GAME" name="Chien Quoc">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="SGT_SHAIYA" group="GAME" name="Shaiya">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="SGT_ZERO" group="GAME" name="Zero">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="SGT_LINHGIOI" group="GAME" name="Linh Gioi">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="SGT_THONGLINH"  group="GAME" name="Thong Linh">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="VDC_SILK"  group="GAME" name="Silk">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="VTC_VCOIN"  group="GAME" name="VCoin">
			<connection-id>VTC,VIETPAY</connection-id>
		</provider>
		<provider id="DECO_COLONG" group="GAME" name="Co Long">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="PHONGVAN"  group="GAME" name="Phong Van">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="ONCASH"  group="GAME" name="OnCash">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="GATE"  group="GAME" name="Gate">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="ZING_XU" group="GAME" name="ZING XU">
			<connection-id>VIETPAY</connection-id>
		</provider>
		<provider id="NETGAME" group="GAME" name="(M-CASH)NETGAME">
			<connection-id>VIETPAY</connection-id>
		</provider>
	</telco-provider>
</constant>
</airtime>
</app>','xml',NULL);

DELETE FROM `airtime_config` WHERE `module`='airtime' and `type`='config' and `param_key`='app-context-loader.airtime-launcher';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','app-context-loader.airtime-launcher','common-context.xml,workflow-context.xml,queue-context.xml','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='cdrsync' and `type`='cacher' and `param_key`='vietpay.last-import-date';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('cdrsync','cacher','vietpay.last-import-date','0','prop',NULL);

DELETE FROM `airtime_config` WHERE `module`='queue' and `type`='config' and `param_key`='queue.constant.queue-config';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('queue','config','queue.constant.queue-config','<app>
	<queue>
		<constant>
			<queue-config>
				<queue id="VNPAY_DELIVERY">
					<max-queue-size>500</max-queue-size>
					<dequeue-size>1</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>true</terminable>
					<queue-table></queue-table>
				</queue>
                 <queue id="VIETPAY_DELIVERY">
					<max-queue-size>500</max-queue-size>
					<dequeue-size>1</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>true</terminable>
					<queue-table></queue-table>
				</queue>
                <queue id="VTC_DELIVERY">
					<max-queue-size>500</max-queue-size>
					<dequeue-size>1</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>true</terminable>
					<queue-table></queue-table>
				</queue>
                 <queue id="VTEL_DELIVERY">
					<max-queue-size>500</max-queue-size>
					<dequeue-size>1</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>true</terminable>
					<queue-table></queue-table>
				</queue>
				<queue id="MOBI_DELIVERY">
					<max-queue-size>500</max-queue-size>
					<dequeue-size>1</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>true</terminable>
					<queue-table></queue-table>
				</queue>
				<queue id="AT_DN_PROCESSSOR">
					<max-queue-size>1000</max-queue-size>
					<dequeue-size>30</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>false</terminable>
					<queue-table></queue-table>
				</queue>
				<queue id="CDR_PROCESSSOR">
					<max-queue-size>1000</max-queue-size>
					<dequeue-size>30</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>false</terminable>
					<queue-table></queue-table>
                </queue>
			</queue-config>
		</constant>
	</queue>
</app>','xml',NULL);

DELETE FROM `airtime_config` WHERE `module`='vietpay' and `type`='config' and `param_key`='settings.vietpay.topup-code';
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.topup-code','MOBI:VMS,VTEL:VIETTEL,SFON:SFONE,VINA:VINAPHONE,VNMB:VNMOBILE,BLVN:BEELINE,AS_TAMQUOCCHI:AS_TAMQUOCCHI,AS_THIENTU:AS_THIENTU,AS_PLAYPARK:AS_PLAYPARK,AS_RAGNAROK:AS_RAGNAROK,AS_HUANGYI:AS_HUANGYI,AS_DBGH:AS_DBGH,AS_CABAL:AS_CABAL,SGT_CHIENQUOC:SGT_CHIENQUOC,SGT_SHAIYA:SGT_SHAIYA,SGT_ZERO:SGT_ZERO,SGT_LINHGIOI:SGT_LINHGIOI,SGT_THONGLINH:SGT_THONGLINH,VDC_SILK:VDC_SILK,VTC_VCOIN:VTC_VCOIN,DECO_COLONG:DECO_COLONG,PHONGVAN:PHONGVAN,ONCASH:ONCASH,LOTO365:LOTO365,TRUONGVN:TRUONGVN,GATE:GATE,ZING_XU:ZING_XU,NETGAME:NETGAME','prop',NULL);
