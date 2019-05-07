package com.vcredit.framework.fastdfs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.List;

/**
 * @author Dong Zhuming
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "fastdfs")
public class FastdfsProperties {
    /**
     * 连接超时时间
     */
    private Duration connectTimeout;

    /**
     * 读取时间
     */
    private Duration soTimeout;

    private Duration networkTimeout;
    /**
     * group name字符集
     */
    private String charset = "UTF-8";

    private Pool pool;

    private Cluster cluster;

    /**
     * 连接池配置
     */
    @Getter
    @Setter
    public static class Pool {
        /**
         * 最大连接数
         */
        private int maxSize = 10;

        /**
         * 最小连接数
         */
        private int minSize = 1;

        /**
         * 最大空闲时间
         */
        private Duration maxIdleTime;

        /**
         * 最大错误重试次数
         */
        private int maxAttempt = 3;

    }

    /**
     * 集群配置
     */
    @Getter
    @Setter
    public static class Cluster {
        /**
         * Comma-separated list of "host:port" pairs.
         */
        private List<String> nodes;

    }
}


