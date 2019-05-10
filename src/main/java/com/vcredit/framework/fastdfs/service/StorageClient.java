package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.MetaInfo;
import com.vcredit.framework.fastdfs.conn.ConnectionManager;
import com.vcredit.framework.fastdfs.proto.*;
import com.vcredit.framework.fastdfs.proto.storage.StorageDeleteFileCommand;
import com.vcredit.framework.fastdfs.proto.storage.StorageDownloadCommand;
import com.vcredit.framework.fastdfs.proto.storage.StorageUploadFileCommand;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Set;

/**
 * @author Dong Zhuming
 */
@Component
public class StorageClient {

    private final TrackerClient trackerClient;
    private final ConnectionManager connectionManager;

    public StorageClient(TrackerClient trackerClient, ConnectionManager connectionManager) {
        this.trackerClient = trackerClient;
        this.connectionManager = connectionManager;
    }


    /**
     * 上传文件
     *
     * @param inputStream 文件流
     * @param fileSize    文件长度
     * @param fileExtName 文件扩展名
     * @param metaInfo    元数据
     * @return 上传文件结果
     */
    public UploadResult uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaInfo> metaInfo) {
        UploadingFile file;
        if (null == metaInfo) {
            file = new UploadingFile(inputStream, fileSize, fileExtName);
        } else {
            file = new UploadingFile(inputStream, fileSize, fileExtName, metaInfo);
        }
        return updateFile(file);
    }

    /**
     * 文件上传
     *
     * @param file 上传文件对象
     * @return 上传文件结果
     */
    private UploadResult updateFile(UploadingFile file) {
        Validate.notNull(file.getInputStream(), "上传文件流不能为空");
        Validate.notBlank(file.getFileExtName(), "文件扩展名不能为空");
        // 获取存储节点
        StorageNode client = trackerClient.getStorageNode(file.getGroupName());
        // 上传文件
        return uploadFileAndMetaData(client, file.getInputStream(),
                file.getSize(), file.getFileExtName(),
                file.getMetaData());
    }

    /**
     * 上传文件和元数据
     *
     * @param client      storage存储节点
     * @param inputStream 文件流
     * @param fileSize    文件长度
     * @param fileExtName 文件扩展名
     * @param metaDataSet 元数据
     * @return 上传文件结果
     */
    private UploadResult uploadFileAndMetaData(StorageNode client, InputStream inputStream, long fileSize,
                                               String fileExtName, Set<MetaInfo> metaDataSet) {
        // 上传文件
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), inputStream, fileExtName, fileSize);
        UploadResult path = connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
        // 上传metadata
        if (null != metaDataSet && !metaDataSet.isEmpty()) {
            //TODO
//            StorageSetMetadataCommand setMDCommand = new StorageSetMetadataCommand(path.getGroup(), path.getPath(),
//                    metaDataSet, StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
//            connectionManager.executeFdfsCmd(client.getInetSocketAddress(), setMDCommand);
        }
        return path;
    }

    /**
     * 删除文件
     *
     * @param groupName 组名
     * @param filename  文件全路径
     * @return 删除结果
     */
    public DeleteResult deleteFile(String groupName, String filename) {
        // 获取存储节点
        StorageNode client = trackerClient.getUpdateStorage(groupName, filename);
        StorageDeleteFileCommand command = new StorageDeleteFileCommand(groupName, filename);
        return connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
    }


    /**
     * 下载整个文件
     *
     * @param groupName 组名
     * @param path      文件全路径
     * @return 下载结果
     */
    public DownLoadResult downloadFile(String groupName, String path) {
        long fileOffset = 0;
        long fileSize = 0;
        return this.downloadFile(groupName, path, fileOffset, fileSize);
    }

    /**
     * 下载文件片段
     */
    public DownLoadResult downloadFile(String groupName, String path, long fileOffset, long fileSize) {
        StorageNode client = trackerClient.getUpdateStorage(groupName, path);
        StorageDownloadCommand command = new StorageDownloadCommand(groupName, path, fileOffset, fileSize);
        return connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
    }
}
