package com.vcredit.framework.fastdfs.refine.tracker.invoker;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.proto.TrackerResult;
import com.vcredit.framework.fastdfs.proto.tracker.TrackerGetStorageRequest;
import com.vcredit.framework.fastdfs.proto.tracker.TrackerGetStorageWithGroupRequest;
import com.vcredit.framework.fastdfs.refine.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.refine.tracker.AbstractTrackerCommandInvoker;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

import static com.vcredit.framework.fastdfs.constants.Constants.*;

/**
 * @author tangxu
 */
public class GetStorageCommandInvoker extends AbstractTrackerCommandInvoker {

    public GetStorageCommandInvoker(TrackerCommand.GetStorage command) {
        this.command = command;
        if (StringUtils.isBlank(command.getGroupName())) {
            super.request = new TrackerGetStorageRequest();
        } else {
            super.request = new TrackerGetStorageWithGroupRequest(command.getGroupName());
        }
    }

    @Override
    protected OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception {
        byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
        String groupName = new String(body, 0, FDFS_GROUP_NAME_MAX_LEN, charset).trim();
        String ip = new String(body, FDFS_GROUP_NAME_MAX_LEN, FDFS_IPADDR_SIZE - 1, charset).trim();
        int port = (int) ProtoPackageUtil.buff2long(body, FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE - 1);
        byte storePath = body[TRACKER_QUERY_STORAGE_STORE_BODY_LEN - 1];
        return new TrackerResult(new StorageNode(groupName, ip, port, storePath));
    }
}
