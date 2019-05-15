package com.vcredit.framework.fastdfs.refine.storage.invoker;

import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.storage.StorageDeleteFileRequest;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommandInvoker;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/1510:07
 */
public class DeleteCommandInvoker extends StorageCommandInvoker {

    public DeleteCommandInvoker(StorageCommand.Delete command) {
        this.command = command;
        super.request = new StorageDeleteFileRequest(command.getGroupName(), command.getFileName());
    }

    @Override
    protected OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) {
        //TODO 描述
        return new DeleteResult(head.getStatus());
    }
}
