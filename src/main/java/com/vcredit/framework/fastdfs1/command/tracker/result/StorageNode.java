/*
 *    Copyright 2019 vcredit
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.vcredit.framework.fastdfs.command.tracker.result;

import java.net.InetSocketAddress;

/**
 * @author tangxu
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
