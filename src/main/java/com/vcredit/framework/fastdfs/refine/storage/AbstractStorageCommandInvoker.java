package com.vcredit.framework.fastdfs.refine.storage;

import com.vcredit.framework.fastdfs.connection.FastdfsConnection;
import com.vcredit.framework.fastdfs.connection.StorageConnectionPool;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsConnectionPoolHolder;

/**
 * @author tangx
 */
public abstract class AbstractStorageCommandInvoker extends AbstractCommandInvoker {


    private StorageConnectionPool pool = FastdfsConnectionPoolHolder.STORAGE_CONNECTION_POOL;

    /**
     * storage指令
     */
    protected StorageCommand command;

    /**
     * 解析反馈消息对象
     */
    @Deprecated
    protected OperationResult response;

    /**
     * 执行指令交易
     *
     * @return 结果
     */
    @Override
    public OperationResult action() throws InvokeCommandException {
        FastdfsConnection conn = null;
        try {
            StorageNode storageNode = command.getStorageNode();
            conn = pool.borrowObject(storageNode);
            return super.execute(conn);
        } catch (Exception e) {
            throw new InvokeCommandException(e);
        } finally {
            pool.returnObject(conn);
        }
    }

}
