package com.vcredit.framework.fastdfs.command.storage.request;

import com.vcredit.framework.fastdfs.command.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/615:39
 */
public class StorageUploadFileRequest extends AbstractFdfsRequest {

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
     * @param charset 编码
     * @return 请求参数转byte
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] storeIndexBytes = {storeIndex};
        byte[] hexLenBytes = ProtoPackageUtil.long2buff(fileSize);
        // 设置文件后缀名转byte数组，最多转6位
        byte[] extNameBs = encodeRequestParam(fileExtName, Constants.FDFS_FILE_EXT_NAME_MAX_LEN, charset);
        return this.byteMergerAll(storeIndexBytes, hexLenBytes, extNameBs);
    }


    /**
     * 获取参数域长度
     *
     * @return 参数域长度
     */
    @Override
    protected long getBodyLength(Charset charset) {
        param = encodeParam(charset);
        return 1 + Constants.FDFS_PROTO_PKG_LEN_SIZE + Constants.FDFS_FILE_EXT_NAME_MAX_LEN + fileSize;
    }


    @Override
    public long getFileSize() {
        return fileSize;
    }

}
