package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import com.vcredit.framework.fastdfs.exception.FastdfsConnectionException;
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
    public FastdfsConnection create(FastdfsConnection.ConnectionInfo key) throws FastdfsConnectionException, IOException {
        switch (key.getType()) {
            case TRACKER:
            case STORAGE:
                return new FastdfsConnection(key, soTimeout, connectTimeout);
            default:
                throw new FastdfsConnectionException("Illegal Connection Type:" + key);
        }
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
