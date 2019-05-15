package com.vcredit.framework.fastdfs.refine.storage.invoker;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import com.vcredit.framework.fastdfs.proto.storage.StorageUploadFileRequest;
import com.vcredit.framework.fastdfs.refine.storage.AbstractStorageCommandInvoker;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author dongzhuming
 */

public class UploadCommandInvoker extends AbstractStorageCommandInvoker {

    public UploadCommandInvoker(StorageCommand.Upload command) {
        this.command = command;
        super.request = new StorageUploadFileRequest(command.getStorageNode().getStoreIndex(), command.getInputStream(), command.getFileExtension(), command.getFileSize());
    }

    @Override
    protected UploadResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception {
        // 如果有内容
        byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
        String groupName = new String(body, 0, Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
        String filename = new String(body, Constants.FDFS_GROUP_NAME_MAX_LEN, body.length - Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
        return new UploadResult(groupName, filename);
    }
}
