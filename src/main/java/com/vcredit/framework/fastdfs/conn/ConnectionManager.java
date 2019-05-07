package com.vcredit.framework.fastdfs.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author tangxu
 * @date 2019/5/715:03
 */
@Component
public class ConnectionManager {

    protected static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    public ConnectionManager() {
        super();
    }

    public ConnectionManager(FdfsConnectionPool pool) {
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
