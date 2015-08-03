package com.mbv.airtime2.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import com.mbv.airtime2.integration.RpcProto.Response;

public class RpcResponseSerializer implements Serializer<RpcProto.Response>, Deserializer<RpcProto.Response> {

	//implements Serializer<RpcProto.Response>, Deserializer<RpcProto.Response>
	public Response deserialize(InputStream input) throws IOException {
		RpcProto.Response.Builder builder = RpcProto.Response.newBuilder();
		builder.mergeDelimitedFrom(input);
		if(!builder.isInitialized()) throw new IOException("RpcResponse Deserializer Error");
		return builder.build();
	}

	public void serialize(RpcProto.Response response, OutputStream output) throws IOException {
		response.writeDelimitedTo(output);
	}

}