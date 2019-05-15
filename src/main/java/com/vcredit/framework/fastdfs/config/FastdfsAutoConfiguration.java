package com.vcredit.framework.fastdfs.config;

import com.vcredit.framework.fastdfs.conn.FdfsConnectionPool;
import com.vcredit.framework.fastdfs.conn.PoolConnectFactory;
import com.vcredit.framework.fastdfs.conn.StorageConnectionPool;
import com.vcredit.framework.fastdfs.conn.TrackerConnectionPool;
import com.vcredit.framework.fastdfs.refine.FastdfsConnectionPoolHolder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dong Zhuming
 */
@Configuration
@EnableConfigurationProperties(FastdfsProperties.class)
public class FastdfsAutoConfiguration {

    /**
     * 连接池连接工厂
     *
     * @param properties 自定义配置参数
     * @return factory
     */
    @Bean
    public PoolConnectFactory poolConnectFactory(FastdfsProperties properties) {
        return new PoolConnectFactory(properties);
    }

    @Bean
    public FdfsConnectionPool fdfsConnectionPool(PoolConnectFactory factory, ConnectPoolConfig config) {
        return new FdfsConnectionPool(factory, config);
    }


    /**
     * 创建tracker服务连接池
     *
     * @param factory    连接池连接工厂
     * @param properties 自定义配置参数
     * @return trackerPool
     */
    @Bean
    public TrackerConnectionPool trackerConnectionPool(PoolConnectFactory factory, ConnectPoolConfig config, FastdfsProperties properties) {
        TrackerConnectionPool trackerConnectionPool = new TrackerConnectionPool(factory, config, properties);
        FastdfsConnectionPoolHolder.TRACKER_CONNECTION_POOL = trackerConnectionPool;
        return trackerConnectionPool;
    }

    /**
     * 创建tracker服务连接池
     *
     * @param factory 连接池连接工厂
     * @return storagePool
     */
    @Bean
    public StorageConnectionPool storageConnectionPool(PoolConnectFactory factory, ConnectPoolConfig config) {
        StorageConnectionPool storageConnectionPool = new StorageConnectionPool(factory, config);
        FastdfsConnectionPoolHolder.STORAGE_CONNECTION_POOL = storageConnectionPool;
        return storageConnectionPool;
    }


}
