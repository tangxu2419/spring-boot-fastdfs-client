package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.FdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;

import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/917:38
 */
public class StorageDeleteFileRequest extends FdfsRequest {

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
     * @param charset
     * @return
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] bGroupName = encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
        byte[] bPath = path.getBytes(charset);
        return this.byteMergerAll(bGroupName, bPath);
    }

}
