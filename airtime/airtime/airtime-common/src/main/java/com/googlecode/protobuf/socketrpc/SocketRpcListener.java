package com.googlecode.protobuf.socketrpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import com.google.protobuf.Service;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Socket listen for incoming connection
 */
public final class SocketRpcListener implements Runnable {

    private static final Log LOG = LogFactory.getLog(SocketRpcListener.class);
    private final Map<String, Service> serviceMap = new HashMap<String, Service>();
    private final int port;
    private final int backLog;
    private final String address;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private boolean running = false;
    private final ArrayList<SocketRpcHandler> handlers = new ArrayList<SocketRpcHandler>();
    private final ArrayList<SocketRpcHandler> runningHandlers = new ArrayList<SocketRpcHandler>();
    private int currentConnections = 0;
    private int maxConnections = 10000;
    private final SocketRpcRequestDispatcher dispatcher = new SocketRpcRequestDispatcher();
    private long connectionDropMillis = Long.MAX_VALUE;
    private ReentrantLock sync = new ReentrantLock();

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public long getConnectionDropMillis() {
        return connectionDropMillis;
    }

    public void setConnectionDropMillis(long connectionDropMillis) {
        this.connectionDropMillis = connectionDropMillis;
    }
    
    /**
     * @param port
     *            Port that this server will be started on.
     * @param executorService
     *            To be used for handling requests.
     */
    public SocketRpcListener(String address, int port, int backLog) {
        this.address = address;
        this.port = port;
        this.backLog = backLog;

    }

    /**
     * Register an rpc service implementation on this server.
     */
    public void registerService(Service service) {
        serviceMap.put(service.getDescriptorForType().getFullName(), service);
    }

    /**
     * return an array of existing clients
     * @return
     */
    public SocketRpcClient[] getClients() {
        sync.lock();
        try {
            SocketRpcClient[] clients = new SocketRpcClient[handlers.size() + runningHandlers.size()];
            int index = 0;
            for (SocketRpcHandler handler:handlers) {
                clients[index++] = handler.client;
            }
            for (SocketRpcHandler handler:runningHandlers) {
                clients[index++] = handler.client;
            }
            return clients;
        }
        finally {
            sync.unlock();
        }
    }

    /**
     *
     * @param executorService
     * @throws IOException
     */
    public void begin(ExecutorService executorService) throws IOException {
        serverSocket = new ServerSocket(port, backLog, InetAddress.getByName(address));

        this.executor = executorService;

        executor.execute(this);
        executor.execute(dispatcher);

        running = true;
    }

    public void end() {
        try {
            LOG.info("Shutting down server");
            try {
                serverSocket.close();
            } catch (Exception ex) {
                // ignored
            }

            // force all client to clean up;
            sync.lock();
            try {
                for (SocketRpcHandler handler : handlers) {
                    handler.cleanUp();
                }
                handlers.clear();
                for (SocketRpcHandler handler : runningHandlers) {
                    handler.cleanUp();
                }
                runningHandlers.clear();
                currentConnections = 0;
            } finally {
                sync.unlock();
            }
        } finally {
            //executor = null;
            serverSocket = null;
            running = false;
        }
    }

    /**
     * Start the server to listen for requests. This thread is blocked.
     *
     * @throws IOException
     *             If there was an errorText starting up the server.
     */
    @Override
    public void run() {
        LOG.info("Listening for requests on : " + address + ":" + port);
        try {
            while (true) {
                Socket s = serverSocket.accept();
                try {
                    SocketRpcHandler handler = new SocketRpcHandler(s);
                    addHandler(handler);
                } catch (IOException ex) {
                    LOG.warn("Unknown error occured while adding handler ", ex);
                }
            }
        } catch (IOException ex) {
        } finally {
            running = false;
        }
    }

    void addHandler(SocketRpcHandler handler) {
        sync.lock();
        try {
            handlers.add(handler);
            currentConnections++;
        } finally {
            sync.unlock();
        }
    }

    void runHandler(SocketRpcHandler handler) {
        sync.lock();
        try {
            handlers.remove(handler);
            runningHandlers.add(handler);
        } finally {
            sync.unlock();
        }
        executor.execute(handler);
        //handler.run();
    }

    void standHandler(SocketRpcHandler handler) {
        sync.lock();
        try {
            runningHandlers.remove(handler);
            handlers.add(0, handler);
        } finally {
            sync.unlock();
        }
    }

    public void removeClient(SocketRpcClient client) {
        sync.lock();
        try {
            for (SocketRpcHandler handler:handlers) {
                if (handler.client == client) {
                    removeHandler(handler);
                    return;
                }
            }
            for (SocketRpcHandler handler:runningHandlers) {
                if (handler.client == client) {
                    removeHandler(handler);
                    return;
                }
            }
        } finally {
            sync.unlock();
        }
    }

    void removeHandler(SocketRpcHandler handler) {
        sync.lock();
        try {
            boolean removed = handlers.remove(handler);
            removed |= runningHandlers.remove(handler);
            if (removed) {
                currentConnections--;
            }
        } finally {
            sync.unlock();
        }
        handler.cleanUp();
    }

    class SocketRpcRequestDispatcher implements Runnable {
        
        @Override
        public void run() {
            while (running) {
                SocketRpcHandler toRun = null;
                SocketRpcHandler toRemove = null;
                try {
                    sync.lock();
                    try {
                        long currentTime = System.currentTimeMillis();
                        Iterator<SocketRpcHandler> iter = handlers.iterator();
                        while (iter.hasNext()) {
                            SocketRpcHandler handler = iter.next();
                            if (handler.client.hasRequest()) {
                                toRun = handler;
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Dispatching request from client " + handler.client);
                                }
                                break;
                            }
                            else if (currentTime - handler.lastRun > connectionDropMillis) {
                                toRemove = handler;
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Dropping connection from client " + handler.client);
                                }
                                break;
                            }
                        }
                    } finally {
                        sync.unlock();
                    }
                    if (toRun != null) {
                        runHandler(toRun);
                    } else if (toRemove !=null) {
                        removeHandler(toRemove);
                    } else {
                        Thread.sleep(1);
                    }
                } catch (Throwable ex) {
                    LOG.warn("Unexpected exception occured while dispatching request ", ex);
                }
            } // while running
        }
    }

    /**
     * Handles socket requests.
     */
    class SocketRpcHandler implements Runnable {
        public long lastRun = Long.MIN_VALUE;
        SocketRpcClient client;
        SocketRpcHandler(Socket socket) throws IOException {
            client = new SocketRpcClient(socket);
        }
        @Override
        public void run() {
            lastRun = System.currentTimeMillis();
            try {
                // Parse request
                if (LOG.isDebugEnabled() && !client.hasRequest()) {
                    LOG.debug("Input stream is not available for reading " + client);
                }
                client.run(serviceMap);
                if (client.isAlive())
                    standHandler(this);
                else
                    removeHandler(this);
            } catch (IOException e) {
                removeHandler(this);
                LOG.warn("Error while reading/writing", e);
            } finally {
            }
            // remove from container
        }
        public void cleanUp() {
            if (client != null) {
                client.destroy();
                client = null;
            }
        }
    }
}
