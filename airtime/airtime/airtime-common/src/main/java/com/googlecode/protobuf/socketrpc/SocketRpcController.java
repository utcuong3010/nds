package com.googlecode.protobuf.socketrpc;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * Simple controller.
 *
 * @author Shardul Deo
 */
public class SocketRpcController implements RpcController {

    /**
     * Error reason, to be used client side
     */
    public enum ErrorReason {
        // Client-side errors

        /** Rpc was called with bad request proto */
        BadRequestProto,
        /** Rpc returned a bad response proto */
        BadResponseProto,
        /** Could not find supplied host */
        UnknownHost,
        /** I/O errorText while communicating with server */
        IOError,
        // Server-side errors
        /** Server received bad request data */
        ServerBadRequestData,
        /** Server received bad request proto */
        ServerBadRequestProto,
        /** Service not found on server */
        ServerServiceNotFound,
        /** Method not found on server */
        ServerMethodNotFound,
        /** Rpc threw exception on server */
        ServerRpcError,
        /** Rpc threw exception on server */
        ServerRpcFailed,
        /** Unknown errorText on server */
        ServerUnknownError,
        /** Unknow error */
        UnknownError,
    }
    boolean success = false;
    String errorText = null;
    Throwable error = null;
    ErrorReason reason = null;

    @Override
    public void reset() {
        success = false;
        errorText = null;
        reason = null;
    }

    @Override
    public boolean failed() {
        return !success;
    }

    /**
     * @return Reason for rpc errorText, to be used client side
     */
    public ErrorReason errorReason() {
        return reason;
    }

    public Throwable error() {
        return error;
    }

    @Override
    public String errorText() {
        return errorText;
    }

    @Override
    public void startCancel() {
        // Not yet supported
        throw new UnsupportedOperationException(
                "Cannot cancel request in Socket RPC");
    }

    @Override
    public void setFailed(String reason) {
        success = false;
        errorText = reason;
    }

    @Override
    public boolean isCanceled() {
        // Not yet supported
        throw new UnsupportedOperationException(
                "Cannot cancel request in Socket RPC");
    }

    @Override
    public void notifyOnCancel(RpcCallback<Object> callback) {
        // Not yet supported
        throw new UnsupportedOperationException(
                "Cannot cancel request in Socket RPC");
    }
}
