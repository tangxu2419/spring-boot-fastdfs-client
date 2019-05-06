package com.vcredit.framework.fastdfs;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Future;

/**
 * @author Dong Zhuming
 */
@Component
public class FastdfsClient {

    private final TrackerClient trackerClient;

    public FastdfsClient(TrackerClient trackerClient) {
        this.trackerClient = trackerClient;
    }

    public Future<UploadResult> upload(InputStream inputStream, String groupName) throws IOException {

        final StorageLocation storageLocation = trackerClient.getStorageLocation(groupName);
        final StorageClient storageClient = new StorageClient(storageLocation);
        return storageClient.uploadFile(groupName, inputStream, null, null);
    }

    public OutputStream download(String fileName, String groupName) {
        return null;
    }

    public Future<DeleteResult> delete(String fileName, String groupName) throws IOException {
        return null;
    }


}
