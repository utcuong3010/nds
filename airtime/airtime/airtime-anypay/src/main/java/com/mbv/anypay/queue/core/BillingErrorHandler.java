package com.mbv.anypay.queue.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.airtime.manager.AmountManager;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.core.workflow.Handler;

public class BillingErrorHandler extends Handler{
	private static Log log=LogFactory.getLog(BillingErrorHandler.class);
	/*
	 * Please, dont modify any property in this context
	 * 
	 */
	@Override
	public boolean handle(ContextBase context, Exception exception) {
		boolean result=true;
		try{
			log.info("Going to process error handle for workflow - " +context.get(Attributes.ATTR_WFP_NAME));
			String errorCode=ErrorCode.SUCCESS;
			AmountManager amountManager=new AmountManager();
			/*
			 * relock transaction
			 */
			try{
				if (AppConstants.YES_FLAG.equalsIgnoreCase(context.get(Attributes.ATTR_RESERVED_BILLING))){
					String seq=IdGeneratorFactory.getInstance().getAirTimeIdGenentor().generateId();
					errorCode=amountManager.debitLockAccount(
							AppConstants.AT_TXN_OPERATION,
							AppUtils.getAtTxnId(context.getDate(Attributes.ATTR_TRANSACTION_DATE), seq), 
							context.get(Attributes.ATTR_CHANNEL_ID), 
							context.get(Attributes.ATTR_RESERVED_ID), 
							context.get(Attributes.ATTR_TRANSACTION_ID), 
							context.getLong(Attributes.ATTR_AMOUNT), 
							"ERROR");
					
					if (!ErrorCode.SUCCESS.equalsIgnoreCase(errorCode)){
						throw new Exception("Fail to process debit");
					}
				}
			}catch (Exception e) {
				result=false;
				log.error("Fail to process relock|context : "+context,e);
			}
			/*
			 * refund provider account.
			 */
			errorCode=ErrorCode.SUCCESS;
			try{
				if (AppConstants.YES_FLAG.equalsIgnoreCase(context.get(Attributes.ATTR_PROVIDER_BILLING))){
					errorCode=amountManager.providerRefund(context.get(Attributes.ATTR_CONN_TYPE),context.getLong(Attributes.ATTR_AMOUNT));
					if (!ErrorCode.SUCCESS.equalsIgnoreCase(errorCode)){
						throw new Exception("Fail to process refund");
					}
				}
			}catch (Exception e) {
				result=false;
				log.error("Fail to process refund|context : "+context,e);
			}
		}catch (Exception e) {
			log.error("Fail to Handle Error.| ErrMsg-"+e.getMessage(),e);
			result=false;
		}
		return result;
	}

}
