package com.googlecode.protobuf.socketrpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.googlecode.protobuf.socketrpc.SocketRpcController.ErrorReason;
import java.io.EOFException;
import java.io.FilterInputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Socket implementation of {@link RpcChannel}. Makes a synchronous rpc call to
 * a server running {@link SocketRpcServer} with the rpc method implementation
 * running on it.
 * <p>
 * If an {@link RpcCallback} is given, the
 * {@link #callMethod(MethodDescriptor, RpcController, Message, Message, RpcCallback)}
 * method will invoke it with the same protobuf that the rpc method
 * implementation on the server side invoked the callback with, or will not
 * invoke it if that was the case on the server side.
 * <p>
 * Nam Pham: this class was modified so that it will not drop connection after
 * receiving response. It will also retry one more time if request is not sent or
 * no single byte of response can be received. The reason of retry is to fix the
 * case connection is dropped on server-side.
 * 
 * @author Shardul Deo
 * @modifier Nam Pham (nam.pham@mobivi.com)
 */
public class SocketRpcChannel implements RpcChannel {
    static final int maxRetry = 1;
    private final static Logger LOG = org.apache.log4j.Logger.getLogger(SocketRpcChannel.class);
    private final String host;
    private final int port;
    private final SocketFactory socketFactory;
    Socket socket = null;
    OutputStream out = null;
    InputStream in = null;

    /**
     * Create a channel for TCP/IP RPCs.
     */
    public SocketRpcChannel(String host, int port) {
        this(host, port, SocketFactory.getDefault());
    }

    // Used for testing
    SocketRpcChannel(String host, int port, SocketFactory socketFactory) {
        this.host = host;
        this.port = port;
        this.socketFactory = socketFactory;
    }

    /**
     * Create new rpc controller to be used to control one request.
     */
    public SocketRpcController newRpcController() {
        return new SocketRpcController();
    }

    /**
     * the method will try to open a socket if it is not opened yet.
     * If this method return FALSE. The caller should stop immediately
     * without access the socketController.
     * @param socketController
     * @return 0 if socket was opened, 1 if socket is opened successfully, -1 if socket failed to open.
     */
    int openSocket(SocketRpcController socketController) {
        if (socket != null && in != null && out != null) {
            return 0;
        }
        // Open socket
        try {
            socket = socketFactory.createSocket(host, port);
            out = socket.getOutputStream(); //new BufferedOutputStream(socket.getOutputStream());
            in = socket.getInputStream(); //new BufferedInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            handleError(socketController, ErrorReason.UnknownHost,
                    "Could not find host: " + host, e);
            return -1;
        } catch (IOException e) {
            handleError(socketController, ErrorReason.IOError, String.format(
                    "Could not open I/O for %s:%s", host, port), e);
            close();
            return -1;
        }
        return 1;
    }

    public void close() {
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            LOG.log(Level.WARN, "Error closing I/O", e);
        }
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            LOG.log(Level.WARN, "Error closing I/O", e);
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            LOG.log(Level.WARN, "Error closing I/O", e);
        }
        out = null;
        in = null;
        socket = null;
    }

    @Override
    public void callMethod(MethodDescriptor method, RpcController controller,
            Message request, Message responsePrototype,
            RpcCallback<Message> done) {
        callMethod(method, controller, request, responsePrototype, done, 0);
    }

    /**
     * this class is copied from the code of Google buffer protocol
     */
    static class LimitedInputStream extends FilterInputStream {
        int limit;

        public LimitedInputStream(InputStream input, int size) {
            super(input);
            limit = size;
        }

        @Override
        public int available() throws IOException {
            return Math.min(super.available(), limit);
        }

        @Override
        public int read() throws IOException {
            if (limit <= 0) {
                return -1;
            }
            int result = super.read();
            if (result >= 0) {
                --limit;
            }
            return result;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (limit <= 0) {
                return -1;
            }
            len = Math.min(len, limit);
            int result = super.read(b, off, len);
            if (result >= 0) {
                limit -= result;
            }
            return result;
        }

        @Override
        public long skip(long n) throws IOException {
            long result = super.skip(Math.min(n, limit));
            if (result >= 0) {
                limit -= result;
            }
            return result;
        }
    };

    /**
     * this method is copied from Google protocol buffer
     * @param input
     * @return
     * @throws IOException
     */
    static int readRawVarint32(InputStream input) throws IOException {
        int result = 0;
        int offset = 0;
        for (; offset < 32; offset += 7) {
            int b = input.read();
            if (b == -1) {
                throw new EOFException("stream is truncated");
            }
            result |= (b & 0x7f) << offset;
            if ((b & 0x80) == 0) {
                return result;
            }
        }
        // Keep reading up to 64 bits.
        for (; offset < 64; offset += 7) {
            int b = input.read();
            if (b == -1) {
                throw new EOFException("stream is truncated");
            }
            if ((b & 0x80) == 0) {
                return result;
            }
        }
        throw new IOException("malformed varInt");
    }

    public void callMethod(MethodDescriptor method, RpcController controller,
            Message request, Message responsePrototype,
            RpcCallback<Message> done, int retry) {
        // Must pass in a SocketRpcController
        SocketRpcController socketController = (SocketRpcController) controller;

        // Check request
        if (!request.isInitialized()) {
            handleError(socketController, ErrorReason.BadRequestProto,
                    "Request is uninitialized", null);
            return;
        }

        // Create RPC request protobuf
        SocketRpcProtos.Request rpcRequest = SocketRpcProtos.Request.newBuilder().setRequestProto(request.toByteString()).setServiceName(method.getService().getFullName()).setMethodName(method.getName()).build();

        int connection = -1;        
        int responseSize = -1;
        boolean requestSent = false;
        try {
            if ((connection = openSocket(socketController)) < 0) {
                return;
            }
            // send request
            rpcRequest.writeDelimitedTo(out);
            out.flush();
            requestSent = true;
             
            // read response size
            responseSize = readRawVarint32(in);

            // Read and handle response
            SocketRpcProtos.Response.Builder builder = SocketRpcProtos.Response.newBuilder()
                    .mergeFrom(new LimitedInputStream(in, responseSize));
            if (!builder.isInitialized()) {
                handleError(socketController, ErrorReason.BadResponseProto,
                        "Bad response from server", null);
                close();
                return;
            }
            SocketRpcProtos.Response rpcResponse = builder.build();
            handleRpcResponse(responsePrototype, rpcResponse, socketController,
                    done);
        } catch (IOException e) {
            close();
            if (connection == 0 && retry < maxRetry && (!requestSent || (responseSize==-1 && e instanceof EOFException))) {
                // connection is dropped from server side --> retry the flow
                callMethod(method, controller, request, responsePrototype, done, ++retry);
            }
            else {
                handleError(socketController, ErrorReason.IOError, String.format(
                    "Error reading/writing for %s:%s", host, port), e);
            }
        } catch (Throwable e) {
            handleError(socketController, ErrorReason.UnknownError, String.format(
                    "Unknown error %s", e.getMessage()), e);
            close();
        }
    }

    private void handleRpcResponse(Message responsePrototype,
            SocketRpcProtos.Response rpcResponse,
            SocketRpcController socketController, RpcCallback<Message> callback) {

        // Check for errorText
        socketController.success = true;
        if (rpcResponse.hasError()) {
            ErrorReason reason = getErrorReason(rpcResponse);
            handleError(socketController, reason, rpcResponse.getError(), null);
        }

        if ((callback == null) || !rpcResponse.getCallback()) {
            // No callback needed
            return;
        }

        if (!rpcResponse.hasResponseProto()) {
            // Callback was called with null on server side
            callback.run(null);
            return;
        }

        try {
            Message.Builder builder = responsePrototype.newBuilderForType().mergeFrom(rpcResponse.getResponseProto());
            if (!builder.isInitialized()) {
                handleError(socketController, ErrorReason.BadResponseProto,
                        "Uninitialized RPC Response Proto", null);
                return;
            }
            Message response = builder.build();
            callback.run(response);
        } catch (InvalidProtocolBufferException e) {
            handleError(socketController, ErrorReason.BadResponseProto,
                    "Response could be parsed as "
                    + responsePrototype.getClass().getName(), e);
        }
    }

    private ErrorReason getErrorReason(SocketRpcProtos.Response rpcResponse) {
        switch (rpcResponse.getErrorReason()) {
            case BAD_REQUEST_DATA:
                return ErrorReason.ServerBadRequestData;
            case BAD_REQUEST_PROTO:
                return ErrorReason.ServerBadRequestProto;
            case SERVICE_NOT_FOUND:
                return ErrorReason.ServerServiceNotFound;
            case METHOD_NOT_FOUND:
                return ErrorReason.ServerMethodNotFound;
            case RPC_ERROR:
                return ErrorReason.ServerRpcError;
            case RPC_FAILED:
                return ErrorReason.ServerRpcFailed;
            default:
                return ErrorReason.ServerUnknownError;
        }
    }

    private void handleError(SocketRpcController socketController,
            ErrorReason reason, String msg, Throwable e) {
        LOG.log(Level.WARN, reason + ": " + msg, e);
        socketController.success = false;
        socketController.reason = reason;
        socketController.errorText = msg;
        socketController.error = e;
    }
}
