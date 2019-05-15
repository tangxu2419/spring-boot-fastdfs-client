package com.vcredit.framework.fastdfs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
     * 上传MetaData失败时，是否删除原始文件
     */
    private Boolean uploadFailIgnore;
    /**
     * 连接超时时间
     */
    private Duration connectTimeout;

    /**
     * 读取时间
     */
    private Duration soTimeout;

    /**
     * group name字符集
     */
    private String charset = "UTF-8";


    private Cluster cluster;

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


