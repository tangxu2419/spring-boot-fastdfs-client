package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.conn.Connection;
import com.vcredit.framework.fastdfs.conn.FdfsConnectionPool;
import com.vcredit.framework.fastdfs.exception.FdfsIOException;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;
import com.vcredit.framework.fastdfs.refine.storage.invoker.*;
import com.vcredit.framework.fastdfs.refine.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.refine.tracker.invoker.FetchStorageCommandInvoker;
import com.vcredit.framework.fastdfs.refine.tracker.invoker.GetStorageCommandInvoker;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author dongzhuming
 */
public abstract class AbstractCommandInvoker {

    private static final Logger log = LoggerFactory.getLogger(AbstractCommandInvoker.class);

    public static AbstractCommandInvoker prepare(FastdfsCommand command) {
        if (command instanceof StorageCommand.Upload) {
            return new UploadCommandInvoker((StorageCommand.Upload) command);
        } else if (command instanceof StorageCommand.SetMeta) {
            return new SetMetaDataCommandInvoker((StorageCommand.SetMeta) command);
        } else if (command instanceof StorageCommand.GetMeta) {
            return new GetMetaDataCommandInvoker((StorageCommand.GetMeta) command);
        } else if (command instanceof StorageCommand.Download) {
            return new DownloadCommandInvoker((StorageCommand.Download) command);
        } else if (command instanceof StorageCommand.Delete) {
            return new DeleteCommandInvoker((StorageCommand.Delete) command);
        } else if (command instanceof StorageCommand.SlaveFile) {
            return new SlaveFileCommandInvoker((StorageCommand.SlaveFile) command);
        } else if (command instanceof StorageCommand.FileInfo) {
            return new FileInfoCommandInvoker((StorageCommand.FileInfo) command);
        } else if (command instanceof TrackerCommand.GetStorage) {
            return new GetStorageCommandInvoker((TrackerCommand.GetStorage) command);
        } else if (command instanceof TrackerCommand.FetchStorage) {
            return new FetchStorageCommandInvoker((TrackerCommand.FetchStorage) command);
        } else {
            //TODO
            throw new NotImplementedException("");
        }
    }

    /**
     * 表示请求消息
     */
    protected AbstractFdfsRequest request;

    /**
     * 执行命令
     *
     * @return 操作结果
     */
    public abstract OperationResult action();


    /**
     * 对服务端发出请求然后接收反馈
     */
    protected OperationResult execute(FdfsConnectionPool pool, InetSocketAddress address, Connection conn) {
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

    /**
     * 将报文输出规范为模板方法
     * <p>
     * <pre>
     * 1.输出报文头
     * 2.输出报文参数
     * 3.输出文件内容
     * </pre>
     *
     * @param out     socket输出流
     * @param charset 编码
     * @throws Exception 异常
     */
    protected void send(OutputStream out, Charset charset) throws Exception {
        byte[] head = request.getHeadByte(charset);
        byte[] param = request.getParam();
        InputStream inputFile = request.getInputFile();
        long fileSize = request.getFileSize();
        log.debug("发出交易请求..报文头为{}", request.getHead());
        log.debug("交易参数为{}", param);
        out.write(head);
        if (null != param) {
            out.write(param);
        }
        if (null != inputFile) {
            sendFileContent(inputFile, fileSize, out);
        }
    }

    /**
     * 发送文件
     *
     * @param ins  文件输入流
     * @param size 文件长度
     * @param ous  socket输出流
     * @throws IOException 异常
     */
    private void sendFileContent(InputStream ins, long size, OutputStream ous) throws IOException {
        log.debug("开始上传文件流大小为{}", size);
        long remainBytes = size;
        byte[] buff = new byte[256 * 1024];
        int bytes;
        while (remainBytes > 0) {
            if ((bytes = ins.read(buff, 0, remainBytes > buff.length ? buff.length : (int) remainBytes)) < 0) {
                throw new IOException("the end of the stream has been reached. not match the expected size ");
            }
            ous.write(buff, 0, bytes);
            remainBytes -= bytes;
            log.debug("剩余数据量{}", remainBytes);
        }
    }


    /**
     * 接收这里只能确切知道报文头，报文内容(参数+文件)只能靠接收对象分析
     *
     * @param in socket输入流
     * @return 返回响应头
     * @throws IOException 异常
     */
    protected ProtoHead parseHeader(InputStream in) throws Exception {
        ProtoHead head = ProtoHead.createFromInputStream(in);
        log.debug("服务端返回报文头{}", head);
        head.validateResponseHead();
        return head;
    }
}
