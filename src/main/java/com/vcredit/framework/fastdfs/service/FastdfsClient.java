package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.constants.StorageStatus;
import com.vcredit.framework.fastdfs.proto.*;
import com.vcredit.framework.fastdfs.refine.MetaData;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;
import com.vcredit.framework.fastdfs.refine.tracker.TrackerCommand;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author Dong Zhuming
 */
@Component
public class FastdfsClient {


    /**
     * 下载
     * @param groupName     指定组名
     * @param inputStream   文件输入流
     * @param fileSize      文件长度
     * @param fileExtension 文件后缀名
     * @param metaData      metaData
     * @return  UploadResult
     */
    public UploadResult upload(String groupName, InputStream inputStream, long fileSize, String fileExtension, MetaData metaData) {
        TrackerResult trackerResult = (TrackerResult) TrackerCommand.GetStorage.create()
                .groupName(groupName)
                .execute();
        StorageNode storageNode = trackerResult.getStorageNode();

        UploadResult result = (UploadResult) StorageCommand.Upload.create(storageNode)
                .inputStream(inputStream)
                .fileSize(fileSize)
                .fileExtension(fileExtension)
                .execute();
        if (null != metaData) {
            StorageCommand.SetMeta.create(storageNode)
                    .groupName(result.getGroupName())
                    .fileName(result.getFileName())
                    .metaData(metaData)
                    .opFlag(StorageStatus.STORAGE_SET_METADATA_FLAG_OVERWRITE)
                    .execute();
        }
        return result;
    }

    /**
     * 获取metadata
     *
     * @return metaDataResult
     */
    public MetaDataResult getMetaData(String groupName, String fileName) {
        TrackerResult trackerResult = (TrackerResult) TrackerCommand.FetchStorage.create()
                .groupName(groupName)
                .fileName(fileName)
                .toUpdate(true)
                .execute();
        return (MetaDataResult) StorageCommand.GetMeta.create(trackerResult.getStorageNode())
                .groupName(groupName)
                .fileName(fileName)
                .execute();
    }


    /**
     * 下载
     *
     * @param groupName 组名
     * @param fileName  文件全路径
     * @return DownLoadResult
     */
    public DownLoadResult download(String groupName, String fileName) {
        TrackerResult storageNode = (TrackerResult) TrackerCommand.FetchStorage.create()
                .groupName(groupName)
                .fileName(fileName)
                .toUpdate(true)
                .execute();
        return (DownLoadResult) StorageCommand.Download.create(storageNode.getStorageNode())
                .groupName(groupName)
                .fileName(fileName)
                .execute();
    }

    /**
     * 删除
     *
     * @param groupName 组名
     * @param fileName  文件全路径
     * @return DeleteResult
     */
    public DeleteResult delete(String groupName, String fileName) {
        TrackerResult storageNode = (TrackerResult) TrackerCommand.FetchStorage.create()
                .groupName(groupName)
                .fileName(fileName)
                .toUpdate(true)
                .execute();
        return (DeleteResult) StorageCommand.Delete.create(storageNode.getStorageNode())
                .groupName(groupName)
                .fileName(fileName)
                .execute();
    }

}
