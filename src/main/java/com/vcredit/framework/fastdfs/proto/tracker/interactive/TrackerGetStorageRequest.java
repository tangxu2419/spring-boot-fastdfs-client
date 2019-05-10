package com.vcredit.framework.fastdfs.proto.tracker.interactive;

import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;

import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/814:40
 */
public class TrackerGetStorageRequest extends AbstractFdfsRequest {

    public TrackerGetStorageRequest() {
        super();
        this.head = new ProtoHead(ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE);
    }

    @Override
    public byte[] encodeParam(Charset charset) {
        return null;
    }
}
