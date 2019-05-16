package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import com.vcredit.framework.fastdfs.conn.TrackerConnectionPool2;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;

import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzhuming
 */
public class TrackerConnectionPool extends GenericKeyedObjectPool<FastdfsConnection.ConnectionInfo, FastdfsConnection> {

    public TrackerConnectionPool(KeyedPooledObjectFactory<FastdfsConnection.ConnectionInfo, FastdfsConnection> factory, FastdfsProperties properties) {
        super(factory);
        //TODO parse properties & set config & set TrackerNodeLocator
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
}
