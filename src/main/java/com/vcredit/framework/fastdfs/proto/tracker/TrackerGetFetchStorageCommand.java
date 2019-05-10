package com.vcredit.framework.fastdfs.proto.tracker;

import com.vcredit.framework.fastdfs.proto.AbstractCommand;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.proto.tracker.interactive.TrackerGetFetchStorageRequest;
import com.vcredit.framework.fastdfs.proto.tracker.interactive.TrackerGetFetchStorageResponse;

/**
 * @author tangxu
 * @date 2019/5/917:16
 */
public class TrackerGetFetchStorageCommand extends AbstractCommand<StorageNode> {

    public TrackerGetFetchStorageCommand(String groupName, String path, boolean toUpdate) {
        super.request = new TrackerGetFetchStorageRequest(groupName, path, toUpdate);
        super.response = new TrackerGetFetchStorageResponse();

    }
}
