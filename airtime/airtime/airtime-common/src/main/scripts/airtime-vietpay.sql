DELETE FROM `airtime`.`airtime_config` WHERE `module`='airtime' and`type`='config' and`param_key`='airtime.constant.airtime-provider';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='airtime' and`type`='config' and`param_key`='airtime.constant.integation-gateway';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='airtime' and`type`='config' and`param_key`='airtime.constant.telco-provider';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='gateway' and`type`='config' and`param_key`='gateway.constant.error-convert';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.cdr-processor-queue-id';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.cdr-processor-wfp';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.connection-type';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.delivery-queue-id';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.delivery-wfp';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.max-seq-error';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.partner-code';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.password';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.response-time-out';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.secret-key';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.success-errorcode';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.topup-code';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.unknown-errorcode';
DELETE FROM `airtime`.`airtime_config` WHERE `module`='queue' and`type`='config' and`param_key`='queue.constant.queue-config';
/*
-- Query: select * from airtime_config where 
(`module`='airtime' and`type`='config' and`param_key`='airtime.constant.airtime-provider') or
(`module`='airtime' and`type`='config' and`param_key`='airtime.constant.integation-gateway') or
(`module`='airtime' and`type`='config' and`param_key`='airtime.constant.telco-provider') or
(`module`='gateway' and`type`='config' and`param_key`='gateway.constant.error-convert') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.cdr-processor-queue-id') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.cdr-processor-wfp') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.connection-type') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.delivery-queue-id') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.delivery-wfp') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.max-seq-error') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.partner-code') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.password') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.response-time-out') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.secret-key') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.success-errorcode') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.topup-code') or
(`module`='vietpay' and`type`='config' and`param_key`='settings.vietpay.unknown-errorcode') or
(`module`='queue' and`type`='config' and`param_key`='queue.constant.queue-config')
LIMIT 0, 1000

-- Date: 2011-12-22 00:04
*/
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','airtime.constant.airtime-provider','<?xml version="1.0" encoding="ISO-8859-1" ?>
<app>
<airtime>
<constant>
<airtime-provider> 
                <provider id="VTEL" name="Viettel"/>
	<provider id="MOBI" name="MobiFone"/>
	<provider id="VNPAY" name="VnPay"/>
                <provider id="VIETPAY" name="VietPay"/>
</airtime-provider>
</constant>
</airtime>
</app>','xml',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','airtime.constant.integation-gateway','<?xml version="1.0" encoding="ISO-8859-1" ?>
<app>
	<integation>
		<client>
			<gateway>
				<module>AirTimeAdmin</module>
                <client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>WorkFlowConnection</name>
				<host>localhost</host>
				<port>5102</port>
				<pool-size>2</pool-size>
			</gateway>
			<gateway>
				<module>AirTimeGateWay</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>WorkFlowConnection</name>
				<host>localhost</host>
				<port>5102</port>
				<pool-size>20</pool-size>
			</gateway>
			<gateway>
				<module>AirTimeGateWay</module>
				<client-connection-type>INQUIRY</client-connection-type>
				<local-enable>false</local-enable>
				<name>WorkFlowConnection</name>
				<host>localhost</host>
				<port>5102</port>
				<pool-size>40</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<name>QueueConnection</name>
				<local-enable>true</local-enable>
				<class>com.mbv.bp.queue.integration.QueueLocalExecutor</class>
				<host>localhost</host>
				<port>5101</port>
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
				<host>http://localhost:8080/airtime-simulator-0.1.0/services/UMarketSC</host>
                <port>0</port>  
				<pool-size>1</pool-size>
			</gateway>
                                                <gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>VIETPAY</name>
				<host>http://localhost:8080/airtime-simulator-0.1.0/services/TopupService</host>
                                                                <port>0</port>  
				<pool-size>1</pool-size>
			</gateway>
                <gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<local-enable>false</local-enable>
				<name>VTEL</name>
				<host>localhost</host>
				<port>6868</port>  
				<pool-size>10</pool-size>
			</gateway>
			<gateway>
				<module>Application</module>
				<client-connection-type>COMMON</client-connection-type>
				<name>WorkFlowConnection</name>
				<local-enable>true</local-enable>
				<class>com.mbv.bp.core.integration.WfpLocalExecutor</class>
				<host>localhost</host>
				<port>5102</port>
				<pool-size>50</pool-size>
			</gateway>
		</client>
	</integation>
</app>','xml',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('airtime','config','airtime.constant.telco-provider','<app>
	<airtime>
		<constant>
			<telco-provider> 
				<provider id="VTEL" name="Viettel">
					<connection-id>VTEL,VNPAY</connection-id>
				</provider>
				<provider id="MOBI" name="MobiFone">
					<connection-id>MOBI,VIETPAY,VNPAY</connection-id>
				</provider>
				<provider id="VINA" name="VinaPhone">
					<connection-id>VNPAY</connection-id>
				</provider>
				<provider id="BLVN" name="Beeline VietNam">
					<connection-id>VNPAY</connection-id>
				</provider>
				<provider id="SFON" name="S-Fone">
					<connection-id>VNPAY</connection-id>
				</provider>
				<provider id="EVNT" name="EVN Telecom">
					<connection-id>VNPAY</connection-id>
				</provider>
				<provider id="VNMB" name="Vietnam Mobile">
					<connection-id>VNPAY</connection-id>
				</provider>
			</telco-provider>
		</constant>
	</airtime>
</app>','xml',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('gateway','config','gateway.constant.error-convert','<?xml version="1.0" encoding="ISO-8859-1" ?>
<app>
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
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('queue','config','queue.constant.queue-config','<app>
	<queue>
		<constant>
			<queue-config>
				<queue id="VNPAY_DELIVERY">
					<max-queue-size>1000</max-queue-size>
					<dequeue-size>1</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>true</terminable>
					<queue-table></queue-table>
				</queue>
                                                                <queue id="VIETPAY_DELIVERY">
					<max-queue-size>1000</max-queue-size>
					<dequeue-size>1</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>true</terminable>
					<queue-table></queue-table>
				</queue>
                                                                <queue id="VTEL_DELIVERY">
					<max-queue-size>1000</max-queue-size>
					<dequeue-size>10</dequeue-size>
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
					<max-queue-size>10000</max-queue-size>
					<dequeue-size>30</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>false</terminable>
					<queue-table></queue-table>
				</queue>
				<queue id="CDR_PROCESSSOR">
					<max-queue-size>10000</max-queue-size>
					<dequeue-size>30</dequeue-size>
					<max-dequeue-retry>0</max-dequeue-retry>
					<terminable>false</terminable>
					<queue-table></queue-table>
                                                               </queue>
			</queue-config>
		</constant>
	</queue>
</app>','xml',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.cdr-processor-queue-id','CDR_PROCESSSOR','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.cdr-processor-wfp','cdrProcessorWfp','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.connection-type','VIETPAY','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.delivery-queue-id','VIETPAY_DELIVERY','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.delivery-wfp','wfpVietPayTopupRequestAfterQueue','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.max-seq-error','5','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.partner-code','100133829','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.password','112233','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.response-time-out','30000','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.secret-key','112233','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.success-errorcode','1,01','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.topup-code','MOBI:VMS,VTEL:VIETTEL,SFON:SFONE,VINA:VINAPHONE,VNMB:VNMOBILE,BLVN:BEELINE','prop',NULL);
INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) VALUES ('vietpay','config','settings.vietpay.unknown-errorcode','26,99','prop',NULL);

DELETE FROM `provider_amount` WHERE `provider_id`='VIETPAY';
INSERT INTO `provider_amount` (`provider_id`,`total_loaded`,`total_used`,`notif_amount`,`notif_sent`) VALUES ('VIETPAY',0,0,0,0);