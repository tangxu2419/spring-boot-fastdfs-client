package com.vcredit.framework.fastdfs.config;

import com.vcredit.framework.fastdfs.TrackerClient;
import com.vcredit.framework.fastdfs.TrackerConnectionPool;
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
        log.info(fastdfsProperties.toString());
        return new TrackerConnectionPool();
    }

    @Bean
    public TrackerClient trackerClient(TrackerConnectionPool trackerConnectionPool) {
        return new TrackerClient(trackerConnectionPool);
    }
}
