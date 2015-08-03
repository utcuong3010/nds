package com.mbv.bp.common.settings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.NotifTemplate;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.vo.airtime.CdrSync;


public class NotificationSettings implements ISettings{
	private static Log log = LogFactory.getLog(NotificationSettings.class);
	public enum TEMPLATE_TYPE{
		MOBI_ERROR_TEMPLATE,
		MOBI_SEQ_ERROR_TEMPLATE,
		SEQ_ERROR_TEMPLATE,
		CDR_COMPARE_STATUS_TEMPLATE,
		UNKNOW_TXN_STATUS_TEMPLATE,
//		CONNECTION_STATUS_TEMPLATE,
		PROVIDER_AMOUNT_ALERT
	}
	private static int emailPort; 
	private static String emailHost;
	private static boolean logEmailEnable;
	private static Map<TEMPLATE_TYPE, NotifTemplate> notifTemplates=null;
	
	@Override
	public void load(CompositeConfiguration config) {
		if (notifTemplates==null)
			notifTemplates=new ConcurrentHashMap<TEMPLATE_TYPE, NotifTemplate>();
		notifTemplates.clear();
		emailPort=config.getInt("settings.notification.email-port",25);
		emailHost=config.getString("settings.notification.email-host","10.40.111.55");
		logEmailEnable=config.getBoolean("settings.notification.log-email-enable",false);
		notifTemplates.put(TEMPLATE_TYPE.MOBI_ERROR_TEMPLATE, getNotifTemplate("notification.mobi-err",config));
		notifTemplates.put(TEMPLATE_TYPE.MOBI_SEQ_ERROR_TEMPLATE, getNotifTemplate("notification.mobi-seq-err",config));
		notifTemplates.put(TEMPLATE_TYPE.SEQ_ERROR_TEMPLATE, getNotifTemplate("notification.seq-err",config));
		notifTemplates.put(TEMPLATE_TYPE.CDR_COMPARE_STATUS_TEMPLATE, getNotifTemplate("notification.comparison-status",config));
		notifTemplates.put(TEMPLATE_TYPE.UNKNOW_TXN_STATUS_TEMPLATE, getNotifTemplate("notification.unknown-txn-status",config));
//		notifTemplates.put(TEMPLATE_TYPE.CONNECTION_STATUS_TEMPLATE, getNotifTemplate("notification.conn-status",config));
		notifTemplates.put(TEMPLATE_TYPE.PROVIDER_AMOUNT_ALERT, getNotifTemplate("notification.not-enough-money",config));
		
		log.info("emailHost:"+emailHost);
		log.info("emailPort:"+emailPort);
		log.info(notifTemplates);
		log.info("logEmailEnable:"+logEmailEnable);
	}
	
	private NotifTemplate getNotifTemplate(String key,CompositeConfiguration config){
		NotifTemplate template=new NotifTemplate();
    	template.setTemplateName(key);
    	template.setFrom(config.getString(key+".from"));
    	template.setTo(config.getString(key+".to"));
    	template.setCc(config.getString(key+"cc"));
    	template.setBcc(config.getString(key+"bcc"));
    	template.setSubject(config.getString(key+".subject"));
    	template.setContent(config.getString(key+".content"));
    	return template;
	}

	public String customerToString() {
		return "";
	}
	
	public static NotifTemplate getNotification(TEMPLATE_TYPE notifKey) {
		try{
			NotifTemplate template=notifTemplates.get(notifKey);
			if (template!=null)
				return (NotifTemplate)BeanUtilsBean.getInstance().cloneBean(template);
			else 
				return null;
		}catch (Exception e) {
			log.error("Fail to get notification template.",e);
			return null;
		}
	}
	
	public boolean sendNotification(TEMPLATE_TYPE notifKey, Object ...object){
		try{
			switch (notifKey) {
//			case CONNECTION_STATUS_TEMPLATE:
//				return connectionStatusNotification(notifKey,(String)object[0],(Boolean)object[1]);
			case UNKNOW_TXN_STATUS_TEMPLATE:
				return unknownTxnStatusNotification(notifKey,(CdrSync)object[0]);
			case SEQ_ERROR_TEMPLATE:
				return seqErrorNotification(notifKey,(Map<Long, CdrSync>)object[0]);
			case MOBI_SEQ_ERROR_TEMPLATE:
				return mobiErrorNotification(notifKey,(Map<Long, CdrSync>)object[0]);
			case MOBI_ERROR_TEMPLATE:
				return mobiErrorNotification(notifKey,(Map<Long, CdrSync>)object[0]);	
			case CDR_COMPARE_STATUS_TEMPLATE:
				return cdrCompareStatusNotification(notifKey,(ContextBase)object[0]);
			case PROVIDER_AMOUNT_ALERT:
				return providerAmountAlert(notifKey,(ContextBase)object[0]);
			default:
				break;
			}
			return false;
		}catch (Exception e) {
			log.error("Unexpected error found.",e);
			return false;
		}
	}
	
	private boolean providerAmountAlert(TEMPLATE_TYPE notifKey,	ContextBase context) {
		try{
			NotifTemplate template=NotificationSettings.getNotification(notifKey);
			String subject=AppUtils.prepareContentFromTemplate(context,template.getSubject());
			String content=AppUtils.prepareContentFromTemplate(context,template.getContent());
			AppUtils.sendEmail(emailHost, emailPort, template.getFrom(), template.getTo(), template.getCc(), template.getBcc(), subject, content);
		}catch (Exception e) {
			log.error("Fail to send provider amount alert.| context:"+context,e);
			return false;
		}
		return true;
	}

	private boolean cdrCompareStatusNotification(TEMPLATE_TYPE notifKey,ContextBase context) {
		try{
			NotifTemplate template=NotificationSettings.getNotification(notifKey);
			String subject=AppUtils.prepareContentFromTemplate(context,template.getSubject());
			String content=AppUtils.prepareContentFromTemplate(context,template.getContent());
			AppUtils.sendEmail(emailHost, emailPort, template.getFrom(), template.getTo(), template.getCc(), template.getBcc(), subject, content);
		}catch (Exception e) {
			log.error("Fail to send notification for conparison status.| context:"+context,e);
			return false;
		}
		return true;
	}

	private boolean unknownTxnStatusNotification(TEMPLATE_TYPE notifKey,CdrSync cdrSync) {
		ContextBase context=new ContextBase();
		try{
			context.put("provider_id", cdrSync.getProviderId());
			context.put("msisdn", cdrSync.getMsisdn());
			context.put("at_txn_id", String.valueOf(cdrSync.getAtTxnId()));
			context.put("status", cdrSync.getStatus());
			NotifTemplate template=NotificationSettings.getNotification(notifKey);
			String subject=AppUtils.prepareContentFromTemplate(context,template.getSubject());
			String content=AppUtils.prepareContentFromTemplate(context,template.getContent());
			AppUtils.sendEmail(emailHost, emailPort, template.getFrom(), template.getTo(), template.getCc(), template.getBcc(), subject, content);
		}catch (Exception e) {
			log.error("Fail to send notification for connection status.| context:"+context,e);
			return false;
		}
		return true;
	}


	private boolean connectionStatusNotification(TEMPLATE_TYPE notifKey,String connType,boolean status){
		ContextBase context=new ContextBase();
		try{
			context.put("provider_id", connType);
			if (status){
				context.put("status", "active");
			}else{
				context.put("status", "suspend");
			}
			NotifTemplate template=NotificationSettings.getNotification(notifKey);
			String subject=AppUtils.prepareContentFromTemplate(context,template.getSubject());
			String content=AppUtils.prepareContentFromTemplate(context,template.getContent());
			AppUtils.sendEmail(emailHost, emailPort, template.getFrom(), template.getTo(), template.getCc(), template.getBcc(), subject, content);
		}catch (Exception e) {
			log.error("Fail to send notification for connection status.| context:"+context,e);
			return false;
		}
		return true;
	}
	
	private boolean seqErrorNotification(TEMPLATE_TYPE notifKey, Map<Long, CdrSync> cdrMap ){
		ContextBase context=new ContextBase();
		try{
			String detail="";
			int i=0;
			CdrSync cdrSync;	
			String providerId="";
			for (Long key:cdrMap.keySet()){
				cdrSync=cdrMap.get(key);
				providerId=cdrSync.getProviderId();
				if (cdrSync!=null){
					i++;
					detail=detail+i+". "+
									 cdrSync.getMsisdn()+","+
									 cdrSync.getAmount()+","+
									 cdrSync.getMessageId()+","+
									 cdrSync.getErrorCode()+","+
									 cdrSync.getStatus()+","+
									 cdrSync.getAtTxnId()+ "<br>";
				}
			}
			context.put("provider_id", providerId);
			context.put("detail", detail);
			NotifTemplate template=NotificationSettings.getNotification(notifKey);
			String subject=AppUtils.prepareContentFromTemplate(context,template.getSubject());
			String content=AppUtils.prepareContentFromTemplate(context,template.getContent());
			AppUtils.sendEmail(emailHost, emailPort, template.getFrom(), template.getTo(), template.getCc(), template.getBcc(), subject, content);
		}catch (Exception e) {
			log.error("Fail to send seq error notification| context:"+context,e);
			return false;
		}
		return true;
	}
	
	private boolean mobiErrorNotification(TEMPLATE_TYPE notifKey, Map<Long, CdrSync> cdrMap ){
		ContextBase context=new ContextBase();
		try{
			String detail="";
			int i=0;
			CdrSync cdrSync;	
			String providerId="";
			for (Long key:cdrMap.keySet()){
				cdrSync=cdrMap.get(key);
				providerId=cdrSync.getProviderId();
				if (cdrSync!=null){
					i++;
					detail=detail+i+". "+cdrSync.getMessageId()+","+
										 DateUtils.convertDate2String(cdrSync.getTxnDate(), "GMT+7", "dd/MM/yyyy HH:mm:ss")+","+
										 cdrSync.getMsisdn()+","+
										 cdrSync.getAmount()+","+
										 cdrSync.getErrorCode()+","+
										 cdrSync.getAtTxnId()+ "<br>";
				}
			}
			context.put("provider_id", providerId);
			context.put("detail", detail);
			NotifTemplate template=NotificationSettings.getNotification(notifKey);
			String subject=AppUtils.prepareContentFromTemplate(context,template.getSubject());
			String content=AppUtils.prepareContentFromTemplate(context,template.getContent());
			AppUtils.sendEmail(emailHost, emailPort, template.getFrom(), template.getTo(), template.getCc(), template.getBcc(), subject, content);
		}catch (Exception e) {
			log.error("Fail to send Mobi error notification| context:"+context,e);
			return false;
		}
		return true;
	}

	public boolean isLogEmail() {
		return logEmailEnable;
	}
	

}
