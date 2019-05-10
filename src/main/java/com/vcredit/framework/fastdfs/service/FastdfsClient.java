package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.DownLoadResult;
import com.vcredit.framework.fastdfs.proto.UploadResult;
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

    private final StorageClient storageClient;

    public FastdfsClient(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    public UploadResult upload(InputStream inputStream, long fileSize, String fileExtName) {
        return storageClient.uploadFile(inputStream, fileSize, fileExtName, null);
    }

    public DownLoadResult download(String fileName, String groupName) {
        return storageClient.downloadFile(groupName, fileName);
    }

    public DeleteResult delete(String fileName, String groupName) {
        return storageClient.deleteFile(groupName, fileName);
    }


}
