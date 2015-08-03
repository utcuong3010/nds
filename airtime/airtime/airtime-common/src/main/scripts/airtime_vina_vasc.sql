INSERT INTO `provider_amount` (`provider_id`,`total_loaded`,`total_used`,`notif_amount`,`notif_sent`) VALUES ('VASC',0,0,0,0);

INSERT INTO `provider_amount` (`provider_id`,`total_loaded`,`total_used`,`notif_amount`,`notif_sent`) VALUES ('VINA',0,0,0,0);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.serviceUrl','http://localhost:8080/vina-simulator/services/EloadFunction','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.time-out','30000','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.user-name','mobivi_36','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.password','123456','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.delivery-queue-id','VINAPHONE_DELIVERY','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.delivery-wfp','wfpVinaphoneTopupGameRequestAfterQueue','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.cdr-processor-wfp','cdrProcessorWfp','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.cdr-processor-queue-id','CDR_PROCESSSOR','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.max-seq-error','5','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.success-errorcode','0,SUCCESS','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.unknown-errorcode','DELIVERY_RESPONSE_ERROR','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vinaphone','config','settings.vinaphone.agent-msisdn','0917943157','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.serviceUrl','http://203.162.35.90/topup/qttu/index.php/vina/eload?ws=1','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.time-out','30000','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.user-name','mobivi','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.secret-key','123456','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.delivery-queue-id','VASC_DELIVERY','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.delivery-wfp','wfpVascTopupGameRequestAfterQueue','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.cdr-processor-wfp','cdrProcessorWfp','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.cdr-processor-queue-id','CDR_PROCESSSOR','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.max-seq-error','5','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.success-errorcode','0,SUCCESS','prop',NULL);

INSERT INTO `airtime_config` (`module`,`type`,`param_key`,`param_value`,`value_type`,`remote_key`) 
VALUES ('vasc','config','settings.vasc.unknown-errorcode','DELIVERY_RESPONSE_ERROR,12','prop',NULL);
