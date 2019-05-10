package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.FdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;

import java.nio.charset.Charset;

/**
 * 文件下载请求
 *
 * @author tangxu
 * @date 2019/5/918:13
 */
public class StorageDownloadRequest extends FdfsRequest {


    /**
     * 开始位置
     */
    private long fileOffset;
    /**
     * 读取文件长度
     */
    private long downloadBytes;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 文件路径
     */
    private String path;

    public StorageDownloadRequest(String groupName, String path, long fileOffset, long downloadBytes) {
        super();
        this.groupName = groupName;
        this.downloadBytes = downloadBytes;
        this.path = path;
        this.fileOffset = fileOffset;
        head = new ProtoHead(ProtocolCommand.STORAGE_PROTO_CMD_DOWNLOAD_FILE);
    }

    /**
     * 打包参数
     *
     * @param charset
     * @return
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] bsOffset = ProtoPackageUtil.long2buff(fileOffset);
        byte[] bsDownBytes = ProtoPackageUtil.long2buff(downloadBytes);
        byte[] bGroupName = encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
        byte[] bPath = path.getBytes(charset);
        return this.byteMergerAll(bsOffset, bsDownBytes, bGroupName, bPath);
    }

}
