package com.vcredit.framework.fastdfs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Dong Zhuming
 */
public class TrackerConnection implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(TrackerConnection.class);

    private final Socket socket;
    private final InetSocketAddress inetSocketAddress;
    private boolean inUse;

    public TrackerConnection(Socket socket, InetSocketAddress inetSocketAddress) {
        this(socket, inetSocketAddress, false);
    }

    public TrackerConnection(Socket socket, InetSocketAddress inetSocketAddress, boolean inUse) {
        this.socket = socket;
        this.inetSocketAddress = inetSocketAddress;
        this.inUse = inUse;
    }

    public Socket getSocket() {
        return socket;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public OutputStream getOutputStream() throws IOException {
        Assert.notNull(this.socket, "socket shouldn't be null");
        return this.socket.getOutputStream();
    }

    public InputStream getIntputStream() throws IOException {
        Assert.notNull(this.socket, "socket shouldn't be null");
        return this.socket.getInputStream();
    }

    @Override
    public synchronized void close() throws IOException {
        if (this.socket != null && !socket.isClosed()) {
            //TODO 向服务器发送退出指令

        }
        this.inUse = false;
    }

    public boolean isInUse() {
        return false;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
    }
