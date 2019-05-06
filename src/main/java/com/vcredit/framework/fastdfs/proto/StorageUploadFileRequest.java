package com.vcredit.framework.fastdfs.proto;

/**
 * @author tangxu
 * @date 2019/5/615:39
 */
public class StorageUploadFileRequest extends FdfsRequest {

    /**
     * 存储节点index
     */
    private byte storeIndex;
    /**
     * 发送文件长度
     */


    private long fileSize;
    /**
     * 文件扩展名
     */
    private String fileExtName;

}
