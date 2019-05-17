package com.vcredit.framework.fastdfs.command.storage.request;

import com.vcredit.framework.fastdfs.command.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;

import java.nio.charset.Charset;

/**
 * 查询文件信息命令
 *
 * @author tangxu
 */
public class StorageGetMetadataRequest extends AbstractFdfsRequest {


    /**
     * 组名
     */
    private String groupName;
    /**
     * 路径名
     */
    private String path;

    public StorageGetMetadataRequest(String groupName, String filename) {
        super();
        this.groupName = groupName;
        this.path = filename;
        this.head = new ProtoHead(ProtocolCommand.STORAGE_PROTO_CMD_GET_METADATA);
    }

    /**
     * 打包参数
     *
     * @param charset 编码
     * @return 请求参数转byte
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] bGroupName = encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
        byte[] bPath = path.getBytes(charset);
        return this.byteMergerAll(bGroupName, bPath);
    }
}
