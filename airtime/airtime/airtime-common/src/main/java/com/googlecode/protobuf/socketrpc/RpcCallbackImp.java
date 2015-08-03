package com.googlecode.protobuf.socketrpc;

import com.google.protobuf.RpcCallback;

public class RpcCallbackImp<T> implements RpcCallback<T> {
		private T result;
		@Override
		public void run(T obj) {
			result=obj;
			
		}
		public T getResult() {
			return result;
		}
	}