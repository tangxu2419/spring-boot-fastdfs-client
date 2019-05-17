package com.vcredit.framework.fastdfs.command.storage.invoker;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.storage.request.StorageDeleteFileRequest;
import com.vcredit.framework.fastdfs.command.storage.result.DeleteResult;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class DeleteCommandInvoker extends AbstractStorageCommandInvoker {

    public DeleteCommandInvoker(StorageCommand.Delete command) {
        this.command = command;
        super.request = new StorageDeleteFileRequest(command.getGroupName(), command.getFileName());
    }

    @Override
    protected BaseOperationResult parseContent(InputStream in, ProtoHead head, Charset charset) {
        return new DeleteResult(head.getStatus());
    }
}
