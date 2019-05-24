/*
 *    Copyright 2019 VCREDIT
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.storage.result.DeleteResult;
import com.vcredit.framework.fastdfs.command.storage.result.DownloadResult;
import com.vcredit.framework.fastdfs.command.storage.result.MetaDataResult;
import com.vcredit.framework.fastdfs.command.storage.result.UploadResult;
import com.vcredit.framework.fastdfs.command.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.command.tracker.result.*;
import com.vcredit.framework.fastdfs.constant.StorageStatus;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;
import com.vcredit.framework.fastdfs.exception.FastdfsException;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dong Zhuming
 */
public class FastdfsClient {

    private static final Logger log = LoggerFactory.getLogger(FastdfsClient.class);

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
            StorageNodeResult storageNodeResult = (StorageNodeResult) TrackerCommand.GetStorage.create()
                    .groupName(groupName)
                    .execute();
            StorageNode storageNode = storageNodeResult.getStorageNode();
            log.debug("storageNode:[{}]", storageNode.toString());
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
            log.error("错误码：{}，错误信息：{}", cse.getErrorCode(), cse.getMessage());
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
            StorageNodeResult storageNodeResult = (StorageNodeResult) TrackerCommand.FetchStorage.create()
                    .groupName(groupName)
                    .fileName(fileName)
                    .toUpdate(true)
                    .execute();
            log.debug("storageNode:[{}]", storageNodeResult.getStorageNode().toString());
            return (MetaDataResult) StorageCommand.GetMeta.create(storageNodeResult.getStorageNode())
                    .groupName(groupName)
                    .fileName(fileName)
                    .execute();
        } catch (CommandStatusException cse) {
            log.error("错误码：{}，错误信息：{}", cse.getErrorCode(), cse.getMessage());
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
            StorageNodeResult storageNodeResult = (StorageNodeResult) TrackerCommand.FetchStorage.create()
                    .groupName(groupName)
                    .fileName(fileName)
                    .toUpdate(true)
                    .execute();
            log.debug("storageNode:[{}]", storageNodeResult.getStorageNode().toString());
            return (DownloadResult) StorageCommand.Download.create(storageNodeResult.getStorageNode())
                    .groupName(groupName)
                    .fileName(fileName)
                    .execute();
        } catch (CommandStatusException cse) {
            log.error("错误码：{}，错误信息：{}", cse.getErrorCode(), cse.getMessage());
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
            StorageNodeResult storageNodeResult = (StorageNodeResult) TrackerCommand.FetchStorage.create()
                    .groupName(groupName)
                    .fileName(fileName)
                    .toUpdate(true)
                    .execute();
            log.debug("storageNode:[{}]", storageNodeResult.getStorageNode().toString());
            return (DeleteResult) StorageCommand.Delete.create(storageNodeResult.getStorageNode())
                    .groupName(groupName)
                    .fileName(fileName)
                    .execute();
        } catch (CommandStatusException cse) {
            log.error("错误码：{}，错误信息：{}", cse.getErrorCode(), cse.getMessage());
            return new DeleteResult(cse);
        }
    }


    /**
     * 获取所有组信息
     */
    public List<GroupState> listGroups() throws InvokeCommandException {
        GroupStateResult groupStateResult = (GroupStateResult) TrackerCommand.ListGroups.create().execute();
        List<GroupState> list = groupStateResult.getList();
        if (null == list || list.isEmpty()) {
            log.warn("未获取到任何组信息");
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    /**
     * 按组列出存储状态
     */
    public List<StorageState> listStorage(String groupName) throws InvokeCommandException {
        StorageStateResult result = (StorageStateResult) TrackerCommand.ListStorage.create()
                .groupName(groupName)
                .execute();
        List<StorageState> list = result.getList();
        if (null == list || list.isEmpty()) {
            log.warn("未获取到任何节点信息");
        }
        return list;
    }

    /**
     * 按组和IP列出存储状态
     */
    public List<StorageState> listStorage(String groupName, String storageIpAddr) throws InvokeCommandException {
        StorageStateResult result = (StorageStateResult) TrackerCommand.ListStorage.create()
                .groupName(groupName)
                .storageIpAddr(storageIpAddr)
                .execute();
        List<StorageState> list = result.getList();
        if (null == list || list.isEmpty()) {
            log.warn("未获取到任何节点信息");
        }
        return list;
    }

    /**
     * 按组和IP列出存储状态
     */
    public void deleteStorage(String groupName, String storageIpAddr) throws InvokeCommandException {
        TrackerCommand.DeleteStorage.create()
                .groupName(groupName)
                .storageIpAddr(storageIpAddr)
                .execute();
    }

}
