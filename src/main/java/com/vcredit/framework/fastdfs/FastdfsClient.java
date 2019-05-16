package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.storage.result.DeleteResult;
import com.vcredit.framework.fastdfs.command.storage.result.DownloadResult;
import com.vcredit.framework.fastdfs.command.storage.result.MetaDataResult;
import com.vcredit.framework.fastdfs.command.storage.result.UploadResult;
import com.vcredit.framework.fastdfs.command.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageNode;
import com.vcredit.framework.fastdfs.command.tracker.result.TrackerResult;
import com.vcredit.framework.fastdfs.constant.StorageStatus;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;
import com.vcredit.framework.fastdfs.exception.FastdfsException;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author Dong Zhuming
 */
@Component
public class FastdfsClient {


    /**
     * 下载
     *
     * @param groupName     指定组名
     * @param inputStream   文件输入流
     * @param fileSize      文件长度
     * @param fileExtension 文件后缀名
     * @param metaData      metaData
     * @return UploadResult
     */
    public UploadResult upload(String groupName, InputStream inputStream, long fileSize, String fileExtension, MetaData metaData) throws FastdfsException {
        try {
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
        } catch (CommandStatusException cse) {
            return new UploadResult(cse);
        }
    }

    /**
     * 获取metadata
     *
     * @return metaDataResult
     */
    public MetaDataResult getMetaData(String groupName, String fileName) throws FastdfsException {
        try {
            TrackerResult trackerResult = (TrackerResult) TrackerCommand.FetchStorage.create()
                    .groupName(groupName)
                    .fileName(fileName)
                    .toUpdate(true)
                    .execute();
            return (MetaDataResult) StorageCommand.GetMeta.create(trackerResult.getStorageNode())
                    .groupName(groupName)
                    .fileName(fileName)
                    .execute();
        } catch (CommandStatusException cse) {
            return new MetaDataResult(cse);
        }
    }


    /**
     * 下载
     *
     * @param groupName 组名
     * @param fileName  文件全路径
     * @return DownloadResult
     */
    public DownloadResult download(String groupName, String fileName) throws FastdfsException {
        try {
            TrackerResult storageNode = (TrackerResult) TrackerCommand.FetchStorage.create()
                    .groupName(groupName)
                    .fileName(fileName)
                    .toUpdate(true)
                    .execute();
            return (DownloadResult) StorageCommand.Download.create(storageNode.getStorageNode())
                    .groupName(groupName)
                    .fileName(fileName)
                    .execute();
        } catch (CommandStatusException cse) {
            return new DownloadResult(cse);
        }
    }

    /**
     * 删除
     *
     * @param groupName 组名
     * @param fileName  文件全路径
     * @return DeleteResult
     */
    public DeleteResult delete(String groupName, String fileName) throws FastdfsException {
        try {
            TrackerResult storageNode = (TrackerResult) TrackerCommand.FetchStorage.create()
                    .groupName(groupName)
                    .fileName(fileName)
                    .toUpdate(true)
                    .execute();
            return (DeleteResult) StorageCommand.Delete.create(storageNode.getStorageNode())
                    .groupName(groupName)
                    .fileName(fileName)
                    .execute();
        } catch (CommandStatusException cse) {
            return new DeleteResult(cse);
        }
    }

}
