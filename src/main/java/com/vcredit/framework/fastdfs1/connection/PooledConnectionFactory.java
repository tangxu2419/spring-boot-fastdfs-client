/*
 *    Copyright 2019 vcredit
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
