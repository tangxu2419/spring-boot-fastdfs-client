package com.vcredit.framework.fastdfs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * @author Dong Zhuming
 */
public class StorageClient {

    private final StorageLocation storageLocation;

    public StorageClient(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Future<UploadResult> uploadFile(InputStream inputStream, String extension, MetaInfo[] metaInfo) throws IOException {
        return uploadFile(null, inputStream, extension, metaInfo);
    }

    public Future<UploadResult> uploadFile(String groupName, InputStream inputStream, String extension, MetaInfo[] metaInfo) throws IOException {
        //TODO
        return null;
    }
}
