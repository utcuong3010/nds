package com.googlecode.protobuf.socketrpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.google.protobuf.RpcChannel;
import com.google.protobuf.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Socket server for running rpc services. It can serve requests for any
 * registered service from any client who is using {@link RpcChannel}.
 * <p>
 * Note that this server can only handle synchronous requests, so the client is
 * blocked until the callback is called by the service implementation.
 * 
 * @author Shardul Deo
 */
public class SocketRpcServer implements Runnable {

    private static final Log LOG = LogFactory.getLog(SocketRpcServer.class);
    private final Map<String, Service> serviceMap = new HashMap<String, Service>();
    private final int port;
    private final int backLog;
    private final String address;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private boolean running = false;
    private final ArrayList<Handler> handlers = new ArrayList<Handler>();

    /**
     * @param port
     *            Port that this server will be started on.
     * @param executorService
     *            To be used for handling requests.
     */
    public SocketRpcServer(String address, int port, int backLog) {
        this.address = address;
        this.port = port;
        this.backLog = backLog;

    }

    /**
     * return an array of existing clients
     * @return
     */
    public SocketRpcClient[] getClients() {
        synchronized(handlers) {
            SocketRpcClient[] clients = new SocketRpcClient[handlers.size()];
            int index = 0;
            for (Handler handler:handlers) {
                clients[index++] = handler.client;
            }
            return clients;
        }
    }

    /**
     * Register an rpc service implementation on this server.
     */
    public void registerService(Service service) {
        serviceMap.put(service.getDescriptorForType().getFullName(), service);
    }

    /**
     *
     * @param executorService
     * @throws IOException
     */
    public void begin(ExecutorService executorService) throws IOException {
        serverSocket = new ServerSocket(port, backLog, InetAddress.getByName(address));

        executor = executorService;
        executor.execute(this);

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
            synchronized (handlers) {
                for (Handler handler : handlers) {
                    handler.cleanUp();
                }
            }
        } finally {
            executor = null;
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
                Handler handler = new Handler(serverSocket.accept());
                synchronized (handlers) {
                    handlers.add(handler);
                }
                // Thread blocks here waiting for requests
                executor.execute(handler);
            }
        } catch (IOException ex) {
        } finally {
            running = false;
        }
    }

    void removeHandler(Handler handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    /**
     * Handles socket requests.
     */
    class Handler implements Runnable {
        SocketRpcClient client;

        Handler(Socket socket) throws IOException {
            client = new SocketRpcClient(socket);
        }

        @Override
        public void run() {
            // Call method
            try {
                while (true) {
                    client.run(serviceMap);
                    if (!client.isAlive())
                        break;
                }
            } catch (IOException e) {
                LOG.warn("Error while reading/writing", e);
            } finally {
                cleanUp();
                removeHandler(this);
            }
            // remove from container

        }

        public void cleanUp() {
            if (client!=null) {
                client.destroy();
                client = null;
            }
        }

    }
}
