package com.vcredit.framework.fastdfs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Dong Zhuming
 */
public class StorageLocation {

    private final int storePathIndex;
    private final InetSocketAddress inetSocketAddress;
    private final Socket socket;
    private String path;

    public StorageLocation(String ip, int port, int storePath) throws IOException {
        this.inetSocketAddress = new InetSocketAddress(ip, port);
        this.socket = new Socket(ip, port);
        this.storePathIndex = storePath;
    }

    public int getStorePathIndex() {
        return storePathIndex;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return "StorageLocation{" +
                "storePathIndex=" + storePathIndex +
                ", ip=" + inetSocketAddress.getAddress() +
                ", port=" + inetSocketAddress.getPort() +
                ", path='" + path + '\'' +
                '}';
    }
}
