package com.vcredit.framework.fastdfs.proto;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.exception.FdfsServerException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.vcredit.framework.fastdfs.constants.Constants.PROTO_HEADER_CMD_INDEX;
import static com.vcredit.framework.fastdfs.constants.Constants.PROTO_HEADER_STATUS_INDEX;

/**
 * @author tangxu
 * @date 2019/5/614:19
 */
public class ProtoHead {
    /**
     * 报文长度
     */
    private static final int HEAD_LENGTH = Constants.FDFS_PROTO_PKG_LEN_SIZE + 2;

    /**
     * 报文内容长度1-7位
     */
    private long contentLength = 0;
    /**
     * 报文类型8位
     */
    private byte cmd;
    /**
     * 处理状态9位
     */
    private byte status = (byte) 0;


    public long getContentLength() {
        return contentLength;
    }

    public byte getCmd() {
        return cmd;
    }

    public byte getStatus() {
        return status;
    }


    /**
     * 请求报文构造函数
     */
    public ProtoHead(byte cmd) {
        super();
        this.cmd = cmd;
    }

    /**
     * 返回报文构造函数
     *
     * @param contentLength
     * @param cmd
     * @param status
     */
    public ProtoHead(long contentLength, byte cmd, byte status) {
        this.contentLength = contentLength;
        this.cmd = cmd;
        this.status = status;
    }


    /**
     * pack header by FastDFS transfer protocol
     *
     * @return packed byte buffer
     */
    public byte[] packHeader() {
        byte[] header = new byte[HEAD_LENGTH];
        Arrays.fill(header, (byte) 0);
        byte[] hexLen = ProtoPackageUtil.long2buff(contentLength);
        System.arraycopy(hexLen, 0, header, 0, hexLen.length);
        header[PROTO_HEADER_CMD_INDEX] = cmd;
        header[PROTO_HEADER_STATUS_INDEX] = status;
        return header;
    }

    /**
     * 读取输入流创建报文头
     *
     * @param ins
     * @return
     * @throws IOException
     */
    public static ProtoHead createFromInputStream(InputStream ins) throws IOException {
        byte[] header = new byte[HEAD_LENGTH];
        int bytes;
        // 读取HEAD_LENGTH长度的输入流
        if ((bytes = ins.read(header)) != header.length) {
            throw new IOException("recv package size " + bytes + " != " + header.length);
        }
        long returnContentLength = ProtoPackageUtil.buff2long(header, 0);
        byte returnCmd = header[Constants.PROTO_HEADER_CMD_INDEX];
        byte returnStatus = header[Constants.PROTO_HEADER_STATUS_INDEX];
        // 返回解析出来的ProtoHead
        return new ProtoHead(returnContentLength, returnCmd, returnStatus);

    }

    /**
     * 验证服务端返回报文有效性
     *
     * @return
     * @throws IOException
     */
    public boolean validateResponseHead() throws IOException {
        // 检查是否是正确反馈报文
        if (cmd != ProtocolCommand.TRACKER_PROTO_CMD_RESP) {
            throw new IOException(
                    "recv cmd: " + cmd + " is not correct, expect cmd: " + ProtocolCommand.TRACKER_PROTO_CMD_RESP);
        }
        // 获取处理错误状态
        if (status != 0) {
            throw FdfsServerException.byCode(status);
        }

        if (contentLength < 0) {
            throw new IOException("recv body length: " + contentLength + " < 0!");
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProtoHead{ contentLength=" + contentLength + ", cmd=" + cmd + ", status=" + status + '}';
    }
}
