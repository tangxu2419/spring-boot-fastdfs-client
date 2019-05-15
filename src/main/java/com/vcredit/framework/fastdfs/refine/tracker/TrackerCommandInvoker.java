package com.vcredit.framework.fastdfs.refine.tracker;

import com.vcredit.framework.fastdfs.conn.Connection;
import com.vcredit.framework.fastdfs.conn.TrackerConnectionPool;
import com.vcredit.framework.fastdfs.exception.FdfsIOException;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsConnectionPoolHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Tracker指令
 */
public abstract class TrackerCommandInvoker extends AbstractCommandInvoker {

    private static final Logger log = LoggerFactory.getLogger(TrackerCommandInvoker.class);

    private TrackerConnectionPool pool = FastdfsConnectionPoolHolder.TRACKER_CONNECTION_POOL;

    /**
     * storage指令
     */
    protected TrackerCommand command;


    /**
     * 执行指令交易
     *
     * @return 结果
     */
    @Override
    public OperationResult action() {
        return this.executeFdfsCmd();
    }

    /**
     * 获取连接并执行交易
     *
     * @return 结果
     */
    private OperationResult executeFdfsCmd() {
        // 获取连接
        InetSocketAddress address = pool.getTrackerAddress();
        Connection conn = pool.borrow(address);
        // 执行交易
        return execute(address, conn);

    }

    /**
     * 对服务端发出请求然后接收反馈
     */
    private OperationResult execute(InetSocketAddress address, Connection conn) {
        try {
            Charset charset = conn.getCharset();
            InputStream inputStream = conn.getInputStream();

            send(conn.getOutputStream(), charset);
            ProtoHead head = parseHeader(inputStream);
            return parseContent(inputStream, head, charset);
        } catch (Exception e) {
            log.error("parseHeader content error", e);
            throw new FdfsIOException("socket io exception occurred while execute command", e);
        } finally {
            try {
                if (null != conn) {
                    pool.returnObject(address, conn);
                }
            } catch (Exception e) {
                log.error("return pooled connection error", e);
            }
        }
    }


    /**
     * 解析反馈内容
     *
     * @param in      响应输入流
     * @param head    响应头
     * @param charset 编码
     * @return 响应对象
     * @throws Exception 异常
     */
    protected abstract OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception;

}
