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

package com.vcredit.framework.fastdfs.config;

import com.vcredit.framework.fastdfs.FastdfsClient;
import com.vcredit.framework.fastdfs.connection.FastdfsConnectionPoolHolder;
import com.vcredit.framework.fastdfs.connection.PooledConnectionFactory;
import com.vcredit.framework.fastdfs.connection.StorageConnectionPool;
import com.vcredit.framework.fastdfs.connection.TrackerConnectionPool;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author Dong Zhuming
 */
@Configuration
@EnableConfigurationProperties(FastdfsProperties.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastdfsAutoConfiguration {

    /**
     * 连接池连接工厂
     *
     * @return factory
     */
    @Bean
    public PooledConnectionFactory pooledConnectionFactory(FastdfsProperties fastdfsProperties) {
        return new PooledConnectionFactory(fastdfsProperties);
    }

    /**
     * 创建tracker服务连接池
     *
     * @param pooledConnectionFactory 连接池连接工厂
     * @param fastdfsProperties       自定义配置参数
     * @return trackerPool
     */
    @Bean
    public TrackerConnectionPool trackerConnectionPool(PooledConnectionFactory pooledConnectionFactory, FastdfsProperties fastdfsProperties) {
        TrackerConnectionPool trackerConnectionPool = new TrackerConnectionPool(pooledConnectionFactory, fastdfsProperties);
        FastdfsConnectionPoolHolder.TRACKER_CONNECTION_POOL = trackerConnectionPool;
        return trackerConnectionPool;
    }

    /**
     * 创建tracker服务连接池
     *
     * @param pooledConnectionFactory 连接池连接工厂
     * @param fastdfsProperties       自定义配置参数
     * @return storagePool
     */
    @Bean
    public StorageConnectionPool storageConnectionPool(PooledConnectionFactory pooledConnectionFactory, FastdfsProperties fastdfsProperties) {
        StorageConnectionPool storageConnectionPool = new StorageConnectionPool(pooledConnectionFactory, fastdfsProperties);
        FastdfsConnectionPoolHolder.STORAGE_CONNECTION_POOL = storageConnectionPool;
        return storageConnectionPool;
    }

    @Bean
    public FastdfsClient fastdfsClient() {
        return new FastdfsClient();
    }


}
