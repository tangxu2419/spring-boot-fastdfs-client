package com.vcredit.framework.fastdfs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Dong Zhuming
 */
public class TrackerConnectionPool {

    private TrackerConnection[] trackerConnections;
    private int size;

    public TrackerConnectionPool() throws IOException {
        //FIXME hard coding
        Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.setSoTimeout(1000);
        final InetSocketAddress inetSocketAddress = new InetSocketAddress("10.138.30.172", 22122);
        socket.connect(inetSocketAddress);
        trackerConnections = new TrackerConnection[]{new TrackerConnection(socket, inetSocketAddress)};
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
