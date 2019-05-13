package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import org.apache.commons.lang3.NotImplementedException;

/**
 * @author dongzhuming
 */
public abstract class AbstractCommandInvoker {


    public static AbstractCommandInvoker prepare(FastdfsCommand command) {
        if(command instanceof FastdfsCommand.Upload) {
            return new UploadInvoker((FastdfsCommand.Upload)command);
        } else {
            //TODO
            throw new NotImplementedException("");
        }
    }

    /**
     * 执行命令
     * @return 操作结果
     */
    abstract OperationResult action();
}
