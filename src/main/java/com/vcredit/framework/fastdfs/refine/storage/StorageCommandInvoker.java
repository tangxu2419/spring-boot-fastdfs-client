package com.vcredit.framework.fastdfs.refine.storage;

import com.vcredit.framework.fastdfs.conn.Connection;
import com.vcredit.framework.fastdfs.conn.StorageConnectionPool;
import com.vcredit.framework.fastdfs.exception.FdfsIOException;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsConnectionPoolHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author tangx
 */
public abstract class StorageCommandInvoker extends AbstractCommandInvoker {

    private static final Logger log = LoggerFactory.getLogger(StorageCommandInvoker.class);

    private StorageConnectionPool pool = FastdfsConnectionPoolHolder.STORAGE_CONNECTION_POOL;

    /**
     * storage指令
     */
    protected StorageCommand command;


    /**
     * 解析反馈消息对象
     */
    @Deprecated
    protected OperationResult response;

    /**
     * 执行指令交易
     *
     * @return 结果
     */
    @Override
    public OperationResult action() {
        return this.executeFdfsCmd(command);
    }

    /**
     * 获取连接并执行交易
     *
     * @param command 指令
     * @return 结果
     */
    private OperationResult executeFdfsCmd(StorageCommand command) {
        // 获取连接
        StorageNode storageNode = command.getStorageNode();
        Connection conn = pool.borrow(storageNode);
        // 执行交易
        return execute(storageNode.getInetSocketAddress(), conn);

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
