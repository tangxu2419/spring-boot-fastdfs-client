package com.vcredit.framework.fastdfs.refine.tracker.invoker;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.proto.TrackerResult;
import com.vcredit.framework.fastdfs.proto.tracker.TrackerGetFetchStorageRequest;
import com.vcredit.framework.fastdfs.refine.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.refine.tracker.TrackerCommandInvoker;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class FetchStorageCommandInvoker extends TrackerCommandInvoker {

    public FetchStorageCommandInvoker(TrackerCommand.FetchStorage command) {
        this.command = command;
        super.request = new TrackerGetFetchStorageRequest(command.getGroupName(), command.getFileName(), command.isToUpdate());
    }

    @Override
    protected OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception {
        if (head.getContentLength() > 0) {
            byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
            String groupName = new String(body, 0, Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
            String ip = new String(body, Constants.FDFS_GROUP_NAME_MAX_LEN, Constants.FDFS_IPADDR_SIZE - 1, charset).trim();
            int port = (int) ProtoPackageUtil.buff2long(body, Constants.FDFS_GROUP_NAME_MAX_LEN + Constants.FDFS_IPADDR_SIZE - 1);
            return new TrackerResult(new StorageNode(groupName, ip, port));
        }
        return null;
    }
}
