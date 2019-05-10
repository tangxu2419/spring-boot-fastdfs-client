package com.vcredit.framework.fastdfs.proto.tracker;

import com.vcredit.framework.fastdfs.proto.AbstractCommand;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.proto.tracker.interactive.TrackerGetStorageRequest;
import com.vcredit.framework.fastdfs.proto.tracker.interactive.TrackerGetStorageResponse;
import com.vcredit.framework.fastdfs.proto.tracker.interactive.TrackerGetStoreStorageWithGroupRequest;

/**
 * @author tangxu
 * @date 2019/5/814:28
 */
public class TrackerGetStorageCommand extends AbstractCommand<StorageNode> {

    public TrackerGetStorageCommand(String groupName) {
        super.request = new TrackerGetStoreStorageWithGroupRequest(groupName);
        super.response = new TrackerGetStorageResponse();
    }

    public TrackerGetStorageCommand() {
        super.request = new TrackerGetStorageRequest();
        super.response = new TrackerGetStorageResponse();
    }

}
