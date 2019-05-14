package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class PoolConnectFactory extends BaseKeyedPooledObjectFactory<InetSocketAddress, Connection> {
    /**
     * 读取时间
     */
    private int soTimeout;
    /**
     * 连接超时时间
     */
    private int connectTimeout;
    /**
     * 字符集
     */
    private Charset charset;

    /**
     * 默认字符集
     */
    private static final String  DEFAULT_CHARSET_NAME = "UTF-8";

    public PoolConnectFactory(FastdfsProperties properties) {
        this.soTimeout = properties.getSoTimeout().toMillisPart();
        this.connectTimeout = properties.getConnectTimeout().toMillisPart();
        this.charset = Charset.forName(properties.getCharset());
    }

    /**
     * 创建连接
     */
    @Override
    public Connection create(InetSocketAddress address) {
        // 初始化字符集
        if (null == charset) {
            charset = Charset.forName(DEFAULT_CHARSET_NAME);
        }
        return new DefaultConnection(address, soTimeout, connectTimeout, charset);
    }

    @Override
    public PooledObject<Connection> wrap(Connection conn) {
        return new DefaultPooledObject<>(conn);
    }

}
