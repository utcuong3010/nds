package com.mbv.bp.common.executor.client;

import com.googlecode.protobuf.socketrpc.SocketRpcChannel;
import com.mbv.bp.common.integration.IntegrationProtocService;
import com.mbv.bp.common.integration.protoc.IntegrationProtocClient;

public class AnypayQueueConnection extends IntegrationProtocClient// , IIntegration
{
	public AnypayQueueConnection(String ip, int port) {
		super(ip, port);
	}

	public void connect() throws Exception {
		try {
			socketRpcChannel = new SocketRpcChannel(ip, port);
			rpcController = socketRpcChannel.newRpcController();
			myService = IntegrationProtocService.Service.newStub(socketRpcChannel);
			status = true;
		} catch (Exception e) {
			status = false;
			throw e;
		}
	}

	public void disconnect() {
	}

	@Override
	public void destroy() {
		disconnect();

	}

	@Override
	public void reset() throws Exception {
		logger.info("recover lost connection...");
		try {
			connect();
			status = true;
		} catch (Exception e) {
			status = false;
		}
	}

	@Override
	public boolean isUsable() {
		return status;
	}

	@Override
	public void setUsable(boolean status) {
		this.status = status;
	}
}