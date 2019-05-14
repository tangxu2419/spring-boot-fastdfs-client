package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.exception.FdfsIOException;
import com.vcredit.framework.fastdfs.proto.FdfsCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author tangxu
 */
@Component
public class ConnectionManager {

    protected static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    public ConnectionManager(@Qualifier("fdfsConnectionPool") FdfsConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * 连接池
     */
    private FdfsConnectionPool pool;

    public void setPool(FdfsConnectionPool pool) {
        this.pool = pool;
    }

    public FdfsConnectionPool getPool() {
        return pool;
    }

    /**
     * 获取连接并执行交易
     *
     * @param address
     * @param command
     * @return
     */
    public <T> T executeFdfsCmd(InetSocketAddress address, FdfsCommand<T> command) {
        // 获取连接
        Connection conn = getConnection(address);
        // 执行交易
        return execute(address, conn, command);

    }

    /**
     * 执行交易
     *
     * @param conn
     * @param command
     * @return
     */
    protected <T> T execute(InetSocketAddress address, Connection conn, FdfsCommand<T> command) {
        try {
            // 执行交易
            log.debug("对地址{}发出交易请求{}", address, command.getClass().getSimpleName());
            return command.execute(conn);
        } catch (FdfsIOException e) {
            throw e;
        } catch (Exception e) {
            log.error("execute fdfs command error", e);
            throw new RuntimeException("execute fdfs command error", e);
        } finally {
            try {
                if (null != conn) {
                    //移除pool
                    pool.returnObject(address, conn);
                }
            } catch (Exception e) {
                log.error("return pooled connection error", e);
            }
        }
    }

    /**
     * 获取连接
     *
     * @param address
     * @return
     */
    protected Connection getConnection(InetSocketAddress address) {
        Connection conn;
        try {
            // 获取连接
            conn = pool.borrowObject(address);
        } catch (Exception e) {
            log.error("Unable to borrow buffer from pool", e);
            throw new RuntimeException("Unable to borrow buffer from pool", e);
        }
        return conn;
    }


}
