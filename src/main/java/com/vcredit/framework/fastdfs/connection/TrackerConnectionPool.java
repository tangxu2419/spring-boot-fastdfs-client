package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzhuming
 */
public class TrackerConnectionPool extends GenericKeyedObjectPool<FastdfsConnection.ConnectionInfo, FastdfsConnection> {

    private static final Logger log = LoggerFactory.getLogger(TrackerConnectionPool.class);

    public TrackerConnectionPool(KeyedPooledObjectFactory<FastdfsConnection.ConnectionInfo, FastdfsConnection> factory, FastdfsProperties properties) {
        super(factory);
        // 从池中借出的对象的最大数目
        FastdfsProperties.Pool pool = properties.getPool();
        super.setMaxTotal(pool.getMaxTotal());
        // 在空闲时检查有效性
        super.setTestWhileIdle(pool.isTestWhileIdle());
        // 连接耗尽时是否阻塞(默认true)
        super.setBlockWhenExhausted(pool.isBlockWhenExhausted());
        // 获取连接时的最大等待毫秒数100
        super.setMaxWaitMillis(pool.getMaxWaitMillis().toMillis());
        // 视休眠时间超过了180秒的对象为过期
        super.setMinEvictableIdleTimeMillis(pool.getMinEvictableIdleTimeMillis().toMillis());
        // 每过60秒进行一次后台对象清理的行动
        super.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRunsMillis().toMillis());
        // 清理时候检查所有线程
        super.setNumTestsPerEvictionRun(pool.getNumTestsPerEvictionRun());

        properties.getCluster().getNodes().forEach(node -> {
            String[] split = node.split(":", 2);
            try {
                final InetSocketAddress inetSocketAddress = new InetSocketAddress(split[0], Integer.parseInt(split[1]));
                TrackerConnectionPool.TrackerNodeLocator.addNode(inetSocketAddress);
            } catch (IllegalArgumentException e) {
                log.error("Illegal Tracker Server: {}", node);
            }
        });
    }

    public FastdfsConnection borrowObject() throws Exception {
        FastdfsConnection.ConnectionInfo connectionInfo = new FastdfsConnection.ConnectionInfo();
        connectionInfo.setType(FastdfsConnection.Type.TRACKER);
        connectionInfo.setInetSocketAddress(TrackerNodeLocator.getNodeSocket());
        return super.borrowObject(connectionInfo);
    }

    public void returnObject(FastdfsConnection connection) {
        super.returnObject(connection.getConnectionInfo(), connection);
    }



    private static class TrackerNodeLocator {

        private static Map<InetSocketAddress, Integer> nodeMap = new HashMap<>();

        static void addNode(InetSocketAddress inetSocketAddress) {
            nodeMap.put(inetSocketAddress, 0);
        }

        /**
         * 将此连接标记为不可用状态
         *
         * @param inetSocketAddress Tracker地址
         * @return 标记不可用之前的连接数
         */
        static Integer markProblem(InetSocketAddress inetSocketAddress) {
            return nodeMap.put(inetSocketAddress, Integer.MAX_VALUE);
        }

        static InetSocketAddress getNodeSocket() {
            //匹配连接数最小的服务器
            //FIXME 锁定addNode/removeNode方法
            return nodeMap.entrySet().stream()
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    .filter(entry -> entry.getValue() != Integer.MAX_VALUE)
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }
    }


    /**
     * 标记问题Socket
     */
    public void markAsProblem(InetSocketAddress inetSocketAddress) {
        TrackerConnectionPool.TrackerNodeLocator.markProblem(inetSocketAddress);
    }

    /**
     * 标记连接有效
     */
    public void markAsActive(InetSocketAddress inetSocketAddress) {
        TrackerConnectionPool.TrackerNodeLocator.addNode(inetSocketAddress);
    }
}
