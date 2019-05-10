package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.conn.TrackerConnectionManager;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.proto.tracker.TrackerGetFetchStorageCommand;
import com.vcredit.framework.fastdfs.proto.tracker.TrackerGetStorageCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Dong Zhuming
 */
@Component
public class TrackerClient {

    private final TrackerConnectionManager trackerConnectionManager;

    public TrackerClient(TrackerConnectionManager trackerConnectionManager) {
        this.trackerConnectionManager = trackerConnectionManager;
    }


    /**
     * 按组获取存储节点
     */
    public StorageNode getStorageNode(String groupName) {
        TrackerGetStorageCommand command;
        if (StringUtils.isBlank(groupName)) {
            command = new TrackerGetStorageCommand();
        } else {
            command = new TrackerGetStorageCommand(groupName);
        }
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 获取更新服务器
     */
    public StorageNode getUpdateStorage(String groupName, String filename) {
        TrackerGetFetchStorageCommand command = new TrackerGetFetchStorageCommand(groupName, filename, true);
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }


}
