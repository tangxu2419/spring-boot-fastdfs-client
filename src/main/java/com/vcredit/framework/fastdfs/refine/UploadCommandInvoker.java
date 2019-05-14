package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageUploadFileRequest;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommandInvoker;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author dongzhuming
 */

public class UploadCommandInvoker extends StorageCommandInvoker {

    private final StorageCommand.Upload command;

    UploadCommandInvoker(StorageCommand.Upload upload) {
        this.command = upload;
        super.request = new StorageUploadFileRequest(command.getStorageNode().getStoreIndex(), command.getInputStream(), command.getFileExtension(), command.getFileSize());
    }

    @Override
    public OperationResult action() {
        return super.execute(FastdfsConnectionPoolHolder.STORAGE_CONNECTION_POOL.borrow(command.getStorageNode()));
    }

    @Override
    protected OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception {
        // 如果有内容
        if (head.getContentLength() > 0) {
            byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
            String groupName = new String(body, 0, Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
            String filename = new String(body, Constants.FDFS_GROUP_NAME_MAX_LEN, body.length - Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
            return new UploadResult(groupName, filename);
        }
        //TODO
        return null;
    }
}
