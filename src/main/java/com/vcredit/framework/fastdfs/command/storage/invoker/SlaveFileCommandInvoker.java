package com.vcredit.framework.fastdfs.command.storage.invoker;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import org.apache.commons.lang3.NotImplementedException;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class SlaveFileCommandInvoker extends AbstractStorageCommandInvoker {
    public SlaveFileCommandInvoker(StorageCommand.SlaveFile command) {
        this.command = command;

    }

    @Override
    protected BaseOperationResult parseContent(InputStream in, ProtoHead head, Charset charset) {
        throw new NotImplementedException("暂未实现");
    }
}
