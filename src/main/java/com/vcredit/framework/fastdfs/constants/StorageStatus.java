package com.vcredit.framework.fastdfs.constants;

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


    public static final byte FDFS_STORAGE_STATUS_INIT = 0;
    public static final byte FDFS_STORAGE_STATUS_WAIT_SYNC = 1;
    public static final byte FDFS_STORAGE_STATUS_SYNCING = 2;
    public static final byte FDFS_STORAGE_STATUS_IP_CHANGED = 3;
    public static final byte FDFS_STORAGE_STATUS_DELETED = 4;
    public static final byte FDFS_STORAGE_STATUS_OFFLINE = 5;
    public static final byte FDFS_STORAGE_STATUS_ONLINE = 6;
    public static final byte FDFS_STORAGE_STATUS_ACTIVE = 7;
    public static final byte FDFS_STORAGE_STATUS_NONE = 99;


}
