package com.vcredit.framework.fastdfs.config;

import com.vcredit.framework.fastdfs.conn.PoolConnectFactory;
import com.vcredit.framework.fastdfs.conn.TrackerConnectionPool;
import com.vcredit.framework.fastdfs.service.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Dong Zhuming
 */
@Configuration
@EnableConfigurationProperties(FastdfsProperties.class)
public class FastdfsAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FastdfsAutoConfiguration.class);

    private final FastdfsProperties fastdfsProperties;

    public FastdfsAutoConfiguration(FastdfsProperties fastdfsProperties) {
        this.fastdfsProperties = fastdfsProperties;
    }

    @Bean
    public TrackerConnectionPool trackerConnectionPool() throws IOException {
        return new TrackerConnectionPool(fastdfsProperties);
    }

//    @Bean
//    public PoolConnectFactory poolConnectFactory() throws IOException {
//        return new PoolConnectFactory(fastdfsProperties);
//    }


    @Bean
    public TrackerClient trackerClient(TrackerConnectionPool trackerConnectionPool) {
        return new TrackerClient(trackerConnectionPool);
    }
}
