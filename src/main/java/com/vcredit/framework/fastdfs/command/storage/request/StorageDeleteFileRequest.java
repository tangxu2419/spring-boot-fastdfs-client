package com.vcredit.framework.fastdfs.command.storage.request;

import com.vcredit.framework.fastdfs.command.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;

import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class StorageDeleteFileRequest extends AbstractFdfsRequest {

    /**
     * 组名
     */
    private String groupName;
    /**
     * 路径名
     */
    private String path;

    public StorageDeleteFileRequest(String groupName, String path) {
        super();
        this.groupName = groupName;
        this.path = path;
        this.head = new ProtoHead(ProtocolCommand.STORAGE_PROTO_CMD_DELETE_FILE);
    }

    /**
     * 打包参数
     *
     * @param charset 编码
     * @return byte[]
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] bGroupName = encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
        byte[] bPath = path.getBytes(charset);
        return this.byteMergerAll(bGroupName, bPath);
    }

}
