package com.vcredit.framework.fastdfs.command.storage.invoker;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.storage.request.StorageSetMetadataRequest;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class SetMetaDataCommandInvoker extends AbstractStorageCommandInvoker {

    public SetMetaDataCommandInvoker(StorageCommand.SetMeta command) {
        this.command = command;
        super.request = new StorageSetMetadataRequest(command.getGroupName(), command.getFileName(), command.getMetaData(), command.getOpFlag());
    }

    @Override
    protected BaseOperationResult parseContent(InputStream in, ProtoHead head, Charset charset) {
        //TODO
        return null;
    }

}
