package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;


/**
 * @author tangxu
 */
public class PooledConnectionFactory extends BaseKeyedPooledObjectFactory<FastdfsConnection.ConnectionInfo, FastdfsConnection> {
    /**
     * 读取时间
     */
    private int soTimeout;
    /**
     * 连接超时时间
     */
    private int connectTimeout;

    public PooledConnectionFactory(FastdfsProperties properties) {
        this.soTimeout = properties.getSoTimeout().toMillisPart();
        this.connectTimeout = properties.getConnectTimeout().toMillisPart();
    }

    @Override
    public FastdfsConnection create(FastdfsConnection.ConnectionInfo key) throws Exception {
        switch (key.getType()) {
            case TRACKER:
                return createTrackerConnection(key);
            case STORAGE:
                return createStorageConnection(key);
            default:
                throw new Exception("Illegal Connection Type:" + key);
        }
    }

    private FastdfsConnection createTrackerConnection(FastdfsConnection.ConnectionInfo connectionInfo) throws IOException {
        ;
        return new FastdfsConnection(connectionInfo, soTimeout, connectTimeout);
    }

    private FastdfsConnection createStorageConnection(FastdfsConnection.ConnectionInfo connectionInfo) throws IOException {
        return new FastdfsConnection(connectionInfo, soTimeout, connectTimeout);
    }

    @Override
    public PooledObject<FastdfsConnection> wrap(FastdfsConnection connection) {
        return new DefaultPooledObject<>(connection);
    }


    @Override
    public void destroyObject(FastdfsConnection.ConnectionInfo key, PooledObject<FastdfsConnection> connection) {
        connection.getObject().close();
    }

    @Override
    public boolean validateObject(FastdfsConnection.ConnectionInfo key, PooledObject<FastdfsConnection> connection) {
        return connection.getObject().isValid();
    }
}
