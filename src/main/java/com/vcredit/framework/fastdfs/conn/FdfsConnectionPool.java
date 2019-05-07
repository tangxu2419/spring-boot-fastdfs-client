package com.vcredit.framework.fastdfs.conn;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author tangxu
 * @date 2019/5/714:33
 */
@Component
public class FdfsConnectionPool extends GenericKeyedObjectPool<InetSocketAddress, Connection> {


    public FdfsConnectionPool(KeyedPooledObjectFactory<InetSocketAddress, Connection> factory) {
        super(factory);
    }

    @Autowired
    public FdfsConnectionPool(KeyedPooledObjectFactory<InetSocketAddress, Connection> factory, GenericKeyedObjectPoolConfig<Connection> config) {
        super(factory, config);
    }
}
