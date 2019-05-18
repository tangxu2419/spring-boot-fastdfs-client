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

package com.vcredit.framework.fastdfs.command.storage.invoker;

import com.vcredit.framework.fastdfs.command.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageNode;
import com.vcredit.framework.fastdfs.connection.FastdfsConnection;
import com.vcredit.framework.fastdfs.connection.FastdfsConnectionPoolHolder;
import com.vcredit.framework.fastdfs.connection.StorageConnectionPool;
import com.vcredit.framework.fastdfs.exception.FastdfsConnectionException;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;

import java.io.IOException;

/**
 * @author tangx
 */
public abstract class AbstractStorageCommandInvoker extends AbstractCommandInvoker {


    private StorageConnectionPool pool = FastdfsConnectionPoolHolder.STORAGE_CONNECTION_POOL;

    /**
     * storage指令
     */
    protected StorageCommand command;

    /**
     * 解析反馈消息对象
     */
    @Deprecated
    protected BaseOperationResult response;

    /**
     * 执行指令交易
     *
     * @return 结果
     */
    @Override
    public BaseOperationResult action() throws InvokeCommandException {
        FastdfsConnection conn;
        try {
            StorageNode storageNode = command.getStorageNode();
            conn = pool.borrowObject(storageNode);
        } catch (Exception e) {
            throw new InvokeCommandException(e);
        }
        try {
            return super.execute(conn);
        } catch (IOException ioe) {
            throw new FastdfsConnectionException(ioe);
        } finally {
            pool.returnObject(conn);
        }
    }
}
