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

    private Pool pool;

    @Getter
    @Setter
    public static class Pool {
        /**
         * 从池中借出的对象的最大数目
         */
        private int maxTotal;

        /**
         * 在空闲时检查有效性, 默认false
         */
        private boolean testWhileIdle;

        /**
         * 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted)
         * 如果超时就抛异常,小于零:阻塞不确定的时间,默认-1
         */
        private Duration maxWaitMillis;

        /**
         * 连接耗尽时是否阻塞(默认true)
         * false报异常,true阻塞直到超时
         */
        private boolean blockWhenExhausted;

        /**
         * 视休眠时间超过了xxx秒的对象为过期
         */
        private Duration minEvictableIdleTimeMillis;

        /**
         * 逐出扫描的时间间隔(毫秒) 每过xx秒进行一次后台对象清理的行动
         * 如果为负数,则不运行逐出线程, 默认-1
         */
        private Duration timeBetweenEvictionRunsMillis;

        /**
         * 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
         * －1表示清理时检查所有线程
         */
        private int numTestsPerEvictionRun;

    }
}


