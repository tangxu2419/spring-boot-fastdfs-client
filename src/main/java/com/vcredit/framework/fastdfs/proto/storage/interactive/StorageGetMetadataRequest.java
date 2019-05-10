package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.FdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;

import java.nio.charset.Charset;

/**
 * 查询文件信息命令
 *
 * @author tangxu
 * @date 2019/5/1011:44
 */
public class StorageGetMetadataRequest extends FdfsRequest {


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
