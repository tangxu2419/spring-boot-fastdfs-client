package com.vcredit.framework.fastdfs.refine.storage;

import com.vcredit.framework.fastdfs.conn.Connection;
import com.vcredit.framework.fastdfs.exception.FdfsIOException;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author tangx
 */
public abstract class StorageCommandInvoker extends AbstractCommandInvoker {

    private static final Logger log = LoggerFactory.getLogger(StorageCommandInvoker.class);

    /**
     * 表示请求消息
     */
    protected AbstractFdfsRequest request;

    /**
     * 解析反馈消息对象
     */
    protected OperationResult response;

    /**
     * 对服务端发出请求然后接收反馈
     */
    public OperationResult execute(Connection conn) {
        // 封装socket交易 send
        try {
            send(conn.getOutputStream(), conn.getCharset());
        } catch (Exception e) {
            log.error("send conent error", e);
            throw new FdfsIOException("socket io exception occured while sending cmd", e);
        }
        try {
            return receive(conn.getInputStream(), conn.getCharset());
        } catch (Exception e) {
            log.error("receive conent error", e);
            throw new FdfsIOException("socket io exception occured while receive content", e);
        }

    }

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
    private void send(OutputStream out, Charset charset) throws Exception {
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
     * @param in      socket输入流
     * @param charset 编码
     * @return 解析响应对象
     * @throws IOException 异常
     */
    private OperationResult receive(InputStream in, Charset charset) throws Exception {
        ProtoHead head = ProtoHead.createFromInputStream(in);
        log.debug("服务端返回报文头{}", head);
        head.validateResponseHead();
        return this.decodeContent(in, head, charset);
    }

    /**
     * 解析反馈内容
     *
     * @param in      响应输入流
     * @param charset 编码
     * @return 响应对象
     * @throws Exception 异常
     */
    public abstract OperationResult decodeContent(InputStream in, ProtoHead head, Charset charset) throws Exception;

}
