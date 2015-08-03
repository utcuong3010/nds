package com.googlecode.protobuf.socketrpc;

import com.google.protobuf.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.googlecode.protobuf.socketrpc.SocketRpcServer;
import java.util.List;

public class TCPListener implements InitializingBean, DisposableBean {

    String address;
    int port = 12345;
    int backLog = 64;
    List<Service> serviceInstances;
    SocketRpcServer socketRpcServer;
    int threadPoolSize;
    ExecutorService executorService;

    public TCPListener() {
    }

    public TCPListener(List<Service> serviceInstances) {
        this.setServiceInstances(serviceInstances);
    }

    public List<Service> getServiceInstances() {
        return serviceInstances;
    }

    public void setServiceInstances(List<Service> serviceInstances) {
        this.serviceInstances = serviceInstances;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        socketRpcServer = new SocketRpcServer(
                address, port, backLog);
        for (Service serviceInstance : serviceInstances) {
            socketRpcServer.registerService(serviceInstance);
        }

        executorService = Executors.newFixedThreadPool(threadPoolSize);

        socketRpcServer.begin(executorService);
    }

    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub
        if (socketRpcServer != null) {
            socketRpcServer.end();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBackLog() {
        return backLog;
    }

    public void setBackLog(int backLog) {
        this.backLog = backLog;
    }

    public SocketRpcServer getSocketRpcServer() {
        return socketRpcServer;
    }

    public void setSocketRpcServer(SocketRpcServer socketRpcServer) {
        this.socketRpcServer = socketRpcServer;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
}
