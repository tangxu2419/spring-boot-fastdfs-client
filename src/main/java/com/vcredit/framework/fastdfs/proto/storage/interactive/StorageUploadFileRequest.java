package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.FdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;

import java.io.InputStream;
import java.nio.charset.Charset;

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

    /**
     * 构造函数
     *
     * @param inputStream 文件流
     * @param fileExtName 文件扩展名
     * @param fileSize    发送文件长度
     * @param storeIndex  存储节点index
     */
    public StorageUploadFileRequest(byte storeIndex, InputStream inputStream, String fileExtName, long fileSize) {
        super();
        this.inputFile = inputStream;
        this.fileSize = fileSize;
        this.storeIndex = storeIndex;
        this.fileExtName = fileExtName;
        head = new ProtoHead(ProtocolCommand.STORAGE_PROTO_CMD_UPLOAD_FILE);
    }

    /**
     * 打包参数
     *
     * @param charset
     * @return
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        //初始化storage节点信息以及文件长度信息
//        byte[] sizeBytes = new byte[1 + Constants.FDFS_PROTO_PKG_LEN_SIZE];
//        sizeBytes[0] = storeIndex;
//        byte[] hexLenBytes = ProtoPackageUtil.long2buff(fileSize);
//        System.arraycopy(hexLenBytes, 0, sizeBytes, 1, hexLenBytes.length);
        byte[] storeIndexBytes = {storeIndex};
        byte[] hexLenBytes = ProtoPackageUtil.long2buff(fileSize);
        // 设置文件后缀名转byte数组，最多转6位
        byte[] extNameBs = encodeRequestParam(fileExtName, Constants.FDFS_FILE_EXT_NAME_MAX_LEN, charset);
        return this.byteMergerAll(storeIndexBytes, hexLenBytes, extNameBs);
    }


    @Override
    public long getFileSize() {
        return fileSize;
    }

}
