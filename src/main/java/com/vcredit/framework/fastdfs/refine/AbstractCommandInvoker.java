package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.conn.FdfsConnectionPool;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;
import org.apache.commons.lang3.NotImplementedException;

/**
 * @author dongzhuming
 */
public abstract class AbstractCommandInvoker {

    public static AbstractCommandInvoker prepare(StorageCommand command) {
        if (command instanceof StorageCommand.Upload) {
            return new UploadInvoker((StorageCommand.Upload) command);
        } else {
            //TODO
            throw new NotImplementedException("");
        }
    }

    /**
     * 执行命令
     *
     * @return 操作结果
     */
    public abstract OperationResult action();
}
