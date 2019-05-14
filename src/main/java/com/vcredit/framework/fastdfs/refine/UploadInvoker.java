package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageUploadFileRequest;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageUploadFileResponse;

/**
 * @author dongzhuming
 */

public class UploadInvoker extends StorageCommandInvoker {

    private StorageUploadFileRequest request;
    private StorageUploadFileResponse response;
    private final StorageCommand.Upload command;


    public UploadInvoker(StorageCommand.Upload upload) {
        super();
        this.command = upload;
    }

    @Override
    OperationResult action() {
        return null;
    }
}
