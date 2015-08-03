/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.protobuf.socketrpc;

import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.Service;
import com.googlecode.protobuf.socketrpc.SocketRpcProtos.Response;
import com.googlecode.protobuf.socketrpc.SocketRpcProtos.Response.ServerErrorReason;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Nam Pham
 */
public class SocketRpcClient {
    public static class Statistics {
        public long totalCall;
        public long firstCall;
        public long lastCall;
        public long fastestCall;
        public long slowestCall;
        public double averageCall;
    }

    private static final Log LOG = LogFactory.getLog(SocketRpcServer.class);

    private final Socket socket;
    OutputStream bufferredOut = null;
    InputStream bufferredIn = null;
    List<Service> services = new ArrayList<Service>(10);
    boolean alive = true;
    Map<String, Statistics> statistics = new HashMap<String, Statistics>();

    public SocketRpcClient(Socket socket) throws IOException {
        try {
            this.socket = socket;
            InputStream sockIn = socket.getInputStream();
            bufferredIn = new BufferedInputStream(sockIn);
            bufferredOut = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            this.destroy();
            throw ex;
        }
    }

    public Map<String, Statistics> getStatistics() {
        return statistics;
    }

    public boolean isAlive() {
        return alive && socket!=null && !socket.isClosed();
    }
    
    public boolean hasRequest() throws IOException {
        return bufferredIn.available() > 0;
    }

    public Socket getSocket() {
        return socket;
    }

    public List<Service> getServices() {
        synchronized (this) {
            return services.subList(0, services.size());
        }
    }
    
    public void run(Map<String, Service> serviceMap) throws IOException {
        // Call method
        SocketRpcProtos.Response rpcResponse = null;
        try {
            // Parse request
            SocketRpcProtos.Request rpcRequest;
            try {
                if (LOG.isDebugEnabled() && bufferredIn.available() == 0) {
                    LOG.debug("Input stream is not available for reading " + bufferredIn.available());
                }
                SocketRpcProtos.Request.Builder builder = SocketRpcProtos.Request.newBuilder().mergeDelimitedFrom(bufferredIn);
                if (!builder.isInitialized()) {
                    // client is disconnected
                    alive = false;
                    socket.shutdownOutput();
                }
                rpcRequest = builder.build();
            } catch (IOException e) {
                //rpcResponse = handleError("Bad request data from client",
                //		ServerErrorReason.BAD_REQUEST_DATA, e);
                throw e;
            }
            rpcResponse = callMethod(serviceMap, rpcRequest);
            rpcResponse.writeDelimitedTo(bufferredOut);
            bufferredOut.flush();
        } catch (IOException e) {
            LOG.warn("Error while reading/writing", e);
            throw e;
        } finally {
        }
        // remove from container
    }

    public void destroy() {
        if (bufferredIn != null) {
            try {
                bufferredIn.close();
            } catch (IOException e) {
            }
        }
        bufferredIn = null;
        if (bufferredOut != null) {
            try {
                bufferredOut.close();
            } catch (IOException e) {
            }
        }
        bufferredOut = null;
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                // It's ok
                LOG.warn("Error while closing I/O", e);
            }
        }

        services.clear();
        alive = false;
    }

    private SocketRpcProtos.Response callMethod(Map<String, Service> serviceMap, SocketRpcProtos.Request rpcRequest) {
        // Get the service/method
        Service service = serviceMap.get(rpcRequest.getServiceName());
        if (service == null) {
            return handleError("Could not find service: "
                    + rpcRequest.getServiceName(),
                    ServerErrorReason.SERVICE_NOT_FOUND, null);
        }

        synchronized (this) {
            if (!services.contains(service)) {
                services.add(service);
            }
        }

        MethodDescriptor method = service.getDescriptorForType().findMethodByName(rpcRequest.getMethodName());
        if (method == null) {
            return handleError(String.format(
                    "Could not find method %s in service %s", rpcRequest.getMethodName(), service.getDescriptorForType().getFullName()),
                    ServerErrorReason.METHOD_NOT_FOUND, null);
        }
        // track start time for statistics
        long startTime = System.nanoTime();
        try {
            // Parse request
            Message.Builder builder;
            try {
                builder = service.getRequestPrototype(method).newBuilderForType().mergeFrom(
                        rpcRequest.getRequestProto());
                if (!builder.isInitialized()) {
                    return handleError("Invalid request proto",
                            ServerErrorReason.BAD_REQUEST_PROTO, null);
                }
            } catch (InvalidProtocolBufferException e) {
                return handleError("Invalid request proto",
                        ServerErrorReason.BAD_REQUEST_PROTO, e);
            }
            Message request = builder.build();

            // Call method
            SocketRpcController socketController = new SocketRpcController();
            socketController.success = true;
            Callback callback = new Callback();
            try {
                service.callMethod(method, socketController, request, callback);
            } catch (RuntimeException e) {
                return handleError("Error running method "
                        + method.getFullName() + rpcRequest.getMethodName(),
                        ServerErrorReason.RPC_ERROR, e);
            }

            // Build and return response (callback is optional)
            Response.Builder responseBuilder = SocketRpcProtos.Response.newBuilder();
            if (callback.response != null) {
                responseBuilder.setCallback(true).setResponseProto(
                        callback.response.toByteString());
            } else {
                // Set whether callback was called
                responseBuilder.setCallback(callback.invoked);
            }
            if (!socketController.success) {
                responseBuilder.setError(socketController.errorText);
                responseBuilder.setErrorReason(ServerErrorReason.RPC_FAILED);
            }
            return responseBuilder.build();
        }
        finally {
            // calculate the statistics
            long elapsedTime = System.nanoTime() - startTime;

            String statKey = rpcRequest.getServiceName() + "-" + rpcRequest.getMethodName();
            Statistics stats = statistics.containsKey(statKey)?statistics.get(statKey): new Statistics();

            if (stats.totalCall==0) {
                stats.averageCall = stats.firstCall = stats.fastestCall = stats.slowestCall =  elapsedTime;
                statistics.put(statKey, stats);
            }
            else {
                stats.averageCall = (stats.averageCall * stats.totalCall + elapsedTime)/(stats.totalCall + 1);
                stats.fastestCall = Math.min(stats.fastestCall, elapsedTime);
                stats.slowestCall = Math.max(stats.slowestCall, elapsedTime);
            }
            stats.lastCall = elapsedTime;
            stats.totalCall++;
        }
    }

    private SocketRpcProtos.Response handleError(String msg,
            ServerErrorReason reason, Exception e) {
        LOG.warn(reason + ": " + msg, e);
        return SocketRpcProtos.Response.newBuilder().setError(msg).setErrorReason(reason).build();
    }

    /**
     * Callback that just saves the response and the fact that it was
     * invoked.
     */
    private class Callback implements RpcCallback<Message> {
        private Message response;
        private boolean invoked = false;

        public void run(Message response) {
            this.response = response;
            invoked = true;
        }
    }
}
