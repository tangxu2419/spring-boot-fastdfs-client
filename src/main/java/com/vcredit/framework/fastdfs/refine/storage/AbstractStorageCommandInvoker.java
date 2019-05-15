package com.vcredit.framework.fastdfs.refine.storage;

import com.vcredit.framework.fastdfs.conn.Connection;
import com.vcredit.framework.fastdfs.conn.StorageConnectionPool;
import com.vcredit.framework.fastdfs.exception.FdfsIOException;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsConnectionPoolHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

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
    public OperationResult action() {
        // 获取连接
        StorageNode storageNode = command.getStorageNode();
        Connection conn = pool.borrow(storageNode);

        return super.execute(pool, storageNode.getInetSocketAddress(), conn);
    }

}
