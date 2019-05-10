package com.vcredit.framework.fastdfs.proto.tracker.interactive;

import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.FdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;

/**
 * @author tangxu
 * @date 2019/5/814:40
 */
public class TrackerGetStorageRequest extends FdfsRequest {

    public TrackerGetStorageRequest() {
        super();
        this.head = new ProtoHead(ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE);
    }

}
