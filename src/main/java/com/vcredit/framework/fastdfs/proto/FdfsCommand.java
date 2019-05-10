package com.vcredit.framework.fastdfs.proto;

import com.vcredit.framework.fastdfs.conn.Connection;

/**
 * @author tangxu
 * @date 2019/5/814:55
 */
public interface FdfsCommand<T> {

    /**
     * 执行交易
     */
    public T execute(Connection conn);
}
