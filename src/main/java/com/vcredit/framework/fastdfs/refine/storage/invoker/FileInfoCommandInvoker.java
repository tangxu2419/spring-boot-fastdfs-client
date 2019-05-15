package com.vcredit.framework.fastdfs.refine.storage.invoker;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;
import com.vcredit.framework.fastdfs.refine.storage.AbstractStorageCommandInvoker;
import org.apache.commons.lang3.NotImplementedException;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/1510:44
 */
public class FileInfoCommandInvoker extends AbstractStorageCommandInvoker {
    public FileInfoCommandInvoker(StorageCommand.FileInfo command) {
        this.command = command;
    }

    @Override
    protected OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception {
        throw new NotImplementedException("暂未实现");
    }
}
