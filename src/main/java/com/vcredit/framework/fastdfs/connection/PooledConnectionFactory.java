package com.vcredit.framework.fastdfs.connection;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.InetSocketAddress;

public class PooledConnectionFactory extends BaseKeyedPooledObjectFactory<FastdfsConnection.ConnectionInfo, FastdfsConnection> {

    @Override
    public FastdfsConnection create(FastdfsConnection.ConnectionInfo key) throws Exception {
        switch (key.getType()) {
            case TRACKER:
                return createTrackerConnection(key.getInetSocketAddress());
            case STORAGE:
                return createStorageConnection(key.getInetSocketAddress());
            default:
                throw new Exception("Illegal Connection Type:" + key);
        }
    }

    private FastdfsConnection createTrackerConnection(InetSocketAddress inetSocketAddress) {
        return new FastdfsConnection(inetSocketAddress);
    }

    private FastdfsConnection createStorageConnection(InetSocketAddress inetSocketAddress) {
        return new FastdfsConnection(inetSocketAddress);
    }

    @Override
    public PooledObject<FastdfsConnection> wrap(FastdfsConnection connection) {
        return new DefaultPooledObject<>(connection);
    }
}
