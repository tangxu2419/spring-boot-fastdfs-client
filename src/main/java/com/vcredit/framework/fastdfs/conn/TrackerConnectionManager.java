package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import com.vcredit.framework.fastdfs.exception.FdfsConnectException;
import com.vcredit.framework.fastdfs.exception.FdfsServerException;
import com.vcredit.framework.fastdfs.proto.CircularList;
import com.vcredit.framework.fastdfs.proto.FdfsCommand;
import com.vcredit.framework.fastdfs.proto.TrackerNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author tangxu
 */
@Component
public class TrackerConnectionManager extends ConnectionManager {

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
     * 目录服务地址-为了加速处理，增加了一个map
     */
    private final Map<InetSocketAddress, TrackerNode> trackerAddressMap = new HashMap<>();

    /**
     * 轮询圈
     */
    private final CircularList<TrackerNode> trackerAddressCircular = new CircularList<>();


    public TrackerConnectionManager(@Qualifier("fdfsConnectionPool") FdfsConnectionPool pool, FastdfsProperties fastdfsProperties) {
        super(pool);
        if (fastdfsProperties.getCluster() == null || fastdfsProperties.getCluster().getNodes().isEmpty()) {
            throw new FdfsServerException("tracker地址配置为空");
        }
        trackerList = fastdfsProperties.getCluster().getNodes();
        buildTrackerAddresses();
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
    InetSocketAddress getTrackerAddress() {
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
     * 获取连接并执行交易
     *
     * @param command 请求指令
     * @return 响应结果
     */
    public <T> T executeFdfsTrackerCmd(FdfsCommand<T> command) {
        Connection conn;
        InetSocketAddress address = null;
        // 获取连接
        try {
            address = this.getTrackerAddress();
            log.debug("获取到Tracker连接地址{}", address);
            conn = getConnection(address);
            this.setActive(address);
        } catch (FdfsConnectException e) {
            this.setInActive(address);
            throw e;
        } catch (Exception e) {
            log.error("Unable to borrow buffer from pool", e);
            throw new RuntimeException("Unable to borrow buffer from pool", e);
        }
        // 执行交易
        return execute(address, conn, command);
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
    void setActive(InetSocketAddress address) {
        TrackerNode holder = trackerAddressMap.get(address);
        holder.setActive();
    }

    /**
     * 设置连接无效
     *
     * @param address 连接地址
     */
    void setInActive(InetSocketAddress address) {
        TrackerNode holder = trackerAddressMap.get(address);
        holder.setInActive();
    }

}
