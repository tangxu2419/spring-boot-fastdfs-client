package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author tangxu
 */
public class TrackerConnectionPool2 {

    private static final Logger log = LoggerFactory.getLogger(TrackerConnectionPool2.class);


    private int maxSize;
    private int soTimeout;
    private int connectionTimeout;
    private int maxAttempts;
    private LinkedList<TrackerConnection> trackerConnections = new LinkedList<>();

    public TrackerConnectionPool2(FastdfsProperties properties) {
        properties.getCluster().getNodes().forEach(node -> {
            String[] split = node.split(":", 2);
            try {
                final InetSocketAddress inetSocketAddress = new InetSocketAddress(split[0], Integer.parseInt(split[1]));
                TrackerNodeLocator.addNode(inetSocketAddress);
            } catch (IllegalArgumentException e) {
                log.error("Illegal Tracker Server: {}", node);
            }
        });
        this.maxSize = properties.getPool().getMaxSize();
        this.connectionTimeout = properties.getConnectTimeout().toMillisPart();
        this.soTimeout = properties.getSoTimeout().toMillisPart();
        this.maxAttempts = properties.getPool().getMaxAttempt();
    }

    private static class TrackerNodeLocator {

        private static Map<InetSocketAddress, Integer> nodeMap = new HashMap<>();

        static void addNode(InetSocketAddress inetSocketAddress) {
            nodeMap.put(inetSocketAddress, 0);
        }

        /**
         * 将此连接标记为不可用状态
         *
         * @param inetSocketAddress Tracker地址
         * @return 标记不可用之前的连接数
         */
        static Integer markProblem(InetSocketAddress inetSocketAddress) {
            return nodeMap.put(inetSocketAddress, Integer.MAX_VALUE);
        }

        static InetSocketAddress getNodeSocket() {
            //匹配连接数最小的服务器
            //FIXME 锁定addNode/removeNode方法
            return nodeMap.entrySet().stream()
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    .filter(entry -> entry.getValue() != Integer.MAX_VALUE)
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }
    }

    /**
     * 获取连接
     *
     * @throws IOException 连接异常
     */
    synchronized TrackerConnection borrow() throws IOException {
        TrackerConnection start = null;
        do {
            TrackerConnection trackerConnection = trackerConnections.poll();
            if (start == null && trackerConnection != null) {
                //获取第一个元素时，start为null
                start = trackerConnection;
            } else if (start == trackerConnection || trackerConnection == null) {
                TrackerConnection newTrackerConnection = establishConnection();
                trackerConnections.offer(newTrackerConnection);
                return newTrackerConnection;
            }
            if (!trackerConnection.isInUse()) {
                if (!checkConnection(trackerConnection)) {
                    log.warn("Socket has already been closed. Recover connection");
                    if (!recoverConnection(trackerConnection)) {
                        continue;
                    }
                }
                trackerConnection.setInUse(true);
                //TODO whether checkConnection
                boolean valid = trackerConnection.isValid();

                trackerConnections.offer(trackerConnection);
                return trackerConnection;
            } else {
                trackerConnections.offer(trackerConnection);
            }
        } while (true);
    }

    private boolean checkConnection(TrackerConnection trackerConnection) {
        boolean alive;
        try {
            alive = trackerConnection.getSocket().getKeepAlive();
        } catch (IOException e) {
            log.error(e.getMessage());
            alive = false;
        }
        return alive;
    }

    private boolean recoverConnection(TrackerConnection trackerConnection) {
        try {
            trackerConnection.getSocket().connect(trackerConnection.getInetSocketAddress(), connectionTimeout);
        } catch (IOException e) {
            log.error("Cannot recover connection[{}]", trackerConnection);
            try {
                trackerConnection.getSocket().close();
            } catch (IOException e1) {
                e1.printStackTrace();
                // swallow this error
            }
            return false;
        }
        return true;
    }

    private TrackerConnection establishConnection() throws IOException {
        if (trackerConnections.size() >= maxSize) {
            log.error("Establish connection failed. Reached max connection size[{}]", maxSize);
            return null;
        }
        for (int i = 1; i <= this.maxAttempts; i++) {
            InetSocketAddress inetSocketAddress = TrackerNodeLocator.getNodeSocket();
            try {
                return new TrackerConnection(inetSocketAddress, this.soTimeout, this.connectionTimeout, true);
            } catch (IOException e) {
                log.error("Failed to connect to Tracker Server[{}], attempt reconnect ({}/{})", inetSocketAddress, i, maxAttempts);
                markAsProblem(inetSocketAddress);
            }
        }
        return null;
    }

    /**
     * 标记问题Socket
     */
    private void markAsProblem(InetSocketAddress inetSocketAddress) {
        TrackerNodeLocator.markProblem(inetSocketAddress);
    }

    /**
     * 释放连接
     */
    synchronized void release(TrackerConnection trackerConnection) {
        trackerConnection.setInUse(false);
    }

}
