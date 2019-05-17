package com.vcredit.framework.fastdfs.command.tracker.request;

import com.vcredit.framework.fastdfs.command.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;

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
