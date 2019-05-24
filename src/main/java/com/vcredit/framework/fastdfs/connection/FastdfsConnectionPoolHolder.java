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

package com.vcredit.framework.fastdfs.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangxu
 */
public class FastdfsConnectionPoolHolder {
    private static final Logger log = LoggerFactory.getLogger(FastdfsConnectionPoolHolder.class);

    public static TrackerConnectionPool TRACKER_CONNECTION_POOL;
    public static StorageConnectionPool STORAGE_CONNECTION_POOL;


    public static void trackerPoolInfo(FastdfsConnection.ConnectionInfo connectionInfo) {
        log.info("==============Begin Tracker Pool Info==========");
        log.info("活动连接{}", TRACKER_CONNECTION_POOL.getNumActive(connectionInfo));
        log.info("空闲连接{}", TRACKER_CONNECTION_POOL.getNumIdle(connectionInfo));
        log.info("连接获取总数统计{}", TRACKER_CONNECTION_POOL.getBorrowedCount());
        log.info("连接返回总数统计{}", TRACKER_CONNECTION_POOL.getReturnedCount());
        log.info("连接销毁总数统计{}", TRACKER_CONNECTION_POOL.getDestroyedCount());
        log.info("==============END Tracker Pool ================");
    }


    public static void storagePoolInfo(FastdfsConnection.ConnectionInfo connectionInfo) {
        log.info("==============Begin Storage Pool Info==========");
        log.info("活动连接{}", STORAGE_CONNECTION_POOL.getNumActive(connectionInfo));
        log.info("空闲连接{}", STORAGE_CONNECTION_POOL.getNumIdle(connectionInfo));
        log.info("连接获取总数统计{}", STORAGE_CONNECTION_POOL.getBorrowedCount());
        log.info("连接返回总数统计{}", STORAGE_CONNECTION_POOL.getReturnedCount());
        log.info("连接销毁总数统计{}", STORAGE_CONNECTION_POOL.getDestroyedCount());
        log.info("==============END Storage Pool ================");
    }

}
