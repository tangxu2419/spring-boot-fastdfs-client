package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.command.tracker.result.StorageNode;
import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;

/**
 * @author dongzhuming
 */
public class StorageConnectionPool extends GenericKeyedObjectPool<FastdfsConnection.ConnectionInfo, FastdfsConnection> {

    public StorageConnectionPool(KeyedPooledObjectFactory<FastdfsConnection.ConnectionInfo, FastdfsConnection> factory, FastdfsProperties properties) {
        super(factory);
        FastdfsProperties.Pool pool = properties.getPool();
        super.setMaxTotal(pool.getMaxTotal());
        // 在空闲时检查有效性
        super.setTestWhileIdle(pool.isTestWhileIdle());
        // 连接耗尽时是否阻塞(默认true)
        super.setBlockWhenExhausted(pool.isBlockWhenExhausted());
        // 获取连接时的最大等待毫秒数100
        super.setMaxWaitMillis(pool.getMaxWaitMillis().toMillis());
        // 视休眠时间超过了180秒的对象为过期
        super.setMinEvictableIdleTimeMillis(pool.getMinEvictableIdleTimeMillis().toMillis());
        // 每过60秒进行一次后台对象清理的行动
        super.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRunsMillis().toMillis());
        // 清理时候检查所有线程
        super.setNumTestsPerEvictionRun(pool.getNumTestsPerEvictionRun());
    }

    public FastdfsConnection borrowObject(StorageNode storageNode) throws Exception {
        FastdfsConnection.ConnectionInfo connectionInfo = new FastdfsConnection.ConnectionInfo();
        connectionInfo.setType(FastdfsConnection.Type.STORAGE);
        connectionInfo.setInetSocketAddress(storageNode.getInetSocketAddress());
        return super.borrowObject(connectionInfo);
    }

    public void returnObject(FastdfsConnection connection) {
        super.returnObject(connection.getConnectionInfo(), connection);
    }
}
