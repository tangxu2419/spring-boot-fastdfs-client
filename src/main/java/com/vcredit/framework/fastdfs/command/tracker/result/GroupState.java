/*
 *    Copyright 2019 VCREDIT
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

import com.vcredit.framework.fastdfs.command.FdfsField;
import com.vcredit.framework.fastdfs.constant.Constants;

/**
 * @author tangxu
 */
public class GroupState {

    /**
     * name of this group
     */
    @FdfsField(order = 0, maxLength = Constants.FDFS_GROUP_NAME_MAX_LEN + 1)
    private String groupName;
    /**
     * total disk storage in MB
     */
    @FdfsField(order = 1, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private long totalMB;
    /**
     * free disk space in MB
     */
    @FdfsField(order = 2, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private long freeMB;
    /**
     * trunk free space in MB
     */
    @FdfsField(order = 3, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private long trunkFreeMB;
    /**
     * storage server count
     */
    @FdfsField(order = 4, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int storageCount;
    /**
     * storage server port
     */
    @FdfsField(order = 5, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int storagePort;
    /**
     * storage server HTTP port
     */
    @FdfsField(order = 6, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int storageHttpPort;
    /**
     * active storage server count
     */
    @FdfsField(order = 7, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int activeCount;
    /**
     * current storage server index to upload file
     */
    @FdfsField(order = 8, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int currentWriteServer;
    /**
     * store base path count of each storage server
     */
    @FdfsField(order = 9, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int storePathCount;
    /**
     * sub dir count per store path
     */
    @FdfsField(order = 10, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int subdirCountPerPath;
    /**
     * current trunk file id
     */
    @FdfsField(order = 11, maxLength = Constants.FDFS_PROTO_PKG_LEN_SIZE)
    private int currentTrunkFileId;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getTotalMB() {
        return totalMB;
    }

    public void setTotalMB(long totalMB) {
        this.totalMB = totalMB;
    }

    public long getFreeMB() {
        return freeMB;
    }

    public void setFreeMB(long freeMB) {
        this.freeMB = freeMB;
    }

    public long getTrunkFreeMB() {
        return trunkFreeMB;
    }

    public void setTrunkFreeMB(long trunkFreeMB) {
        this.trunkFreeMB = trunkFreeMB;
    }

    public int getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(int storageCount) {
        this.storageCount = storageCount;
    }

    public int getStoragePort() {
        return storagePort;
    }

    public void setStoragePort(int storagePort) {
        this.storagePort = storagePort;
    }

    public int getStorageHttpPort() {
        return storageHttpPort;
    }

    public void setStorageHttpPort(int storageHttpPort) {
        this.storageHttpPort = storageHttpPort;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getCurrentWriteServer() {
        return currentWriteServer;
    }

    public void setCurrentWriteServer(int currentWriteServer) {
        this.currentWriteServer = currentWriteServer;
    }

    public int getStorePathCount() {
        return storePathCount;
    }

    public void setStorePathCount(int storePathCount) {
        this.storePathCount = storePathCount;
    }

    public int getSubdirCountPerPath() {
        return subdirCountPerPath;
    }

    public void setSubdirCountPerPath(int subdirCountPerPath) {
        this.subdirCountPerPath = subdirCountPerPath;
    }

    public int getCurrentTrunkFileId() {
        return currentTrunkFileId;
    }

    public void setCurrentTrunkFileId(int currentTrunkFileId) {
        this.currentTrunkFileId = currentTrunkFileId;
    }


    @Override
    public String toString() {
        return "GroupState{" +
                "groupName='" + groupName +
                ", totalMB=" + totalMB +
                ", freeMB=" + freeMB +
                ", trunkFreeMB=" + trunkFreeMB +
                ", storageCount=" + storageCount +
                ", storagePort=" + storagePort +
                ", storageHttpPort=" + storageHttpPort +
                ", activeCount=" + activeCount +
                ", currentWriteServer=" + currentWriteServer +
                ", storePathCount=" + storePathCount +
                ", subdirCountPerPath=" + subdirCountPerPath +
                ", currentTrunkFileId=" + currentTrunkFileId +
                '}';
    }
}
