package com.vcredit.framework.fastdfs.proto;

/**
 * @author tangxu
 * @date 2019/5/1511:39
 */
public class TrackerResult extends OperationResult {
    private StorageNode storageNode;

    public TrackerResult(StorageNode storageNode) {
        this.storageNode = storageNode;
    }

    public StorageNode getStorageNode() {
        return storageNode;
    }
}
