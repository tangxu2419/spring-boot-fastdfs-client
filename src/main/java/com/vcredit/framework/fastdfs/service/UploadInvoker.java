package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageUploadFileRequest;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageUploadFileResponse;

/**
 * @author dongzhuming
 */

public class UploadInvoker extends AbstractCommandInvoker {

    private StorageUploadFileRequest request;
    private StorageUploadFileResponse response;
    private final FastdfsCommand.Upload command;


    public UploadInvoker(FastdfsCommand.Upload upload) {
        this.command = upload;
    }

    @Override
    OperationResult action() {
        return null;
    }
}
