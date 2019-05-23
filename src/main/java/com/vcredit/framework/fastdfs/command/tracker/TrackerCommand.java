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

package com.vcredit.framework.fastdfs.command.tracker;

import com.vcredit.framework.fastdfs.command.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.FastdfsCommand;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;

/**
 * Tracker指令
 *
 * @author dongzhuming
 */
public class TrackerCommand implements FastdfsCommand {

    /**
     * 执行Fastdfs指令
     *
     * @return 操作结果
     */
    @Override
    public BaseOperationResult execute() throws InvokeCommandException {
        return AbstractCommandInvoker.prepare(this).action();
    }

    /**
     * 获取存储节点命令
     */
    public static class GetStorage extends TrackerCommand {
        /**
         * 组名
         */
        private String groupName;

        public static TrackerCommand.GetStorage create() {
            return new TrackerCommand.GetStorage();
        }

        public TrackerCommand.GetStorage groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }
    }


    /**
     * 获取源服务器
     */
    public static class FetchStorage extends TrackerCommand {
        /**
         * 组名
         */
        private String groupName;
        /**
         * 文件路径
         */
        private String fileName;
        /**
         * 是否更新
         */
        private boolean toUpdate;

        public static TrackerCommand.FetchStorage create() {
            return new TrackerCommand.FetchStorage();
        }

        public TrackerCommand.FetchStorage groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }


        public TrackerCommand.FetchStorage fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public TrackerCommand.FetchStorage toUpdate(boolean toUpdate) {
            this.toUpdate = toUpdate;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getFileName() {
            return fileName;
        }

        public boolean isToUpdate() {
            return toUpdate;
        }
    }


    /**
     * 获取组信息
     */
    public static class ListGroups extends TrackerCommand {

        public static ListGroups create() {
            return new ListGroups();
        }

    }

    /**
     * 列出组
     */
    public static class ListStorage extends TrackerCommand {
        /**
         * 组名
         */
        private String groupName;
        /**
         * 存储服务器ip地址
         */
        private String storageIpAddr;


        public static ListStorage create() {
            return new ListStorage();
        }

        public ListStorage groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public ListStorage storageIpAddr(String storageIpAddr) {
            this.storageIpAddr = storageIpAddr;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getStorageIpAddr() {
            return storageIpAddr;
        }
    }
}
