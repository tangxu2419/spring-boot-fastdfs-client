package com.vcredit.framework.fastdfs.constant;

/**
 * @author Dong Zhuming
 */
public class ProtocolCommand {

    /**
     * 将quit命令发送到服务器并关闭socket
     */
    public static final byte FDFS_PROTO_CMD_QUIT = 82;
    /**
     * 获取分组信息
     */
    public static final byte TRACKER_PROTO_CMD_SERVER_LIST_GROUP = 91;
    /**
     * 根据组名和IP获取storage节点信息
     */
    public static final byte TRACKER_PROTO_CMD_SERVER_LIST_STORAGE = 92;
    /**
     * 从tracker服务器中删除storage服务器
     */
    public static final byte TRACKER_PROTO_CMD_SERVER_DELETE_STORAGE = 93;
    /**
     * 根据指定groupName获取storage服务节点
     */
    public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE = 101;
    /**
     * 获取storage服务做下载操作
     */
    public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ONE = 102;
    /**
     * 获取storage服务节点做 更新（删除文件/设置metaData）操作
     */
    public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE = 103;
    /**
     * 根据tracker策略获取storage服务节点
     */
    public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE = 104;
    /**
     * 获取所有可以下载的storage服务节点
     */
    public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ALL = 105;
    /**
     * 根据指定groupName获取所有storage服务节点
     */
    public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ALL = 106;
    /**
     * 获取所有storage服务节点做下载操作
     */
    public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ALL = 107;


    /**
     * 服务端正确返回报文状态
     */
    public static final byte TRACKER_PROTO_CMD_RESP = 100;
    /**
     * 连接状态检查命令
     */
    public static final byte FDFS_PROTO_CMD_ACTIVE_TEST = 111;

    /**
     * 上传文件
     */
    public static final byte STORAGE_PROTO_CMD_UPLOAD_FILE = 11;
    /**
     * 删除文件
     */
    public static final byte STORAGE_PROTO_CMD_DELETE_FILE = 12;
    /**
     * set metaData
     */
    public static final byte STORAGE_PROTO_CMD_SET_METADATA = 13;
    /**
     * 下载文件
     */
    public static final byte STORAGE_PROTO_CMD_DOWNLOAD_FILE = 14;
    /**
     * get metaData
     */
    public static final byte STORAGE_PROTO_CMD_GET_METADATA = 15;
    //上传从文件
    public static final byte STORAGE_PROTO_CMD_UPLOAD_SLAVE_FILE = 21;
    //查询文件信息命令
    public static final byte STORAGE_PROTO_CMD_QUERY_FILE_INFO = 22;
    //create appender file
    public static final byte STORAGE_PROTO_CMD_UPLOAD_APPENDER_FILE = 23;
    //append file
    public static final byte STORAGE_PROTO_CMD_APPEND_FILE = 24;
    //modify appender file
    public static final byte STORAGE_PROTO_CMD_MODIFY_FILE = 34;
    //truncate appender file
    public static final byte STORAGE_PROTO_CMD_TRUNCATE_FILE = 36;


}
