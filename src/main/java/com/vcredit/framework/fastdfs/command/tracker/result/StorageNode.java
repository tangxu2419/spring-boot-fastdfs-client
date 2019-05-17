package com.vcredit.framework.fastdfs.command.tracker.result;

import java.net.InetSocketAddress;

/**
 * @author tangxu
 * @date 2019/5/814:32
 */
public class StorageNode {

    private String groupName;

    private String ip;

    private int port;

    private byte storeIndex;

    public StorageNode(String groupName, String ip, int port) {
        this.groupName = groupName;
        this.ip = ip;
        this.port = port;
    }

    public StorageNode(String groupName, String ip, int port, byte storeIndex) {
        this.groupName = groupName;
        this.ip = ip;
        this.port = port;
        this.storeIndex = storeIndex;
    }

    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(ip, port);
    }

    public String getGroupName() {
        return groupName;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public byte getStoreIndex() {
        return storeIndex;
    }

    @Override
    public String toString() {
        return "StorageNode{" +
                "groupName='" + groupName + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", storeIndex=" + storeIndex +
                '}';
    }
}
