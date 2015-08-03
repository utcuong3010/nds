package com.mbv.bp.common.integration.protoc;

import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.google.protobuf.RpcController;
import com.googlecode.protobuf.socketrpc.RpcCallbackImp;
import com.googlecode.protobuf.socketrpc.SocketRpcChannel;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IntegrationProtocService;
import com.mbv.bp.common.integration.IntegrationProtocService.KeyedValue;
import com.mbv.bp.common.integration.IntegrationProtocService.Request;
import com.mbv.bp.common.integration.IntegrationProtocService.Response;

public abstract class IntegrationProtocClient implements IExecutable{
	protected static Log logger = LogFactory.getLog(IntegrationProtocClient.class);
	protected RpcController rpcController;
	protected SocketRpcChannel socketRpcChannel;
	protected IntegrationProtocService.Service myService;
	protected String ip="127.0.0.1";	
	protected int port=10002;
	protected boolean status;
	public IntegrationProtocClient(String ip,int port) {
		this.ip=ip;
		this.port=port;
	}
	@Override
	public ContextBase process(ContextBase context) {
		ContextBase ctxResult=new ContextBase();
		try{
			Request.Builder requestBuilder=Request.newBuilder();
			for(Entry<String, String> entry: context.entrySet()){
				if (StringUtils.isNotBlank(entry.getKey())&& StringUtils.isNotBlank(entry.getValue()))
					requestBuilder.addProperties(KeyedValue.newBuilder().setKey(entry.getKey()).setValue(entry.getValue()));
			}
			RpcCallbackImp<Response> callbackImp=new RpcCallbackImp<Response>();
			myService.process(rpcController, requestBuilder.build(), callbackImp);
			Response response= callbackImp.getResult();
			if (response!=null){
				if (response.getPropertiesList()!=null){
					for (KeyedValue keyedValue:response.getPropertiesList()){
						if (StringUtils.isNotBlank(keyedValue.getKey())&& StringUtils.isNotBlank(keyedValue.getValue()))
							ctxResult.put(keyedValue.getKey(),keyedValue.getValue());
					}
				}
			}else {
				throw new Exception("Fail to process request, response is null");
			}
		}catch (Exception e) {
			ctxResult.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SYS_INTERNAL_ERROR);
			logger.error(e.getMessage(),e);
		}
		return ctxResult;
	}
}
