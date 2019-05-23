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

package com.vcredit.framework.fastdfs.constant;

/**
 * @author Dong Zhuming
 */
public class Constants {

    public static final int FDFS_PROTO_CONNECTION_LEN = 4;
    public static final int FDFS_PROTO_PKG_LEN_SIZE = 8;
    public static final int FDFS_PROTO_CMD_SIZE = 1;
    public static final int FDFS_GROUP_NAME_MAX_LEN = 16;
    public static final int FDFS_IPADDR_SIZE = 16;
    public static final int FDFS_DOMAIN_NAME_MAX_SIZE = 128;
    public static final int FDFS_VERSION_SIZE = 6;
    public static final int FDFS_STORAGE_ID_MAX_SIZE = 16;
    public static final String FDFS_RECORD_SEPERATOR = "\u0001";
    public static final String FDFS_FIELD_SEPERATOR = "\u0002";
    public static final int TRACKER_QUERY_STORAGE_FETCH_BODY_LEN = FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE - 1 + FDFS_PROTO_PKG_LEN_SIZE;
    public static final int TRACKER_QUERY_STORAGE_STORE_BODY_LEN = FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE + FDFS_PROTO_PKG_LEN_SIZE;
    public static final byte FDFS_FILE_EXT_NAME_MAX_LEN = 6;
    public static final byte FDFS_FILE_PREFIX_MAX_LEN = 16;
    public static final byte FDFS_FILE_PATH_LEN = 10;
    public static final byte FDFS_FILENAME_BASE64_LENGTH = 27;
    public static final byte FDFS_TRUNK_FILE_INFO_LEN = 16;
    public static final byte ERR_NO_ENOENT = 2;
    public static final byte ERR_NO_EIO = 5;
    public static final byte ERR_NO_EBUSY = 16;
    public static final byte ERR_NO_EINVAL = 22;
    public static final byte ERR_NO_ENOSPC = 28;
    public static final byte ECONNREFUSED = 61;
    public static final byte ERR_NO_EALREADY = 114;
    public static final long INFINITE_FILE_SIZE = 256 * 1024L * 1024 * 1024 * 1024 * 1024L;
    public static final long APPENDER_FILE_SIZE = INFINITE_FILE_SIZE;
    public static final long TRUNK_FILE_MARK_SIZE = 512 * 1024L * 1024 * 1024 * 1024 * 1024L;
    public static final long NORMAL_LOGIC_FILENAME_LENGTH = FDFS_FILE_PATH_LEN + FDFS_FILENAME_BASE64_LENGTH + FDFS_FILE_EXT_NAME_MAX_LEN + 1;
    public static final long TRUNK_LOGIC_FILENAME_LENGTH = NORMAL_LOGIC_FILENAME_LENGTH + FDFS_TRUNK_FILE_INFO_LEN;
    public static final int PROTO_HEADER_CMD_INDEX = FDFS_PROTO_PKG_LEN_SIZE;
    public static final int PROTO_HEADER_STATUS_INDEX = FDFS_PROTO_PKG_LEN_SIZE + 1;
}
