package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dong Zhuming
 */
@Slf4j
public class TrackerConnectionPool {

    private TrackerConnection[] trackerConnections;
    private int size;

    public TrackerConnectionPool(FastdfsProperties properties) throws IOException {
        trackerConnections = properties.getCluster().getNodes().stream().map(node -> {
            String[] addressAndPort = node.split(":", 2);
            final InetSocketAddress inetSocketAddress = new InetSocketAddress(addressAndPort[0], Integer.parseInt(addressAndPort[1]));
            Socket socket = new Socket();
            try {
                socket.setReuseAddress(true);
                socket.setSoTimeout(properties.getConnectTimeout().toMillisPart());
                socket.connect(inetSocketAddress);
            } catch (IOException e) {
                log.error("An error occurred when establishing connection to Tracker Server[{}]", node);
                throw new RuntimeException(e);
            }
            return new TrackerConnection(socket, inetSocketAddress);
        }).toArray(TrackerConnection[]::new);
        size = properties.getPool().getMaxSize();
    }

    /**
     * 获取连接
     *
     * @return
     * @throws Exception 连接池不足异常
     */
    public synchronized TrackerConnection borrow() {

        return trackerConnections[0];
    }


    /**
     * 释放连接
     *
     * @return
     */
    public synchronized void release(TrackerConnection trackerConnections) {
    }
}
