package com.vcredit.framework.fastdfs.command.tracker.invoker;

import com.vcredit.framework.fastdfs.command.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.connection.FastdfsConnection;
import com.vcredit.framework.fastdfs.connection.FastdfsConnectionPoolHolder;
import com.vcredit.framework.fastdfs.connection.TrackerConnectionPool;
import com.vcredit.framework.fastdfs.exception.FastdfsConnectionException;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;

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
    public BaseOperationResult action() throws InvokeCommandException {
        FastdfsConnection conn;
        try {
            conn = pool.borrowObject();
        } catch (Exception e) {
            throw new FastdfsConnectionException(e);
        }
        try {
            pool.markAsActive(conn.getInetSocketAddress());
            return super.execute(conn);
        } catch (IOException e) {
            pool.markAsProblem(conn.getInetSocketAddress());
            throw new FastdfsConnectionException(e);
        } finally {
            pool.returnObject(conn);
        }
    }

}
