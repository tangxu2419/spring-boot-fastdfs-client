package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.config.ConnectPoolConfig;
import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import com.vcredit.framework.fastdfs.exception.FdfsConnectException;
import com.vcredit.framework.fastdfs.exception.FdfsServerException;
import com.vcredit.framework.fastdfs.proto.CircularList;
import com.vcredit.framework.fastdfs.proto.TrackerNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author tangxu
 */
public class TrackerConnectionPool extends FdfsConnectionPool {

    private static final Logger log = LoggerFactory.getLogger(TrackerConnectionPool.class);

    /**
     * 10分钟以后重试连接
     */
    private static final int DEFAULT_RETRY_AFTER_SECOND = 10 * 60;
    /**
     * 连接中断以后经过N秒重试
     */
    private int retryAfterSecond = DEFAULT_RETRY_AFTER_SECOND;

    /**
     * tracker服务配置地址列表
     */
    private List<String> trackerList;

    /**
     * 轮询圈
     */
    private final CircularList<TrackerNode> trackerAddressCircular = new CircularList<>();

    /**
     * 目录服务地址-为了加速处理，增加了一个map
     */
    private final Map<InetSocketAddress, TrackerNode> trackerAddressMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public TrackerConnectionPool(KeyedPooledObjectFactory<InetSocketAddress, Connection> factory, ConnectPoolConfig config, FastdfsProperties fastdfsProperties) {
        super(factory, config);
        if (fastdfsProperties.getCluster() == null || fastdfsProperties.getCluster().getNodes().isEmpty()) {
            throw new FdfsServerException("tracker地址配置为空");
        }
        trackerList = fastdfsProperties.getCluster().getNodes();
        buildTrackerAddresses();
    }


    /**
     * 获取连接
     */
    public Connection borrow(InetSocketAddress address) {
        Connection conn;
        // 获取连接
        try {
            log.debug("获取到Tracker连接地址{}", address);
            conn = this.borrowObject(address);
            this.setActive(address);
        } catch (FdfsConnectException e) {
            this.setInActive(address);
            throw e;
        } catch (Exception e) {
            log.error("Unable to borrow buffer from pool", e);
            throw new RuntimeException("Unable to borrow buffer from pool", e);
        }
        return conn;
    }


    /**
     * 分析TrackerAddress
     */
    private void buildTrackerAddresses() {
        Set<InetSocketAddress> addressSet = new HashSet<>();
        for (String item : trackerList) {
            if (StringUtils.isBlank(item)) {
                continue;
            }
            String[] parts = StringUtils.split(item, ":", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException(
                        "the value of item \"tracker_server\" is invalid, the correct format is host:port");
            }
            InetSocketAddress address = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            addressSet.add(address);
        }
        // 放到轮询圈
        for (InetSocketAddress item : addressSet) {
            TrackerNode holder = new TrackerNode(item);
            trackerAddressCircular.add(holder);
            trackerAddressMap.put(item, holder);
        }
    }


    /**
     * 获取Tracker服务器地址
     *
     * @return 获取地址
     */
    public InetSocketAddress getTrackerAddress() {
        TrackerNode holder;
        // 遍历连接地址,抓取当前有效的地址
        for (int i = 0; i < trackerAddressCircular.size(); i++) {
            holder = trackerAddressCircular.next();
            if (holder.canTryToConnect(retryAfterSecond)) {
                return holder.getAddress();
            }
        }
        throw new FdfsServerException("找不到可用的tracker " + getTrackerAddressConfigString());
    }


    /**
     * 获取配置地址列表
     *
     * @return 获取tracker地址
     */
    private String getTrackerAddressConfigString() {
        StringBuffer config = new StringBuffer();
        for (int i = 0; i < trackerAddressCircular.size(); i++) {
            TrackerNode holder = trackerAddressCircular.next();
            InetSocketAddress address = holder.getAddress();
            config.append(address.toString()).append(",");
        }
        return new String(config);
    }

    /**
     * 设置连接有效
     *
     * @param address 连接地址
     */
    private void setActive(InetSocketAddress address) {
        TrackerNode holder = trackerAddressMap.get(address);
        holder.setActive();
    }

    /**
     * 设置连接无效
     *
     * @param address 连接地址
     */
    private void setInActive(InetSocketAddress address) {
        TrackerNode holder = trackerAddressMap.get(address);
        holder.setInActive();
    }

}
