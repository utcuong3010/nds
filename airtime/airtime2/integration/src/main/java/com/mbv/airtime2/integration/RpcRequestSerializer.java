package com.mbv.airtime2.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import com.mbv.airtime2.integration.RpcProto.Request;

public class RpcRequestSerializer implements Serializer<RpcProto.Request>, Deserializer<RpcProto.Request> {

	public Request deserialize(InputStream input) throws IOException {
		RpcProto.Request.Builder rpcBuilder = RpcProto.Request.newBuilder();
		rpcBuilder.mergeDelimitedFrom(input);
		if(!rpcBuilder.isInitialized()) throw new IOException("RpcRequest Deserializer Error");
		return rpcBuilder.build();
	}

	public void serialize(RpcProto.Request request, OutputStream output) throws IOException {
		request.writeDelimitedTo(output);
	}
}