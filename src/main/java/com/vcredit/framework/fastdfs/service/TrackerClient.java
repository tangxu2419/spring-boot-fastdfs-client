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

import com.vcredit.framework.fastdfs.command.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.command.tracker.result.GroupState;
import com.vcredit.framework.fastdfs.command.tracker.result.GroupStateResult;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageState;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageStateResult;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Tracker 客户端接口
 *
 * @author tangxu
 */
public class TrackerClient {

    private static final Logger log = LoggerFactory.getLogger(TrackerClient.class);

    /**
     * 获取所有组信息
     */
    public List<GroupState> listGroups() throws InvokeCommandException {
        GroupStateResult groupStateResult = (GroupStateResult) TrackerCommand.ListGroups.create().execute();
        List<GroupState> list = groupStateResult.getList();
        if (null == list || list.isEmpty()) {
            log.warn("未获取到任何组信息");
        }
        return list;
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
}
