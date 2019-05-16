package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzhuming
 */
public class StorageConnectionPool extends GenericKeyedObjectPool<FastdfsConnection.ConnectionInfo, FastdfsConnection> {

    public StorageConnectionPool(KeyedPooledObjectFactory<FastdfsConnection.ConnectionInfo, FastdfsConnection> factory, FastdfsProperties properties) {
        super(factory);
        //TODO parse properties & set config
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
