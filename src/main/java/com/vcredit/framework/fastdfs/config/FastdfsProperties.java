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
@ConfigurationProperties(prefix = "fastdfs")
@Getter
@Setter
public class FastdfsProperties {
    private Duration connectTimeout;
    private Duration networkTimeout;
    private String charset;
    private Pool pool;
    private Cluster cluster;

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getNetworkTimeout() {
        return networkTimeout;
    }

    public void setNetworkTimeout(Duration networkTimeout) {
        this.networkTimeout = networkTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    @Getter
    @Setter
    public static class Pool {
        private int maxSize = 10;

        private int maxActive;

        private int maxAttempt = 3;

    }

    @Getter
    @Setter
    public static class Cluster {
        /**
         * Comma-separated list of "host:port" pairs.
         */
        private List<String> nodes;

    }
}
