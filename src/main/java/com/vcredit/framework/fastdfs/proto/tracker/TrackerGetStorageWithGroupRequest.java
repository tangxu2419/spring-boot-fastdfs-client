package com.vcredit.framework.fastdfs.proto.tracker;

import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import org.apache.commons.lang3.Validate;

import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class TrackerGetStorageWithGroupRequest extends AbstractFdfsRequest {

    private final String groupName;

    public TrackerGetStorageWithGroupRequest(String groupName) {
        Validate.notBlank(groupName, "分組信息不能為空");
        this.groupName = groupName;
        this.head = new ProtoHead(ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE);
    }

    /**
     * 打包参数
     *
     * @param charset
     * @return
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        return encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
    }

}
