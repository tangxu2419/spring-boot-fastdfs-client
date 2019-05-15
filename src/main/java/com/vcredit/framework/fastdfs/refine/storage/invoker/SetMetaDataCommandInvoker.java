package com.vcredit.framework.fastdfs.refine.storage.invoker;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.storage.StorageSetMetadataRequest;
import com.vcredit.framework.fastdfs.refine.storage.AbstractStorageCommandInvoker;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/1510:06
 */
public class SetMetaDataCommandInvoker extends AbstractStorageCommandInvoker {

    public SetMetaDataCommandInvoker(StorageCommand.SetMeta command) {
        this.command = command;
        super.request = new StorageSetMetadataRequest(command.getGroupName(), command.getFileName(), command.getMetaData(), command.getOpFlag());
    }

    @Override
    protected OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) {
        //TODO
        return null;
    }

}
