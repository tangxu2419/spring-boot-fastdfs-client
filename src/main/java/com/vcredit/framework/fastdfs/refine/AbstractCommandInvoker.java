package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import org.apache.commons.lang3.NotImplementedException;

/**
 * @author dongzhuming
 */
public abstract class AbstractCommandInvoker {

    private Connection connection;

    protected Connection initTrackerConnection(){

    }
    protected Connection initStorageConnection(){

    }

    public static AbstractCommandInvoker prepare(StorageCommand command) {
        if(command instanceof StorageCommand.Upload) {
            return new UploadInvoker((StorageCommand.Upload)command);
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
