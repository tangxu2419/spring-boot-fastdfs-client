package com.vcredit.framework.fastdfs.config;

import com.vcredit.framework.fastdfs.FastdfsClient;
import com.vcredit.framework.fastdfs.connection.FastdfsConnectionPoolHolder;
import com.vcredit.framework.fastdfs.connection.PooledConnectionFactory;
import com.vcredit.framework.fastdfs.connection.StorageConnectionPool;
import com.vcredit.framework.fastdfs.connection.TrackerConnectionPool;
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
     * @return factory
     */
    @Bean
    public PooledConnectionFactory PooledConnectionFactory(FastdfsProperties properties) {
        return new PooledConnectionFactory(properties);
    }

    /**
     * 创建tracker服务连接池
     *
     * @param factory    连接池连接工厂
     * @param properties 自定义配置参数
     * @return trackerPool
     */
    @Bean
    public TrackerConnectionPool trackerConnectionPool(PooledConnectionFactory factory, FastdfsProperties properties) {
        TrackerConnectionPool trackerConnectionPool = new TrackerConnectionPool(factory, properties);
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
    public StorageConnectionPool storageConnectionPool(PooledConnectionFactory factory, FastdfsProperties properties) {
        StorageConnectionPool storageConnectionPool = new StorageConnectionPool(factory, properties);
        FastdfsConnectionPoolHolder.STORAGE_CONNECTION_POOL = storageConnectionPool;
        return storageConnectionPool;
    }

    @Bean
    public FastdfsClient fastdfsClient() {
        return new FastdfsClient();
    }


}
