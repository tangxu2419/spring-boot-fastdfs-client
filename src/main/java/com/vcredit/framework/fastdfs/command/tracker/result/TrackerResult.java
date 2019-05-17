package com.vcredit.framework.fastdfs.command.tracker.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;

/**
 * @author tangxu
 */
public class TrackerResult extends BaseOperationResult {
    private StorageNode storageNode;

    public TrackerResult(StorageNode storageNode) {
        this.storageNode = storageNode;
    }

    public TrackerResult(CommandStatusException exception) {
        setErrorCode(exception.getErrorCode());
        setErrorMessage(exception.getMessage());
    }

    public StorageNode getStorageNode() {
        return storageNode;
    }
}
