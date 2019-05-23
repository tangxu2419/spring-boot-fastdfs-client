/*
 *    Copyright 2019 VCREDIT
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

package com.vcredit.framework.fastdfs.command.tracker.invoker;

import com.vcredit.framework.fastdfs.command.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.ObjectMetaData;
import com.vcredit.framework.fastdfs.command.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.connection.FastdfsConnection;
import com.vcredit.framework.fastdfs.connection.FastdfsConnectionPoolHolder;
import com.vcredit.framework.fastdfs.connection.TrackerConnectionPool;
import com.vcredit.framework.fastdfs.exception.FastdfsConnectionException;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracker指令
 *
 * @author tangx
 */
public abstract class AbstractTrackerCommandInvoker extends AbstractCommandInvoker {

    private final static TrackerConnectionPool pool = FastdfsConnectionPoolHolder.TRACKER_CONNECTION_POOL;

    /**
     * storage指令
     */
    protected TrackerCommand command;

    /**
     * 执行指令交易
     *
     * @return 结果
     */
    @Override
    public BaseOperationResult action() throws InvokeCommandException {
        FastdfsConnection conn;
        try {
            conn = pool.borrowObject();
        } catch (Exception e) {
            throw new FastdfsConnectionException(e);
        }
        try {
            pool.markAsActive(conn.getInetSocketAddress());
            return super.execute(conn);
        } catch (IOException e) {
            pool.markAsProblem(conn.getInetSocketAddress());
            throw new FastdfsConnectionException(e);
        } finally {
            pool.returnObject(conn);
        }
    }

    /**
     * 解析封装集合对象
     *
     * @param genericType 解析对象类型
     * @param content     字节缓冲区
     * @param charset     编码
     * @param <T>         解析对象类型
     * @return 解析对象集合
     * @throws InvokeCommandException 响应字节长度不匹配/初始化或解析对象失败
     */
    <T> List<T> packageList(Class<T> genericType, byte[] content, Charset charset) throws InvokeCommandException {
        ObjectMetaData<T> metaData = new ObjectMetaData<>(genericType);
        int fieldsTotalSize = metaData.getFieldsTotalSize();
        if (content.length % fieldsTotalSize != 0) {
            throw new InvokeCommandException("响应字节数组长度无效: " + content.length);
        }
        int count = content.length / fieldsTotalSize;
        int offset = 0;
        List<T> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            byte[] one = new byte[fieldsTotalSize];
            System.arraycopy(content, offset, one, 0, fieldsTotalSize);
            list.add(packageObject(one, metaData, charset));
            offset += fieldsTotalSize;
        }
        return list;
    }

    /**
     * 解析封装对象
     *
     * @param content  字节缓冲区
     * @param metaData 类型映射对象
     * @param charset  编码
     * @param <T>      解析对象类型
     * @return 封装对象
     * @throws InvokeCommandException 初始化或解析对象失败
     */
    private <T> T packageObject(byte[] content, ObjectMetaData<T> metaData, Charset charset) throws InvokeCommandException {
        List<ObjectMetaData.FieldMetaData> fieldList = metaData.getFieldList();
        try {
            T obj = metaData.getGenericType().getDeclaredConstructor().newInstance();
            for (ObjectMetaData.FieldMetaData field : fieldList) {
                BeanUtils.setProperty(obj, field.getField().getName(), field.getFieldValue(content, charset));
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InvokeCommandException("初始化对象失败：", e);
        }
    }

}
