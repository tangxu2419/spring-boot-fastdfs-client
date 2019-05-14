package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import com.vcredit.framework.fastdfs.service.StorageClient;
import com.vcredit.framework.fastdfs.service.TrackerClient;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author Dong Zhuming
 */
@Component
public class FastdfsClient2 {

    private final TrackerClient trackerClient;
    private final StorageClient storageClient;

    public FastdfsClient2(TrackerClient trackerClient, StorageClient storageClient) {
        this.trackerClient = trackerClient;
        this.storageClient = storageClient;
    }


    public UploadResult upload(InputStream inputStream, long fileSize, String fileExtension, MetaData metaData) {
        String groupName = null;
        StorageNode node = trackerClient.getStorageNode(groupName);
        OperationResult result = StorageCommand.Upload.create(node)
                .inputStream(inputStream)
                .fileSize(fileSize)
                .metaData(metaData)
                .fileExtension(fileExtension)
                .execute();
        return (UploadResult)result;
    }

}
