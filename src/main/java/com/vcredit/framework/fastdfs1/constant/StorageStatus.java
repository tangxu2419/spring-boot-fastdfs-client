/*
 *    Copyright 2019 vcredit
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

package com.vcredit.framework.fastdfs.constant;

/**
 * @author Dong Zhuming
 */
public class StorageStatus {

    /**
     * for overwrite all old metadata
     */
    public static final byte STORAGE_SET_METADATA_FLAG_OVERWRITE = 'O';
    /**
     * for replace, insert when the meta item not exist, otherwise update it
     */
    public static final byte STORAGE_SET_METADATA_FLAG_MERGE = 'M';

    /**
     * 初始化，尚未得到同步已有数据的源服务器
     */
    public static final byte FDFS_STORAGE_STATUS_INIT = 0;
    /**
     * 等待同步，已得到同步已有数据的源服务器
     */
    public static final byte FDFS_STORAGE_STATUS_WAIT_SYNC = 1;
    /**
     * 同步中
     */
    public static final byte FDFS_STORAGE_STATUS_SYNCING = 2;
    public static final byte FDFS_STORAGE_STATUS_IP_CHANGED = 3;
    /**
     * 已删除，该服务器从本组中摘除
     */
    public static final byte FDFS_STORAGE_STATUS_DELETED = 4;
    /**
     * 离线
     */
    public static final byte FDFS_STORAGE_STATUS_OFFLINE = 5;
    /**
     * 在线，尚不能提供服务
     */
    public static final byte FDFS_STORAGE_STATUS_ONLINE = 6;
    /**
     * 在线，可以提供服务
     */
    public static final byte FDFS_STORAGE_STATUS_ACTIVE = 7;
    public static final byte FDFS_STORAGE_STATUS_NONE = 99;


}
