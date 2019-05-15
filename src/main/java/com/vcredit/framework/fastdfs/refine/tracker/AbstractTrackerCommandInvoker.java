package com.vcredit.framework.fastdfs.refine.tracker;

import com.vcredit.framework.fastdfs.conn.Connection;
import com.vcredit.framework.fastdfs.conn.TrackerConnectionPool;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsConnectionPoolHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Tracker指令
 * @author tangx
 */
public abstract class AbstractTrackerCommandInvoker extends AbstractCommandInvoker {

    private TrackerConnectionPool pool = FastdfsConnectionPoolHolder.TRACKER_CONNECTION_POOL;

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
    public OperationResult action() {
        // 获取链接
        InetSocketAddress address = pool.getTrackerAddress();
        Connection conn = pool.borrow(address);
        return super.execute(pool, address, conn);
    }
}
