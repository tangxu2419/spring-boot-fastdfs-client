package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.config.ConnectPoolConfig;
import com.vcredit.framework.fastdfs.exception.FdfsConnectException;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author tangxu
 */
public class StorageConnectionPool extends FdfsConnectionPool {


    private static final Logger log = LoggerFactory.getLogger(StorageConnectionPool.class);

    @SuppressWarnings("unchecked")
    public StorageConnectionPool(KeyedPooledObjectFactory<InetSocketAddress, Connection> factory, ConnectPoolConfig config) {
        super(factory,config);
    }

    /**
     * 获取连接
     */
    public Connection borrow(StorageNode node) {
        InetSocketAddress address;
        Connection conn;
        // 获取连接
        try {
            address = node.getInetSocketAddress();
            log.debug("获取到Tracker连接地址{}", address);
            conn = this.borrowObject(address);
        } catch (FdfsConnectException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to borrow buffer from pool", e);
            throw new RuntimeException("Unable to borrow buffer from pool", e);
        }
        return conn;
    }

}
