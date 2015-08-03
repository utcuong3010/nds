package com.mbv.airtime2.integration;

import java.util.Map.Entry;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.integration.IntegrationProto.KeyedValue;
import com.mbv.airtime2.integration.IntegrationProto.ResType;
import com.mbv.airtime2.integration.RpcProto.Response.ServerErrorReason;

public class ProtoTransformer {

	
	public IntegrationPayload toIntegrationPayload(RpcProto.Request rpcRequest){
		IntegrationPayload payload = new IntegrationPayload();
		
		if(rpcRequest.hasRequestProto()){
			IntegrationProto.Request.Builder builder = IntegrationProto.Request.newBuilder();
			try {
				builder.mergeFrom(rpcRequest.getRequestProto());
			} catch (InvalidProtocolBufferException e) {
			}
			if(builder.isInitialized()){
				for(KeyedValue kv : builder.getPropertiesList()){
					payload.put(kv.getKey(), kv.getValue());
				}	
			}
		}
		return payload;
	}
	
	public IntegrationPayload toIntegrationPayload(RpcProto.Response rpcResponse){
		IntegrationPayload payload = new IntegrationPayload();
		
		if(rpcResponse.hasResponseProto()){
			IntegrationProto.Response.Builder builder = IntegrationProto.Response.newBuilder();
			try {
				builder.mergeFrom(rpcResponse.getResponseProto());
			} catch (InvalidProtocolBufferException e) {
			}
			if(builder.isInitialized()){
				for(KeyedValue kv : builder.getPropertiesList()){
					payload.put(kv.getKey(), kv.getValue());
				}	
			}
		}else{
			payload.put("error", rpcResponse.getError());
			payload.put("error_reason", rpcResponse.getErrorReason().toString());
		}
		return payload;
	}

	public RpcProto.Response toRpcProtoResponse(IntegrationPayload payload){
		IntegrationProto.Response.Builder intBuilder = IntegrationProto.Response.newBuilder();
		intBuilder.setType(ResType.SUCCESS);
		for(Entry<String,String> entry : payload.entrySet()){
			intBuilder.addProperties(KeyedValue.newBuilder().setKey(entry.getKey()).setValue(entry.getValue()).build());
		}
		
		return RpcProto.Response.newBuilder()
				.setCallback(true)
				.setResponseProto(intBuilder.build().toByteString())
				.build();
	}
	
	public RpcProto.Response createRpcProtoResponse(boolean isSuccess){
		RpcProto.Response.Builder builder = RpcProto.Response.newBuilder();
		
		if(isSuccess){
			IntegrationProto.Response responseProto = IntegrationProto.Response.newBuilder()
				.setType(ResType.SUCCESS)
				.addProperties(KeyedValue.newBuilder().setKey("error_code").setValue("SUCCESS").build())
				.build();
			builder.setResponseProto(responseProto.toByteString())
				.setCallback(true);
		}else{
			builder.setError("RPC_FAILED")
				.setCallback(true)
				.setErrorReason(ServerErrorReason.RPC_FAILED);
		}
		return builder.build();
	}
	
	public RpcProto.Request toRpcProtoRequest(String serviceName, String methodName, IntegrationPayload payload){
		IntegrationProto.Request.Builder intBuilder = IntegrationProto.Request.newBuilder();		
		for(Entry<String,String> entry : payload.entrySet()){
			intBuilder.addProperties(KeyedValue.newBuilder().setKey(entry.getKey()).setValue(entry.getValue()).build());
		}
		
		return RpcProto.Request.newBuilder()
				.setServiceName(serviceName)
				.setMethodName(methodName)
				.setRequestProto(intBuilder.build().toByteString())
				.build();
	}
}