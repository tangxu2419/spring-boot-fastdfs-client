package com.vcredit.framework.fastdfs.refine.tracker;

import com.vcredit.framework.fastdfs.connection.FastdfsConnection;
import com.vcredit.framework.fastdfs.connection.TrackerConnectionPool;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsConnectionPoolHolder;

import java.io.IOException;

/**
 * Tracker指令
 *
 * @author tangx
 */
public abstract class AbstractTrackerCommandInvoker extends AbstractCommandInvoker {

    private final static TrackerConnectionPool pool = FastdfsConnectionPoolHolder.TRACKER_CONNECTION_POOL;

    /**
     * storage指令
     */
    protected TrackerCommand command;

    /**
     * 执行指令交易
     *
     * @return 结果
     */
    @Override
    public OperationResult action() throws Exception {
        FastdfsConnection conn = null;
        try {
            conn = pool.borrowObject();
            pool.markAsActive(conn.getInetSocketAddress());
            return super.execute(conn);
        } catch (IOException e) {
            if (null != conn) {
                pool.markAsProblem(conn.getInetSocketAddress());
            }
            throw e;
        } finally {
            pool.returnObject(conn);
        }
    }

}
