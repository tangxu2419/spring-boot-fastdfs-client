package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.proto.MetaInfo;
import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.DownLoadResult;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Set;

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

    public UploadResult upload(InputStream inputStream, long fileSize, String fileExtName, Set<MetaInfo> metaInfo) {
        return storageClient.uploadFile(inputStream, fileSize, fileExtName, metaInfo);
    }

    public void setMetadata(String groupName, String fileName, Set<MetaInfo> metaInfo) {
        storageClient.setMetadata(groupName, fileName, metaInfo);
    }

    public Set<MetaInfo> getMetadata(String groupName, String fileName) {
        return storageClient.getMetadata(groupName, fileName);
    }

    public DownLoadResult download(String fileName, String groupName) {
        return storageClient.downloadFile(groupName, fileName);
    }

    public DeleteResult delete(String fileName, String groupName) {
        return storageClient.deleteFile(groupName, fileName);
    }


}
